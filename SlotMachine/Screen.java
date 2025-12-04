import java.awt.*;
import java.awt.event.*;
import java.lang.runtime.*;
import java.time.Duration;
import javax.swing.*;

public class Screen extends JPanel implements ActionListener {
  private SlotMachine machine;
  private JButton spinButton;

  public Screen() {
    this.setLayout(null);
    this.setFocusable(true);

    machine = new SlotMachine(100);

    spinButton = new JButton("Spin");
    spinButton.setBounds(50, 400, 100, 50);
    spinButton.addActionListener(this);
    this.add(spinButton);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    machine.drawMe(g);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == spinButton && machine.getBalance() > 0) {
      machine.spin();

      if (machine.getBalance() <= 0) {
        spinButton.setEnabled(false);
        JOptionPane.showMessageDialog(this, "Ran out of tokens, you lost!");
        try {
          Thread.sleep(Duration.ofSeconds(5));
        } catch (InterruptedException ie) {
          ie.printStackTrace();
          System.exit(0);
        } catch (Exception ee) {
          ee.printStackTrace();
          System.exit(0);
        }
        System.exit(0);
      }

      repaint();
    }
  }
}
