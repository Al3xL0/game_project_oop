import biuoop.GUI;
import biuoop.DrawSurface;

import java.util.Random;
import java.awt.Color;


public class AbstractArtDrawing {
    private final int LINES_COUNT = 10;

    public void drawScreen() {
        GUI gui = new GUI("Line drawing", 400, 300);
        DrawSurface d = gui.getDrawSurface();
        Line lines[] = new Line[LINES_COUNT];
        // draw the lines
        for (int i = 0; i < LINES_COUNT; i++) {
            lines[i] = generateRandomLine();
            drawLine(lines[i], d);
            d.setColor(Color.blue);
            d.drawCircle((int) lines[i].middle().getX(), (int) lines[i].middle().getY(),1);
        }
        // draw the intersection points
        for (int i = 0; i < LINES_COUNT; i++) {

            for (int j = i + 1; j < LINES_COUNT; j++){
                Point curr = lines[i].intersectionWith((lines[j]));
                if(curr != null) {
                    int x = (int) curr.getX();
                    int y = (int) curr.getY();
                    d.setColor(Color.red);
                    d.drawCircle(x,y,1);
                }

            }

        }
        gui.show(d);
    }

    void drawLine(Line l, DrawSurface d) {
        int x1, x2, y1, y2;
        x1 = (int) l.start().getX();
        y1 = (int) l.start().getY();
        x2 = (int) l.end().getX();
        y2 = (int) l.end().getY();
        d.setColor(Color.black);
        d.drawLine(x1, y1, x2, y2);
    }

    Line generateRandomLine() {
        Random rand = new Random();
        double x1, x2, y1, y2;
        x1 = rand.nextDouble(400) + 1;
        x2 = rand.nextDouble(400) + 1;
        y1 = rand.nextDouble(300) + 1;
        y2 = rand.nextDouble(300) + 1;
        System.out.println("x1: " + x1 + " y1: " + y1 + " x2: " + x2 + " y2: " + y2);
        return new Line(x1, y1, x2, y2);
    }

    public static void main(String[] args) {
        AbstractArtDrawing example = new AbstractArtDrawing();
        example.drawScreen();
    }
}
