import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.function.DoubleSupplier;

public class Controllers {

  public static class PIDController {
    private double kp, ki, kd;
    private boolean continuous = false;
    private double integral = 0;
    private double previousError = 0;
    private double setpoint;

    public PIDController(double kp, double ki, double kd) {
      this.kp = kp;
      this.ki = ki;
      this.kd = kd;
    }

    public void setContinuous(boolean continuous) {
      this.continuous = continuous;
    }

    public void setSetpoint(double setpoint) {
      this.setpoint = setpoint;
    }

    public double calculate(double measurement, double dt) {
      double error = setpoint - measurement;
      if (continuous) error = wrapAngle(error);

      integral += error * dt;
      double derivative = (error - previousError) / dt;
      previousError = error;

      return kp * error + ki * integral + kd * derivative;
    }
  }

  public static double wrapAngle(double a) {
    a = (a + Math.PI) % (2 * Math.PI);
    if (a < 0) a += 2 * Math.PI;
    return a - Math.PI;
  }

  public static class Controller {
    public final DoubleSupplier xControl;
    public final DoubleSupplier yControl;
    public final DoubleSupplier rotControl;

    public Controller(DoubleSupplier x, DoubleSupplier y, DoubleSupplier rot) {
      this.xControl = x;
      this.yControl = y;
      this.rotControl = rot;
    }
  }

  public static Controller PLAYER_WITH_MOUSE_ROTATION(GameState gameState, Screen screen) {

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

    PIDController xPID = new PIDController(1.5, 0.0, 0.2);
    xPID.setContinuous(false);
    PIDController yPID = new PIDController(1.5, 0.0, 0.2);
    yPID.setContinuous(false);
    PIDController headingPID = new PIDController(3.0, 0.0, 0.25);
    headingPID.setContinuous(true);

    final double[] currentHeading = {0};
    final double[] lastTime = {System.nanoTime() / 1_000_000_000.0};
    final double[] targetX = {gameState.player().x()};
    final double[] targetY = {gameState.player().y()};

    final Runnable pollKeyboard =
        () -> {
          int n = gameState.keysLength();
          double step = 10.0;
          for (int i = 0; i < n; i++) {
            KeyState key = gameState.keys(i);
            if (key.pressed()) {
              switch (key.key()) {
                case java.awt.event.KeyEvent.VK_UP -> targetY[0] -= step;
                case java.awt.event.KeyEvent.VK_DOWN -> targetY[0] += step;
                case java.awt.event.KeyEvent.VK_LEFT -> targetX[0] -= step;
                case java.awt.event.KeyEvent.VK_RIGHT -> targetX[0] += step;
              }
            }
          }
        };

    return new Controller(
        () -> {
          double now = System.nanoTime() / 1_000_000_000.0;
          double dt = now - lastTime[0];
          if (dt <= 0) dt = 0.001;
          lastTime[0] = now;

          pollKeyboard.run();
          xPID.setSetpoint(targetX[0]);
          return xPID.calculate(gameState.player().x(), dt);
        },
        () -> {
          double now = System.nanoTime() / 1_000_000_000.0;
          double dt = now - lastTime[0];
          if (dt <= 0) dt = 0.001;
          lastTime[0] = now;

          pollKeyboard.run();
          yPID.setSetpoint(targetY[0]);
          return yPID.calculate(gameState.player().y(), dt);
        },
        () -> {
          double now = System.nanoTime() / 1_000_000_000.0;
          double dt = now - lastTime[0];
          if (dt <= 0) dt = 0.001;
          lastTime[0] = now;

          double dx = mouseX[0] - gameState.player().x();
          double dy = mouseY[0] - gameState.player().y();
          double targetHeading = Math.atan2(dy, dx);

          headingPID.setSetpoint(targetHeading);
          double vel = headingPID.calculate(currentHeading[0], dt);

          currentHeading[0] = wrapAngle(currentHeading[0] + vel * dt);
          return currentHeading[0];
        });
  }
}
