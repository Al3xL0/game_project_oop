package shapes;
import game.events.HitListener;
import game.events.HitNotifier;
import geometry.*;
import game.GameEnvironment;
import biuoop.DrawSurface;
import game.*;

import java.util.ArrayList;
import java.util.List;

public class Ball implements Sprite , HitNotifier {
    private int radius;
    private Point center;
    private java.awt.Color color;
    private Velocity vel;
    private GameEnvironment gameEnv;
    private List<HitListener> hitListeners;
    private Block bottomBorder;
    // constructors
    public Ball(Point center, int r, java.awt.Color color) {
        this.center = center;
        this.radius = r;
        this.color = color;
    }
    public Ball(Point center, int r, java.awt.Color color, GameEnvironment gameEnvironment, Block bottomBorder) {
        this.center = center;
        this.radius = r;
        this.color = color;
        this.gameEnv = gameEnvironment;
        this.bottomBorder = bottomBorder;
        hitListeners = new ArrayList<>();
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
        final double EPSILON = 0.3;
        int maxCollisions = 100; // Prevent infinite loops
        // Define borders (adjust as needed)
        int left = 0, right = 800, top = 0, bottom = 600;

        for (int i = 0; i < maxCollisions; i++) {
            Point projectedCenter = this.vel.applyToPoint(this.center);
            Line traj = new Line(this.center, projectedCenter);
            CollisionInfo collInfo = gameEnv.getClosestCollision(traj);

            if (collInfo != null) {
                Point collPoint = collInfo.collisionPoint();

                Rectangle rect = collInfo.collisionObject().getCollisionRectangle();

                double blockLeft = rect.getUpperLeft().getX();
                double blockRight = blockLeft + rect.getWidth();
                double blockTop = rect.getUpperLeft().getY();
                double blockBottom = blockTop + rect.getHeight();

                double ballX = collPoint.getX();
                double ballY = collPoint.getY();

                double distToLeft = Math.abs(ballX - blockLeft);
                double distToRight = Math.abs(ballX - blockRight);
                double distToTop = Math.abs(ballY - blockTop);
                double distToBottom = Math.abs(ballY - blockBottom);

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
                this.vel = collInfo.collisionObject().hit(this, collPoint, vel);
                if(collInfo.collisionObject() == bottomBorder) {
                    notifyHit();
                }
                // After handling, continue to check for more collisions in the same frame
            } else {
                // No collision, move normally
                this.center = projectedCenter;
                break;
            }
        }
        // Border collision handling (after all block collisions)
        double nextX = center.getX();
        double nextY = center.getY();
        boolean isBottomBorder = false; // check if the border that was hitten is the bottom one
        boolean borderHit = false;
        if (nextX + radius > right) {
            nextX = right - radius;
            vel.setDx(-Math.abs(vel.getDx()));
            borderHit = true;
        } else if (nextX - radius < left) {
            nextX = left + radius;
            vel.setDx(Math.abs(vel.getDx()));
            borderHit = true;
        }
        if (nextY + radius > bottom) {
            nextY = bottom - radius;
            vel.setDy(-Math.abs(vel.getDy()));
            borderHit = true;
            isBottomBorder = true;
        } else if (nextY - radius < top) {
            nextY = top + radius;
            vel.setDy(Math.abs(vel.getDy()));
            borderHit = true;
        }
        if (borderHit) {
            center = new Point(nextX, nextY);
        }
        if(isBottomBorder) {
            System.out.println("hit bottom");
            this.notifyHit();
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
    }

    @Override
    public void timePassed() {
        moveOneStep();
    }

    @Override
    public void addToGame(Game game) {
        game.addSprite(this);
    }

    public void removeFromGame(Game game) {
        game.removeSprite(this);
    }
    @Override
    public void addHitListener(HitListener hl) {
        hitListeners.add(hl);
    }

    @Override
    public void removeHitListener(HitListener hl) {
        hitListeners.remove(hl);
    }

    void notifyHit() {
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        if(listeners.isEmpty()) {
            return;
        }
        for (HitListener hl : listeners) {
            hl.hitEvent(bottomBorder, this);
        }
    }

}
