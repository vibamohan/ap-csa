import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class Screen extends JPanel implements KeyListener {

  private final Game game;
  private final KeyStateManager keyManager;

  public Screen(KeyStateManager keyManager) {
    this.keyManager = keyManager;
    this.game = new Game(this, keyManager);
    setFocusable(true);
    addKeyListener(this);
  }

  public void animate() {
    Timer timer =
        new Timer(
            16,
            e -> {
              game.update();
              repaint();
            });
    timer.start();
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    game.draw(g);
  }

  @Override
  public void keyPressed(KeyEvent e) {
    keyManager.setKeyPressed(e.getKeyCode(), true);
  }

  @Override
  public void keyReleased(KeyEvent e) {
    keyManager.setKeyPressed(e.getKeyCode(), false);
  }

  @Override
  public void keyTyped(KeyEvent e) {}
}
