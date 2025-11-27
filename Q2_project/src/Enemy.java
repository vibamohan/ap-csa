import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import state.EnemyState;

public class Enemy {
  private final int index; // index in FbsManager
  private final FbsManager fbsManager;
  private static final Random random = new Random();

  public Enemy(int index, FbsManager fbsManager, EnemyState eState) {
    this.index = index;
    this.fbsManager = fbsManager;

    // Initialize velocity if needed
    int dx = random.nextInt(3) - 1;
    int dy = random.nextInt(3) - 1;
    if (dx == 0 && dy == 0) dx = 1;

    fbsManager.setEnemyState(index, eState.x(), eState.y(), eState.size(), dx, dy, true);
  }

  public void update() {
    EnemyState e = fbsManager.getEnemyState(index);
    if (e == null || !e.alive()) return;

    int newX = e.x() + e.vx();
    int newY = e.y() + e.vy();

    // Wrap around screen
    if (newX < 0) newX += Constants.SCREEN_WIDTH;
    if (newX > Constants.SCREEN_WIDTH) newX -= Constants.SCREEN_WIDTH;
    if (newY < 0) newY += Constants.SCREEN_HEIGHT;
    if (newY > Constants.SCREEN_HEIGHT) newY -= Constants.SCREEN_HEIGHT;

    fbsManager.setEnemyState(index, newX, newY, e.size(), e.vx(), e.vy(), e.alive());
  }

  public void draw(Graphics g) {
    EnemyState e = fbsManager.getEnemyState(index);
    if (e == null || !e.alive()) return;

    switch (e.size()) {
      case 3 -> g.setColor(Color.GRAY);
      case 2 -> g.setColor(Color.LIGHT_GRAY);
      case 1 -> g.setColor(Color.WHITE);
    }
    int radius = e.size() * 20;
    g.fillOval(e.x() - radius / 2, e.y() - radius / 2, radius, radius);
  }

  public boolean collidesWith(Player player) {
    EnemyState e = fbsManager.getEnemyState(index);
    if (e == null || !e.alive()) return false;

    int radius = e.size() * 20 / 2;
    double dist = Math.hypot(e.x() - player.x, e.y() - player.y);
    return dist < radius + Constants.PLAYER_WIDTH;
  }

  public List<Enemy> split() {
    EnemyState e = fbsManager.getEnemyState(index);
    if (e == null || !e.alive()) return new ArrayList<>();

    fbsManager.setEnemyAlive(index, false); // mark destroyed

    List<Enemy> pieces = new ArrayList<>();
    if (e.size() > 1) {
      for (int i = 0; i < 2; i++) {
        int dx = random.nextInt(3) - 1;
        int dy = random.nextInt(3) - 1;
        int newIndex = fbsManager.addEnemyState(e.x(), e.y(), e.size() - 1, dx, dy);
        pieces.add(new Enemy(newIndex, fbsManager, fbsManager.getEnemyState(newIndex)));
      }
    }
    return pieces;
  }

  /** Helper to update Enemy from deserialized EnemyState */
  public void setFromEnemyState(EnemyState eState) {
    fbsManager.setEnemyState(
        index, eState.x(), eState.y(), eState.size(), eState.dx(), eState.dy(), eState.alive());
  }
}
