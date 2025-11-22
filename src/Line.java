public class Line {
    private Point start;
    private Point end;
    // using the line equation y = mx + b to find intersection point
    private double b;
    private double slope;
    private boolean isXup;
    private boolean isYup;

    // constructors
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
        clacSlope();
        this.b = this.end.getY() - this.slope * this.end.getX();
        if (start.getX() <= end.getX()) {
            this.isXup = true;
        } else {
            this.isXup = false;
        }
        if (start.getY() <= end.getY()) {
            this.isYup = true;
        } else {
            this.isYup = false;
        }
    }

    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
        clacSlope();
        this.b = this.end.getY() - this.slope * this.end.getX();
        if (x1 <= x2) {
            this.isXup = true;
        } else {
            this.isXup = false;
        }
        if (y1 <= y2) {
            this.isYup = true;
        } else {
            this.isYup = false;
        }
    }

    // Return the length of the line
    public double length() {
        return start.distance(end);
    }

    // Returns the middle point of the line
    public Point middle() {
        double x = (start.getX() + end.getX()) / 2;
        double y = (start.getY() + end.getY()) / 2;
        return new Point(x, y);
    }

    // Returns the start point of the line
    public Point start() {
        return this.start;
    }

    // Returns the end point of the line
    public Point end() {
        return this.end;
    }

    // Returns true if the lines intersect, false otherwise
    public boolean isIntersecting(Line other) {
        boolean ans = false;
        if(intersectionWith(other) != null) {
            ans = true;
        } else if(this.equals(other)) {
          if(this.isPointInLine(other.end) || this.isPointInLine(other.start) || other.isPointInLine(this.start) || other.isPointInLine(this.end)){
              ans =true;
          }
        }
        return ans;
    }

    public boolean isPointInLine(Point point) {
        boolean ans = true;
        if(isXup) {
            if(point.getX()<start.getX() || point.getX()>end.getX()) {
                ans =false;
            }
        }
        else {
            if(point.getX()>start.getX() || point.getX()<end.getX()) {
                ans =false;
            }
        }
        if(isYup) {
            if(point.getY()<start.getY() || point.getY()>end.getY()) {
                ans =false;
            }
        }
        else {
            if(point.getY()>start.getY() || point.getX()<end.getY()) {
                ans =false;
            }
        }
        return ans;
    }
    // Returns the intersection point if the lines intersect,
    // and null otherwise.
    public Point intersectionWith(Line other) {
        if(this.slope == other.getSlope()) {
            return null ;
        }
        Point res;
        double x, y;
        x = (other.getB() - this.b) / (this.slope - other.getSlope());

        y = this.slope * x + this.b;
        res = new Point(x, y);
        boolean checkX = this.isXup ?
                (this.end.getX() >= x && this.start.getX() <= x) : (this.end.getX() <= x && this.start.getX() >= x);
        boolean checkY = this.isYup ?
                (this.end.getY() >= y && this.start.getY() <= y) : (this.end.getY()<=y && this.start.getY()>=y);
        if (checkX && checkY) {
            return res;
        }
        return null;
    }

    // equals -- return true is the lines are equal, false otherwise
    public boolean equals(Line other) {
        return (this.slope == other.getSlope() && this.b == other.getB());
    }

    public double getB() {
        return this.b;
    }

    public double getSlope() {
        return this.slope;
    }

    private void clacSlope() {
        if(this.start.getX() != this.end.getX())
            this.slope = (this.end.getY() - this.start.getY()) / (this.end.getX() - this.start.getX());
        else 
            this.slope = Double.NaN;

    }

}