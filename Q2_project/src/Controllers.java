import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.function.DoubleSupplier;

public class Controllers {

  public static class Controller {
    public final DoubleSupplier xControl;
    public final DoubleSupplier yControl;
    public final DoubleSupplier rotControl; // optional

    public Controller(DoubleSupplier x, DoubleSupplier y, DoubleSupplier rot) {
      this.xControl = x;
      this.yControl = y;
      this.rotControl = rot;
    }
  }

  public static Controller USER_KEYBOARD(KeyStateManager keys) {
    return new Controller(
        () -> {
          double x = 0;
          if (keys.isPressed(java.awt.event.KeyEvent.VK_A)) x -= 1;
          if (keys.isPressed(java.awt.event.KeyEvent.VK_D)) x += 1;
          return x;
        },
        () -> {
          double y = 0;
          if (keys.isPressed(java.awt.event.KeyEvent.VK_W)) y -= 1;
          if (keys.isPressed(java.awt.event.KeyEvent.VK_S)) y += 1;
          return y;
        },
        () -> 0);
  }

  public static Controller USER_MOUSE(Screen screen) {
    final double[] mouseX = {screen.getWidth() / 2.0};
    final double[] mouseY = {screen.getHeight() / 2.0};

    screen.addMouseMotionListener(
        new MouseMotionListener() {
          @Override
          public void mouseMoved(MouseEvent e) {
            mouseX[0] = e.getX();
            mouseY[0] = e.getY();
          }

          @Override
          public void mouseDragged(MouseEvent e) {
            mouseX[0] = e.getX();
            mouseY[0] = e.getY();
          }
        });

    return new Controller(() -> mouseX[0], () -> mouseY[0], () -> 0);
  }
}
