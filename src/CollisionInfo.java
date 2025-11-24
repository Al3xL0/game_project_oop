import java.util.ArrayList;

public class CollisionInfo {
    private Line line;
    private Collidable collidable;
    private Rectangle shape;
    public CollisionInfo(Line line , Collidable collidable) {
        this.line = line;
        this.collidable = collidable;
        this.shape = collidable.getCollisionRectangle();
    }
    // the point at which the collision occurs.
    public Point collisionPoint() {
        // shape.intersectionPoints should return only one element, because the line ends when it collides with the object
        ArrayList<Point> points = (ArrayList<Point>) shape.intersectionPoints(line);
        return points.get(0);
    }

    // the collidable object involved in the collision.
    public Collidable collisionObject() {
        return collidable;
    }
}