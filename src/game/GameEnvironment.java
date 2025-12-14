package game;

import biuoop.DrawSurface;
import geometry.*;
import shapes.Collidable;
import java.util.ArrayList;

public class GameEnvironment {

    private ArrayList<Collidable> collidables = new ArrayList<Collidable>();
    // add the given collidable to the environment.
    public void addCollidable(Collidable c) {
        collidables.add(c);
    }
    public void removeCollidable(Collidable c) {
        collidables.remove(c);
    }
    public GameEnvironment() {

    }
    // Assume an object moving from line.start() to line.end().
    // If this object will not collide with any of the collidables
    // in this collection, return null. Else, return the information
    // about the closest collision that is going to occur.
    public CollisionInfo getClosestCollision(Line trajectory) {
        ArrayList<Point> points = new ArrayList<Point>();
        ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();
        Point res = null;
        Collidable colRes = null;
        double min = Double.POSITIVE_INFINITY;
        for(Collidable c : collidables) {
            ArrayList<Point> pointsRec = (ArrayList<Point>) c.getCollisionRectangle().intersectionPoints(trajectory);
            if(pointsRec != null) {
                for(Point point : pointsRec){
                double distToStart = trajectory.start().distance(point);
                double trajectoryLength = trajectory.length();
                
                // geometry.Point must be between start and end of trajectory
                if(distToStart > 1e-6 && distToStart < trajectoryLength && distToStart < min) {
                    min = distToStart;
                    colRes = c;
                    res = point;
                }
                }
            }
        }
        if(colRes == null) {
            return null;
        }
        return new CollisionInfo(trajectory, colRes, res);
    }
    public void drawWorld(DrawSurface d){
        for(Collidable collidable : collidables) {
            collidable.drawOn(d);

        }
    }
}