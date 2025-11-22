// Velocity specifies the change in position on the `x` and the `y` axes.
public class Velocity {
    private double dx;
    private double dy;
    // constructor
    public Velocity(double dx, double dy){
        this.dx = dx;
        this.dy = dy;
    }
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double dx = angle;
        double dy = speed;
        return new Velocity(dx, dy);
    }
    public double getDx() {
        return this.dx;
    }
    public double getDy() {
        return this.dy;
    }
    // Take a point with position (x,y) and return a new point
    // with position (x+dx, y+dy)
    public Point applyToPoint(Point p){
      double x,y;
      x = this.dx + p.getX();
      y = this.dy + p.getY();
      return new Point(x,y);
    }

    public void setDx(double v) {
        this.dx = v;
    }

    public void setDy(double v) {
        this.dy = v;
    }
}