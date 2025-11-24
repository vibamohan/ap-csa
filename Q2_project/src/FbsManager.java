import com.google.flatbuffers.FlatBufferBuilder;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Map;
import state.EnemyState;
import state.GameState;
import state.KeyState;
import state.PlayerState;

@SuppressWarnings("unused")
public class FbsManager {
  private static FbsManager instance;
  private ByteBuffer buffer;
  private GameState currentState;
  private Game game;

  private Player player;
  private ArrayList<Enemy> enemies;
  private KeyStateManager keyManager;

  public static FbsManager getInstance() {
    if (instance == null) {
      instance = new FbsManager();
    }
    return instance;
  }

  private FbsManager() {
    this.player = null;
    this.enemies = new ArrayList<>();
    this.keyManager = new KeyStateManager();
    this.buffer = null;
    this.currentState = null;
  }

  public void setGameObjects(
      Player player, ArrayList<Enemy> enemies, KeyStateManager keyManager, Game game) {
    this.player = player;
    this.enemies = enemies;
    this.keyManager = keyManager;
    this.game = game;
  }

  public void serialize() {
    if (player == null || enemies == null || keyManager == null) return;

    FlatBufferBuilder builder = new FlatBufferBuilder(1024);

    int playerOffset = PlayerState.createPlayerState(builder, player.x, player.y);

    int[] enemyOffsets = new int[enemies.size()];
    for (int i = 0; i < enemies.size(); i++) {
      Enemy e = enemies.get(i);
      enemyOffsets[i] = EnemyState.createEnemyState(builder, e.x, e.y);
    }
    int enemiesOffset = GameState.createEnemiesVector(builder, enemyOffsets);

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
  }

  public GameState deserialize() {
    if (buffer == null) return null;

    currentState = GameState.getRootAsGameState(buffer);

    if (player != null) {
      player.x = currentState.player().x();
      player.y = currentState.player().y();
    }

    if (enemies != null) {
      for (int i = 0; i < currentState.enemiesLength(); i++) {
        EnemyState eState = currentState.enemies(i);
        if (i < enemies.size()) {
          Enemy e = enemies.get(i);
          e.x = eState.x();
          e.y = eState.y();
        } else {
          enemies.add(new Enemy(eState.x(), eState.y()));
        }
      }
    }

    if (keyManager != null) {
      for (int key : keyManager.getAllKeyStates().keySet()) {
        keyManager.setKeyPressed(key, false);
      }
      for (int i = 0; i < currentState.keysLength(); i++) {
        KeyState kState = currentState.keys(i);
        keyManager.setKeyPressed(kState.key(), kState.pressed());
      }
    }

    return currentState;
  }
}
