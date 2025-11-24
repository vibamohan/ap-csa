import javax.swing.*;

public class Runner {
  public static void main(String[] args) {
    JFrame frame = new JFrame("My Arcade Game");
    KeyStateManager keyManager = new KeyStateManager();
    Screen panel = new Screen(keyManager);

    frame.add(panel);
    frame.pack();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(false);
    frame.setVisible(true);

    panel.animate();
  }
}
