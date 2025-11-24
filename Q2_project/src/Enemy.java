import java.awt.*;

public class Enemy {
  int x, y;

  public Enemy(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public void draw(Graphics g) {
    g.setColor(Color.RED);
    g.fillRect(x, y, Constants.ENEMY_WIDTH, Constants.ENEMY_HEIGHT);
  }
}
