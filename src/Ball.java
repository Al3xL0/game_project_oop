import java.awt.Color;


import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
import org.w3c.dom.css.Rect;

public class Ball {
    private int radius;
    private Point center;
    private java.awt.Color color;
    private Velocity vel;
    private GameEnvironment gameEnv;
    private Point lastCollision;
    // constructors
    public Ball(Point center, int r, java.awt.Color color) {
        this.center = center;
        this.radius = r;
        this.color = color;
    }
    public Ball(Point center, int r, java.awt.Color color, GameEnvironment gameEnvironment) {
        this.center = center;
        this.radius = r;
        this.color = color;
        this.gameEnv = gameEnvironment;
    }
    public Ball(double x, double y, int r, java.awt.Color color){
        this.center = new Point(x,y);
        this.radius = r;
        this.color = color;
    }

    // accessors
    public int getX() {
        return (int) this.center.getX();
    };
    public int getY() {
        return (int) this.center.getY();
    }
    public int getSize() {
        return (int) (Math.PI * radius * radius);
    }
    public java.awt.Color getColor() {
        return this.color;
    }
    public void setVelocity(Velocity v){
        this.vel = v;
    }
    public void setVelocity(double dx, double dy){
        this.vel = new Velocity(dx,dy);
    }
    public Velocity getVelocity(){
        return this.vel;
    }

    public void moveOneStep() {
        final double EPSILON = 1e-1;

        if (this.vel == null) {
            return;
        }

        Point projectedCenter = this.vel.applyToPoint(this.center);
        Line traj = new Line(this.center, projectedCenter);
        CollisionInfo collInfo = gameEnv.getClosestCollision(traj);

        if (collInfo != null) {
            Point collPoint = collInfo.collisionPoint();
            System.out.println("col point: " + collPoint.getX() + ", " + collPoint.getY());

            Rectangle rect = collInfo.collisionObject().getCollisionRectangle();
            double blockLeft = rect.getUpperLeft().getX();
            double blockRight = blockLeft + rect.getWidth();
            double blockTop = rect.getUpperLeft().getY();
            double blockBottom = blockTop + rect.getHeight();

            double ballX = collPoint.getX();
            double ballY = collPoint.getY();

            // Calculate distance from collision point to each edge
            double distToLeft = Math.abs(ballX - blockLeft);
            double distToRight = Math.abs(ballX - blockRight);
            double distToTop = Math.abs(ballY - blockTop);
            double distToBottom = Math.abs(ballY - blockBottom);

            // Find which edge was hit (closest distance)
            double minDist = Math.min(Math.min(distToLeft, distToRight),
                    Math.min(distToTop, distToBottom));

            double xDir = 0, yDir = 0;

            if (minDist == distToTop) {
                yDir = -(radius + EPSILON);
            } else if (minDist == distToBottom) {
                yDir = radius + EPSILON;
            } else if (minDist == distToLeft) {
                xDir = -(radius + EPSILON);
            } else if (minDist == distToRight) {
                xDir = radius + EPSILON;
            }

            this.center = new Point(collPoint.getX() + xDir, collPoint.getY() + yDir);
            this.vel = collInfo.collisionObject().hit(collPoint, vel);
            System.out.println("vel" + vel.getDx() + "," + vel.getDy());

        } else {
            this.center = projectedCenter;
        }
    }



    public void moveOneStep(int right, int bottom, int left, int top) {
        double nextX = center.getX() + vel.getDx();
        double nextY = center.getY() + vel.getDy();

        // פגיעה בקירות שמאל/ימין
        if (nextX + radius > right) {
            nextX = right - radius;
            vel.setDx(-vel.getDx());
        } else if (nextX - radius < left) {
            nextX = left + radius;
            vel.setDx(-vel.getDx());
        }

        // פגיעה בקירות עליון/תחתון
        if (nextY + radius > bottom) {
            nextY = bottom - radius;
            vel.setDy(-vel.getDy());
        } else if (nextY - radius < top) {
            nextY = top + radius;
            vel.setDy(-vel.getDy());
        }

        center = new Point(nextX, nextY);
    }

    // draw the ball on the given DrawSurface
    public void drawOn(DrawSurface surface){
        surface.setColor(this.color);
        surface.drawCircle(this.getX(), this.getY(), this.radius);
        surface.fillCircle(this.getX(), this.getY(), this.radius);
    };

}
