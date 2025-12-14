package shapes;

import biuoop.DrawSurface;
import game.events.PrintingHitListener;
import geometry.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import game.Sprite;
import game.Game;
import game.events.HitNotifier;
import game.events.HitListener;

public class Block implements Collidable, Sprite, HitNotifier {
    Rectangle shape;
    Color color;
    boolean isBorder;
    private List<HitListener> hitListeners;
    @Override
    public Rectangle getCollisionRectangle() {
        return shape;
    }

    public Block(Rectangle rec, Color color){
        this.shape = rec;
        this.color = color;
        this.isBorder =false;
        hitListeners = new ArrayList<>();
    }
    public Block(Rectangle rec, Color color, boolean isBorder){
        this.shape = rec;
        this.color = color;
        this.isBorder =isBorder;
        hitListeners = new ArrayList<>();

    }
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        Rectangle rect = this.getCollisionRectangle();
        Velocity res;
        double blockLeft = rect.getUpperLeft().getX();
        double blockRight = blockLeft + rect.getWidth();
        double blockTop = rect.getUpperLeft().getY();
        double blockBottom = blockTop + rect.getHeight();

        double ballX = collisionPoint.getX();
        double ballY = collisionPoint.getY();

        // Calculate distance to each edge
        double distToLeft = Math.abs(ballX - blockLeft);
        double distToRight = Math.abs(ballX - blockRight);
        double distToTop = Math.abs(ballY - blockTop);
        double distToBottom = Math.abs(ballY - blockBottom);

        // Find closest edge - that's which one was hit
        double minDist = Math.min(Math.min(distToLeft, distToRight),
                Math.min(distToTop, distToBottom));

            // Use velocity direction as tiebreaker for corners
        if (Math.abs(distToLeft - distToRight) < 1e-3) {
            // Hitting left or right - reverse X
            res = new Velocity(-currentVelocity.getDx(), currentVelocity.getDy());
        } else if (Math.abs(distToTop - distToBottom) < 1e-3) {
            // Hitting top or bottom - reverse Y
            res = new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
        } else if (minDist == distToLeft || minDist == distToRight) {
            // Hit left or right wall - reverse X velocity
            res = new Velocity(-currentVelocity.getDx(), currentVelocity.getDy());
        } else {
            // Hit top or bottom wall - reverse Y velocity
            res = new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
        }

        this.notifyHit(hitter);
        return res;
    }

    public void drawOn(DrawSurface d) {
        int x,y,width,height;
        x = (int) shape.getUpperLeft().getX();
        y = (int) shape.getUpperLeft().getY();
        width = (int) shape.getWidth();
        height = (int) shape.getHeight();
        d.setColor(this.color);
        d.drawRectangle(x,y,width,height);
        d.fillRectangle(x,y,width,height);
        Line lines[] = shape.getLines();
        for(Line line : lines) {
            Point start = line.start();
            Point end = line.end();
            d.setColor(Color.black);
            d.drawLine((int) start.getX(), (int) start.getY(), (int) end.getX(), (int) end.getY());

        }
    }

    @Override
    public void timePassed() {
        
    }

    @Override
    public void addToGame(Game game) {
        game.addCollidable(this);
        game.addSprite(this);
    }

    private Color generateRandomColor() {
        Random rand = new Random();
        float r,g,b;
        r = rand.nextFloat();
        g = rand.nextFloat();
        b = rand.nextFloat();
        return new Color(r,g,b);
    }

    public void removeFromGame(Game g) {
        if(!isBorder) {
            g.removeCollidable(this);
            g.removeSprite(this);
        }
    }

    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        if(listeners.isEmpty()) {
            return;
        }
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }
    public boolean getIsBorder() {
        return isBorder;
    }
    @Override
    public void addHitListener(HitListener hl) {
        hitListeners.add(hl);
    }

    @Override
    public void removeHitListener(HitListener hl) {
        hitListeners.remove(hl);
    }
}
