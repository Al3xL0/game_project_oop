import biuoop.DrawSurface;
import biuoop.GUI;

import java.awt.*;

public class Game {
    public static void main(String[] args) {
            GUI gui = new GUI("title", 1800, 1700);
        GameEnvironment gameEnv = new GameEnvironment();
        Ball ball = new Ball(new Point(480/4,900/4),30, Color.black, gameEnv);
        ball.setVelocity(new Velocity(2,2));
        generateBorders(gameEnv);
        while(true){
            DrawSurface d = gui.getDrawSurface();
            gameEnv.drawWorld(d);
            ball.drawOn(d);
            gui.show(d);
            ball.moveOneStep();
        }
    }
    private static void generateBorders(GameEnvironment gameEnv) {
        Block[] borders = new Block[4];
        borders[0] = new Block(new Rectangle(new Point(0,0),480,10), Color.black);
        borders[1] = new Block(new Rectangle(new Point(0,0),10,900), Color.black);
        borders[2] = new Block(new Rectangle(new Point(0,979),480,10),Color.black);
        borders[3] = new Block(new Rectangle(new Point(479,0),10,900), Color.black);
        for(Block block : borders) {
            gameEnv.addCollidable(block);
        }
    }
}
