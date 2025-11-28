import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
import java.awt.*;

public class Game {
    private SpriteCollection sprites;
    private GameEnvironment gameEnv;
    private Sleeper sleeper;
    private GUI gui;
    public Game() {
        initialize();
    }
    public void addCollidable(Collidable c) {
        gameEnv.addCollidable(c);
    }
    public void addSprite(Sprite s) {
        sprites.addSprite(s);
    }

    // Initialize a new game: create the Blocks and Ball (and Paddle)
    // and add them to the game.
    public void initialize() {
        // init gui, gameEnv and spriteCollection
        this.gui = new GUI("title", 800 , 600);
        this.gameEnv = new GameEnvironment();
        this.sprites = new SpriteCollection();
        // init the player
        biuoop.KeyboardSensor keyboard = gui.getKeyboardSensor();
        Paddle paddle = new Paddle(keyboard);

        Ball ball = new Ball(new Point(140/4,140/4),5, Color.black, gameEnv);
        ball.setVelocity(new Velocity(2,2));
        generateBorders(gameEnv);
        paddle.addToGame(this);
        ball.addToGame(this);
        this.sleeper = new Sleeper();
    }

    // Run the game -- start the animation loop.
    public void run() {
        //...
        int framesPerSecond = 60;
        int millisecondsPerFrame = 1000 / framesPerSecond;
        while (true) {
            long startTime = System.currentTimeMillis(); // timing

            DrawSurface d = gui.getDrawSurface();
            d.setColor(Color.blue);
            d.drawRectangle(0,0,800,600);
            d.fillRectangle(0,0,800,600);
            this.sprites.drawAllOn(d);
            gui.show(d);
            this.sprites.notifyAllTimePassed();

            // timing
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }

    private void generateBorders(GameEnvironment gameEnv) {
        Block[] borders = new Block[4];
        borders[0] = new Block(new Rectangle(new Point(0,0),800 ,20), Color.gray);
        borders[1] = new Block(new Rectangle(new Point(0,0),20,600), Color.gray);
        borders[2] = new Block(new Rectangle(new Point(0,580),800,20),Color.gray);
        borders[3] = new Block(new Rectangle(new Point(780,0),20,600), Color.gray);
        for(Block block : borders) {
            block.addToGame(this);
        }
    }
}
