import java.awt.*;
import javax.swing.*;

public class Screen extends JPanel implements Runnable {
  private int sunY = 100;
  private boolean day = true;

  private int cowX = 0;
  private int sheepX = 200;
  private int horseX = 400;
  private boolean horseRight = true;

  private Farm farm = new Farm();
  private Thread animationThread;

  public Screen() {
    animationThread = new Thread(this);
    animationThread.start();
  }

  @Override
  public int getHeight() {
    return 1000;
  }

  @Override
  public void run() {
    while (true) {
      sunY -= 2;
      if (sunY < -200) sunY = 500;
      day = sunY > 0;

      if (day) {
        cowX += 2;
        sheepX += 3;
        if (cowX > getWidth()) cowX = -100;
        if (sheepX > getWidth()) sheepX = -100;

        if (horseRight) horseX += 4;
        else horseX -= 4;
        if (horseX > getWidth() - 80) horseRight = false;
        if (horseX < 0) horseRight = true;
      }

      repaint();

      try {
        Thread.sleep(50);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    // Sky
    if (day) g.setColor(new Color(135, 206, 235));
    else g.setColor(new Color(10, 10, 50));
    g.fillRect(0, 0, getWidth(), getHeight());

    // Ground
    g.setColor(new Color(50, 200, 50));
    g.fillRect(0, 400, getWidth(), 900);

    // Sun/Moon
    g.setColor(day ? Color.YELLOW : Color.WHITE);
    g.fillOval(600, sunY, 80, 80);

    // Grass 10x6
    int startX = 50, startY = 400;
    for (int row = 0; row < 10; row++)
      for (int col = 0; col < 6; col++) farm.drawGrass(g, startX + col * 10, startY + row * 10);

    // Trees 5x5
    startX = 300;
    startY = 400;
    for (int row = 0; row < 5; row++)
      for (int col = 0; col < 5; col++) farm.drawTree(g, startX + col * 50, startY + row * 50);

    // Corn 5x5
    startX = 120;
    startY = 400;
    for (int row = 0; row < 5; row++)
      for (int col = 0; col < 5; col++) farm.drawCorn(g, startX + col * 30, startY + row * 30);

    // Animals
    farm.drawCow(g, cowX, 370);
    farm.drawSheep(g, sheepX, 380);
    farm.drawHorse(g, horseX, 360);
  }
}
