import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

public class BouncingBallAnimation {
    public static void main(String[] args) {
        // Check if exactly four arguments are provided
        if (args.length != 4) {
            System.out.println("Please provide exactly four integer arguments.");
            return; // Exit the program
        }

        int x, y, dx, dy;

        try {
            // Parse each command-line argument to an integer
            x = Integer.parseInt(args[0]);
            y = Integer.parseInt(args[1]);
            dx = Integer.parseInt(args[2]);
            dy = Integer.parseInt(args[3]);
            drawAnimation(new Point(x, y), dx, dy);

        } catch (NumberFormatException e) {
            System.out.println("Invalid input: Please ensure all arguments are valid integers.");
        }
    }

    static public void drawAnimation(Point start, double dx, double dy) {
        GUI gui = new GUI("title", 200, 400);
        Sleeper sleeper = new Sleeper();
        Ball ball = new Ball(start.getX(), start.getY(), 30, java.awt.Color.BLACK);
        ball.setVelocity(dx, dy);
        while (true) {


            DrawSurface d = gui.getDrawSurface();
            ball.drawOn(d);
            gui.show(d);

            sleeper.sleepFor(50);  // wait for 50 milliseconds.
            ball.moveOneStep();
        }
    }
}
