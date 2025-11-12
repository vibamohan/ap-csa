import java.awt.*;

public class Farm {

  public void drawGrass(Graphics g, int x, int y) {
    g.setColor(new Color(30, 255, 30));
    g.drawLine(x, y, x, y - 5);
  }

  public void drawTree(Graphics g, int x, int y) {
    g.setColor(new Color(139, 69, 19));
    g.fillRect(x + 7, y + 20, 6, 20);
    g.setColor(new Color(34, 139, 34));
    g.fillOval(x - 5, y, 25, 25);
  }

  public void drawCorn(Graphics g, int x, int y) {
    g.setColor(new Color(255, 223, 0));
    g.fillOval(x, y, 10, 20);
    g.setColor(new Color(0, 128, 0));
    g.drawLine(x + 5, y + 20, x + 5, y + 30);
  }

  public void drawCow(Graphics g, int x, int y) {
    g.setColor(Color.WHITE);
    g.fillRect(x, y, 60, 30);
    g.setColor(Color.BLACK);
    g.fillOval(x + 10, y + 5, 10, 10);
    g.fillOval(x + 40, y + 10, 8, 8);
    g.setColor(Color.WHITE);
    g.fillOval(x + 55, y + 10, 15, 15);
  }

  public void drawSheep(Graphics g, int x, int y) {
    g.setColor(Color.WHITE);
    g.fillOval(x, y, 40, 25);
    g.setColor(Color.GRAY);
    g.fillOval(x + 30, y + 5, 15, 15);
  }

  public void drawHorse(Graphics g, int x, int y) {
    g.setColor(new Color(139, 69, 19));
    g.fillRect(x, y, 60, 25);
    g.fillOval(x + 50, y + 5, 20, 20);
  }
}
