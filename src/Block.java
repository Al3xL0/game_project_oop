import biuoop.DrawSurface;

import java.awt.*;
import java.util.Random;

public class Block implements Collidable {
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
        Line lines[] = shape.getLines();
        if(lines[0].isPointInLine(collisionPoint) || lines[1].isPointInLine(collisionPoint)) {
            currentVelocity.setDx(-currentVelocity.getDx());
        }
        if(lines[2].isPointInLine(collisionPoint) || lines[3].isPointInLine(collisionPoint)) {
            currentVelocity.setDy(-currentVelocity.getDy());
        }
        return currentVelocity;
    }

    public void drawOn(DrawSurface d) {
        int x,y,width,height;
        x = (int) shape.getUpperLeft().getX();
        y = (int) shape.getUpperLeft().getY();
        width = (int) shape.getWidth();
        height = (int) shape.getHeight();
        d.setColor(this.color);
        d.drawRectangle(x,y,width,height);
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
