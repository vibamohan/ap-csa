import java.awt.*;
import java.awt.event.KeyEvent;

public class Game {
  private final Player player;
  private final Enemy[] enemies;
  private final KeyStateManager keyManager;
  public int level = 1;
  public int score = 0;

  public Game(Screen screen, KeyStateManager keyManager) {
    this.keyManager = keyManager;

    Controllers.Controller controller = Controllers.USER_KEYBOARD(keyManager);
    // Controllers.Controller controller = Controllers.USER_MOUSE(screen);

    this.player =
        new Player(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT - 100, controller, false);

    this.enemies = new Enemy[5];
    for (int i = 0; i < enemies.length; i++) {
      enemies[i] = new Enemy(100 + i * 120, 50);
    }
  }

  public void update() {
    player.update();

    for (Enemy enemy : enemies) {
      if (player.collidesWith(enemy)) System.out.println("Collision!");
    }

    if (keyManager.isPressed(KeyEvent.VK_9)) nextLevel();
  }

  public void draw(Graphics g) {
    g.setColor(level == 1 ? Color.CYAN : Color.ORANGE);
    g.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

    player.draw(g);
    for (Enemy enemy : enemies) enemy.draw(g);

    g.setColor(Color.BLACK);
    g.drawString("Score: " + score, 10, 20);
  }

  private void nextLevel() {
    level++;
    System.out.println("Level up! Now level: " + level);
  }
}
