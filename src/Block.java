public class Block implements Collidable {
    Rectangle shape;
    @Override
    public Rectangle getCollisionRectangle() {
        return shape;
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
}
