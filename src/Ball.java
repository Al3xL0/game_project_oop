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
        // אם אין מהירות עדיין, אין מה לזוז
        if (this.vel == null) {
            return;
        }

        // הנקודה שהכדור היה מגיע אליה בלי התנגשות
        Point projectedCenter = this.vel.applyToPoint(this.center);

        // המסלול מהמרכז הנוכחי לנקודה הבאה
        Line traj = new Line(this.center, projectedCenter);

        // בודקים אם יש אובייקט בדרך
        CollisionInfo collInfo = gameEnv.getClosestCollision(traj);

        // אין התנגשות – זזים כרגיל
        if (collInfo == null) {
            System.out.println("hey");
            this.center = projectedCenter;
            return;
        }

        // יש התנגשות – מטפלים בה
        Point collisionPoint = collInfo.collisionPoint();

        double dx = this.vel.getDx();
        double dy = this.vel.getDy();
        double epsilon = 0.0001; // מספר קטן מאוד

        // ממקמים את המרכז קצת לפני נקודת הפגיעה (בכיוון ההפוך לצעד)
        double newX = collisionPoint.getX() - dx * epsilon;
        double newY = collisionPoint.getY() - dy * epsilon;
        this.center = new Point(newX, newY);

        // מעדכנים מהירות לפי האובייקט שפגענו בו
        this.vel = collInfo.collisionObject().hit(collisionPoint, this.vel);
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
