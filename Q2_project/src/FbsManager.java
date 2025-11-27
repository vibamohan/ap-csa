import com.google.flatbuffers.FlatBufferBuilder;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import state.*;

public class FbsManager {
  private static FbsManager instance;

  public ByteBuffer buffer;
  private GameState currentState;

  private Player player;
  private ArrayList<Enemy> enemies;
  private KeyStateManager keyManager;
  private Game game;

  // Store velocities and alive flags for new enemies before serializing
  private Map<Integer, EnemyStateBuilder> pendingUpdates = new HashMap<>();

  public static FbsManager getInstance() {
    if (instance == null) instance = new FbsManager();
    return instance;
  }

  private FbsManager() {
    this.enemies = new ArrayList<>();
    this.keyManager = new KeyStateManager();
  }

  public void setGameObjects(
      Player player, ArrayList<Enemy> enemies, KeyStateManager keyManager, Game game) {
    this.player = player;
    this.enemies = enemies;
    this.keyManager = keyManager;
    this.game = game;
  }

  public void setEnemyState(int index, int x, int y, int size, int dx, int dy, boolean alive) {
    pendingUpdates.put(index, new EnemyStateBuilder(x, y, size, dx, dy, alive));
  }

  public int addEnemyState(int x, int y, int size, int dx, int dy) {
    int index = enemies.size();
    pendingUpdates.put(index, new EnemyStateBuilder(x, y, size, dx, dy, true));
    return index;
  }

  public void setEnemyAlive(int index, boolean alive) {
    EnemyStateBuilder b = pendingUpdates.get(index);
    if (b != null) b.alive = alive;
  }

  public EnemyState getEnemyState(int index) {
    if (currentState == null || index >= currentState.enemiesLength()) return null;
    return currentState.enemies(index);
  }

  /** Serialize all current game state into FlatBuffer */
  public void serialize() {
    if (player == null || enemies == null || keyManager == null) return;

    FlatBufferBuilder builder = new FlatBufferBuilder(1024);

    // Player
    int playerOffset =
        PlayerState.createPlayerState(builder, player.x, player.y, player.shooting, player.lives);

    // Enemies
    ArrayList<Integer> enemyOffsets = new ArrayList<>();
    for (int i = 0; i < enemies.size(); i++) {
      EnemyStateBuilder b =
          pendingUpdates.getOrDefault(i, new EnemyStateBuilder(0, 0, 3, 0, 0, true));
      int eOffset = EnemyState.createEnemyState(builder, b.x, b.y, b.size, b.dx, b.dy, b.alive);
      enemyOffsets.add(eOffset);
    }
    int enemiesOffset =
        GameState.createEnemiesVector(
            builder, enemyOffsets.stream().mapToInt(Integer::intValue).toArray());

    // Keys
    Map<Integer, Boolean> keyStates = keyManager.getAllKeyStates();
    int[] keyOffsets = new int[keyStates.size()];
    int idx = 0;
    for (Map.Entry<Integer, Boolean> entry : keyStates.entrySet()) {
      keyOffsets[idx++] = KeyState.createKeyState(builder, entry.getKey(), entry.getValue());
    }
    int keysOffset = GameState.createKeysVector(builder, keyOffsets);

    int gameStateOffset =
        GameState.createGameState(
            builder, keysOffset, playerOffset, enemiesOffset, game.score, game.level);

    builder.finish(gameStateOffset);

    buffer = builder.dataBuffer();
    currentState = GameState.getRootAsGameState(buffer);

    // Clear pending updates
    pendingUpdates.clear();
  }

  /** Deserialize FlatBuffer back to game objects */
  public GameState deserialize() {
    if (buffer == null) return null;

    currentState = GameState.getRootAsGameState(buffer);

    // Update player
    if (player != null) {
      PlayerState p = currentState.player();
      player.x = p.x();
      player.y = p.y();
      player.shooting = p.shooting();
      player.lives = p.lives();
    }

    // Update enemies
    for (int i = 0; i < currentState.enemiesLength(); i++) {
      EnemyState eState = currentState.enemies(i);
      if (!eState.alive()) continue; // skip dead enemies

      if (i < enemies.size()) {
        Enemy e = enemies.get(i);
        e.setFromEnemyState(eState);
      } else {
        enemies.add(new Enemy(i, this, eState));
      }
    }

    // Update key states
    for (int key : keyManager.getAllKeyStates().keySet()) keyManager.setKeyPressed(key, false);
    for (int i = 0; i < currentState.keysLength(); i++) {
      KeyState k = currentState.keys(i);
      keyManager.setKeyPressed(k.key(), k.pressed());
    }

    return currentState;
  }

  /** Simple builder for pending enemy state */
  private static class EnemyStateBuilder {
    int x, y, size, dx, dy;
    boolean alive;

    EnemyStateBuilder(int x, int y, int size, int dx, int dy, boolean alive) {
      this.x = x;
      this.y = y;
      this.size = size;
      this.dx = dx;
      this.dy = dy;
      this.alive = alive;
    }
  }
}
