import java.awt.*;

public class Player {
  int x, y;
  int width = Constants.PLAYER_WIDTH;
  int height = Constants.PLAYER_HEIGHT;
  private final Controllers.Controller controller;
  private final boolean isMouse;

  public Player(int x, int y, Controllers.Controller controller, boolean isMouse) {
    this.x = x;
    this.y = y;
    this.controller = controller;
    this.isMouse = isMouse;
  }

  public void update() {
    double xInput = controller.xControl.getAsDouble();
    double yInput = controller.yControl.getAsDouble();

    if (isMouse) {
      int dx = (int) (xInput - (x + width / 2));
      int dy = (int) (yInput - (y + height / 2));
      x += Math.signum(dx) * Math.min(Math.abs(dx), Constants.PLAYER_SPEED);
      y += Math.signum(dy) * Math.min(Math.abs(dy), Constants.PLAYER_SPEED);
    } else {
      x += (int) (xInput * Constants.PLAYER_SPEED);
      y += (int) (yInput * Constants.PLAYER_SPEED);
    }

    if (x < 0) x = 0;
    if (x + width > Constants.SCREEN_WIDTH) x = Constants.SCREEN_WIDTH - width;
    if (y < 0) y = 0;
    if (y + height > Constants.SCREEN_HEIGHT) y = Constants.SCREEN_HEIGHT - height;
  }

  public void draw(Graphics g) {
    g.setColor(Color.BLUE);
    g.fillRect(x, y, width, height);
  }

  public boolean collidesWith(Enemy e) {
    return x < e.x + Constants.ENEMY_WIDTH
        && x + width > e.x
        && y < e.y + Constants.ENEMY_HEIGHT
        && y + height > e.y;
  }
}
