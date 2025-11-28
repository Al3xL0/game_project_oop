import java.util.ArrayList;
import java.util.List;

public class Rectangle {

    private Point upperLeft;
    private Point upperRight;
    private Point downLeft;
    private Point downRight;
    private double width;
    private double height;
    // Create a new rectangle with location and width/height.
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = new Point(upperLeft.getX(), upperLeft.getY());
        this.width = width;
        this.height = height;
        initCorners();
    }

    // Return a (possibly empty) List of intersection points
    // with the specified line.
    public List<Point> intersectionPoints(Line line) {
        List<Point> points = new ArrayList<>();
        Line lines[] = new Line[4];
        lines[0] = new Line(upperLeft, upperRight);
        lines[1] = new Line(downLeft,downRight);
        lines[2] = new Line(downLeft,upperLeft);
        lines[3] = new Line(downRight,upperRight);
        /*
         note for later:
           יכול להיות שתהיה תקלה בהליך נקודות החיתוך בגלל שהמרצה איל אמר להתחייס לקווים זהים כאילו אין בניהם נקודות חיתוך
           בתוך הפונקציה המחזירה נקודות חיתוך, ממליץ לבדוק את זה.
           בנוסף אולי ניתו לפתור את זה ע"י החזרת נקודה קצה כלשהי
         */
        for(int i=0; i<lines.length; i++) {
            Point point = line.intersectionWith(lines[i]);
            if(point != null) {

                points.add(point);
            }
        }

        return points;

    }

    // Return the width and height of the rectangle
    public double getWidth() {
        return this.width;
    }
    public double getHeight() {
        return this.height;
    }

    // Returns the upper-left point of the rectangle.
    public Point getUpperLeft() {
        return this.upperLeft;
    }
    public void setUpperLeft(Point upperLeft) {
        this.upperLeft = upperLeft;
        initCorners();
    }
    /**
     * @return this array contains upperLine (line[0]), downLine (line[1]) left and then right.
     */
    public Line[] getLines() {
        Line lines[] = new Line[4];
        lines[0] = new Line(upperLeft, upperRight);
        lines[1] = new Line(downLeft,downRight);
        lines[2] = new Line(downLeft,upperLeft);
        lines[3] = new Line(downRight,upperRight);
        return lines;
    }
    private void initCorners() {
        upperRight = new Point(upperLeft.getX() + width, upperLeft.getY());
        downLeft   = new Point(upperLeft.getX(), upperLeft.getY() + height);
        downRight  = new Point(upperLeft.getX() + width, upperLeft.getY() + height);
    }

    public Point getUpperRight() {
        return upperRight;
    }
}