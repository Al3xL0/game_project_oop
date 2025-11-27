import biuoop.DrawSurface;

import java.awt.*;
import java.util.Random;

public class Block implements Collidable, Sprite  {
    Rectangle shape;
    Color color;
    @Override
    public Rectangle getCollisionRectangle() {
        return shape;
    }
    public Block(Rectangle rec) {
        this.shape = rec;
        this.color = generateRandomColor();
    }
    public Block(Rectangle rec, Color color){
        this.shape = rec;
        this.color = color;
    }
    @Override
    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
        Rectangle rect = this.getCollisionRectangle();
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
            return new Velocity(-currentVelocity.getDx(), currentVelocity.getDy());
        } else if (Math.abs(distToTop - distToBottom) < 1e-3) {
            // Hitting top or bottom - reverse Y
            return new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
        } else if (minDist == distToLeft || minDist == distToRight) {
            // Hit left or right wall - reverse X velocity
            return new Velocity(-currentVelocity.getDx(), currentVelocity.getDy());
        } else {
            // Hit top or bottom wall - reverse Y velocity
            return new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
        }
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
}
