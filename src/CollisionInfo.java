import java.util.ArrayList;

public class CollisionInfo {
    private Line line;
    private Collidable collidable;
    private Rectangle shape;
    private Point point;
    public CollisionInfo(Line line , Collidable collidable, Point point) {
        this.line = line;
        this.collidable = collidable;
        this.shape = collidable.getCollisionRectangle();
        this.point = point;
    }
    // the point at which the collision occurs.
    public Point collisionPoint() {
        return this.point;
    }

    // the collidable object involved in the collision.
    public Collidable collisionObject() {
        return collidable;
    }
}