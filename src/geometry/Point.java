package geometry;

public class Point {
    private double x;
    private double y;
    // constructor
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // distance -- return the distance of this point to the other point
    public double distance(Point other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }

    // equals -- return true is the points are equal, false otherwise
    public boolean equals(Point other) {
        boolean ans = false;
        if(other == null) {
            return ans;
        }
        if(this.x == other.getX() && this.y == other.getY()){
            ans = true;
        }
        return ans;
    }

    // Return the x and y values of this point
    public double getX() {
        return this.x;
    }
    public double getY() {
        return this.y;
    }
}