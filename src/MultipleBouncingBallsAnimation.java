import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class MultipleBouncingBallsAnimation {
    public static void main(String[] args) {
        // Check if exactly four arguments are provided


        ArrayList<Integer> balls = new ArrayList<Integer>();

        try {
            // Parse each command-line argument to an integer
            for(int i=0; i<args.length; i++){
                balls.add(Integer.parseInt(args[i]));
            }
            drawAnimation(balls);


        } catch (NumberFormatException e) {
            System.out.println("Invalid input: Please ensure all arguments are valid integers.");
        }
    }
    static public void drawAnimation(ArrayList<Integer> balls){
        int width = 1900;
        int height = 950;

        GUI gui = new GUI("title", width, height);
        Random rand = new Random();
        Sleeper sleeper = new Sleeper();
        float r,g,b;
        Ball[] ballArr = new Ball[balls.size()];
        double speed = rand.nextDouble(600, 900);
        for(int i = 0; i<balls.size(); i++ ){
            r = rand.nextFloat();
            g = rand.nextFloat();
            b = rand.nextFloat();
            if(i<=balls.size()/2) {
                ballArr[i] = new Ball(
                        new Point(rand.nextDouble(50,500), rand.nextDouble(50,500) ), balls.get(i), new Color(r,b,g)
                );
            } else if(i>balls.size()/2) {
                ballArr[i] = new Ball(
                        new Point(rand.nextDouble(450,600), rand.nextDouble(450,600) ), balls.get(i), new Color(r,b,g)
                );
            }
            if(ballArr[i].getSize() >= 50) {
                ballArr[i].setVelocity(360/50,speed/50);
            } else {
                ballArr[i].setVelocity(360/ballArr[i].getSize(), speed/ballArr[i].getSize());
            }
        }
        while (true) {


            DrawSurface d = gui.getDrawSurface();
            d.setColor(Color.GRAY);
            d.drawRectangle(50,50,450,450);
            d.fillRectangle(50,50,450,450);

            d.setColor(Color.GREEN);
            d.drawRectangle(450,450,150,150);
            d.fillRectangle(450,450,150,150);
            for(Ball ball : ballArr) {
                ball.drawOn(d);
            }
            gui.show(d);

            sleeper.sleepFor(50);  // wait for 50 milliseconds.
            for(int i=0; i<ballArr.length; i++) {
                if(i<=ballArr.length/2) {
                    ballArr[i].moveOneStep(500,500,50,50);
                } else if(i>ballArr.length/2) {
                    ballArr[i].moveOneStep(600,600,450,450);
                }
            }
        }
    }
}
