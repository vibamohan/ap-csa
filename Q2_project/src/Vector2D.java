public class Vector2D implements Cloneable {
  private double x;
  private double y;

  public Vector2D(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public Vector2D() {
    this(0, 0);
  }

  public Vector2D add(Vector2D other) {
    this.x += other.x();
    this.y += other.y();
    return this;
  }

  public Vector2D subtract(Vector2D other) {
    this.x -= other.x();
    this.y -= other.y();
    return this;
  }

  public Vector2D scale(double scalar) {
    this.x *= scalar;
    this.y *= scalar;
    return this;
  }

  public Vector2D copy() {
    return new Vector2D(this.x, this.y);
  }

  public double length() {
    return Math.sqrt(x * x + y * y);
  }

  public Vector2D normalize() {
    double len = length();
    if (len != 0) scale(1 / len);
    return this;
  }

  public double distance(Vector2D other) {
    double dx = this.x - other.x();
    double dy = this.y - other.y();
    return Math.sqrt(dx * dx + dy * dy);
  }

  public double angle() {
    return Math.atan2(y, x);
  }

  public static Vector2D fromAngle(double angle) {
    return new Vector2D(Math.cos(angle), Math.sin(angle));
  }

  public Vector2D withAngle(double angle) {
    this.x = Math.cos(angle);
    this.y = Math.sin(angle);
    return this;
  }

  public Vector2D rotate(double radians) {
    double cos = Math.cos(radians);
    double sin = Math.sin(radians);
    double nx = x * cos - y * sin;
    double ny = x * sin + y * cos;
    this.x = nx;
    this.y = ny;
    return this;
  }

  public Vector2D reflect(Vector2D normal) {
    Vector2D n = normal.copy().normalize();
    double dot = this.dot(n);
    this.x = this.x - 2 * dot * n.x();
    this.y = this.y - 2 * dot * n.y();
    return this;
  }

  public double dot(Vector2D other) {
    return this.x * other.x() + this.y * other.y();
  }

  public Vector2D withX(double x) {
    this.x = x;
    return this;
  }

  public Vector2D withY(double y) {
    this.y = y;
    return this;
  }

  public double x() {
    return x;
  }

  public double y() {
    return y;
  }

  @Override
  public Vector2D clone() {
    try {
      return (Vector2D) super.clone();
    } catch (CloneNotSupportedException e) {
      return new Vector2D(this.x, this.y);
    }
  }

  @Override
  public String toString() {
    return "Vector2D(" + x + ", " + y + ")";
  }
}
