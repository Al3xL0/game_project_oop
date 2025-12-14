package game;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
import game.events.BallRemover;
import game.events.BlockRemover;
import game.events.Counter;
import geometry.*;
import shapes.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private SpriteCollection sprites;
    private GameEnvironment gameEnv;
    private Sleeper sleeper;
    private GUI gui;
    // tracking the status of blocks in the game
    private Counter blockCounter;
    private BlockRemover blockRemover;

    // tracking the status of balls in the game
    private Counter ballCounter;
    private BallRemover ballRemover;
    // track the bottom border inorder to remove balls
    private Block bottomBorder;

    public Game() {
        initialize();
    }
    public void addCollidable(Collidable c) {
        gameEnv.addCollidable(c);
    }
    public void addSprite(Sprite s) {
        sprites.addSprite(s);
    }

    // Initialize a new game: create the Blocks and shapes.Ball (and gui.Paddle)
    // and add them to the game.
    public void initialize() {
        // init gui, gameEnv and spriteCollection
        this.gui = new GUI("title", 800 , 600);
        this.gameEnv = new GameEnvironment();
        this.sprites = new SpriteCollection();

        // counter and remover for blocks
        this.blockCounter = new Counter();
        this.blockRemover = new BlockRemover(this, blockCounter);

        // counter and remover for balls
        this.ballCounter = new Counter();
        ballCounter.increase(3);
        this.ballRemover = new BallRemover(this,  ballCounter);
        // init the player
        biuoop.KeyboardSensor keyboard = gui.getKeyboardSensor();
        Paddle paddle = new Paddle(keyboard);
        generateBorders(gameEnv);
        initLevel();
        Ball ball = new Ball(new geometry.Point(850/2,1800/4),5, Color.black, gameEnv, this.bottomBorder);
        Ball ball2 = new Ball(new geometry.Point(800/2, 1800/4),5, Color.black, gameEnv, this.bottomBorder);
        Ball ball3 = new Ball(new geometry.Point(700/2, 1800/4),5, Color.black, gameEnv, this.bottomBorder);
        ball.setVelocity(new Velocity(2,2));
        ball2.setVelocity(new Velocity(2,2));
        ball3.setVelocity(new Velocity(2,2));

        paddle.addToGame(this);
        ball.addToGame(this);
        ball2.addToGame(this);
        ball3.addToGame(this);
        ball.addHitListener(ballRemover);
        ball2.addHitListener(ballRemover);
        ball3.addHitListener(ballRemover);
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
            if(blockCounter.getValue() == 0 || ballCounter.getValue() == 0) {
                break;
            }
        }
        gui.close();
    }

    private void generateBorders(GameEnvironment gameEnv) {
        Block[] borders = new Block[4];
        borders[0] = new Block(new Rectangle(new geometry.Point(0,0),800 ,20), Color.gray, true);
        borders[1] = new Block(new Rectangle(new geometry.Point(0,0),20,600), Color.gray, true);
        borders[2] = new Block(new Rectangle(new geometry.Point(0,580),800,20),Color.gray, true);
        borders[3] = new Block(new Rectangle(new geometry.Point(780,0),20,600), Color.gray, true);
        this.bottomBorder = borders[2];
        for(Block block : borders) {
            block.addToGame(this);
        }
    }
    private void initLevel() {
        double startx = 80.0;
        double starty = 100.0;
        List<Block> blockList = new ArrayList<>();
        while(startx + 100 < 800) {
            Block block = new Block(new Rectangle(new geometry.Point(startx,starty),100,20),Color.gray);
            block.addToGame(this);
            blockList.add(block);
            startx +=100;
        }
        startx = 180;
        starty += 20;
        while(startx + 100 <=780) {
            Block block = new Block(new Rectangle(new geometry.Point(startx,starty),100,20),Color.green);
            block.addToGame(this);
            blockList.add(block);
            startx +=100;
        }
        startx = 280;
        starty += 20;
        while(startx + 100 <=780) {
            Block block = new Block(new Rectangle(new geometry.Point(startx,starty),100,20),Color.red);
            block.addToGame(this);
            blockList.add(block);
            startx +=100;
        }
        startx = 380;
        starty += 20;
        while(startx + 100 <=780) {
            Block block = new Block(new Rectangle(new Point(startx,starty),100,20),Color.yellow);
            block.addToGame(this);
            blockList.add(block);
            startx +=100;
        }
        startx = 480;
        starty += 20;
        while(startx + 100 <=780) {
            Block block = new Block(new Rectangle(new geometry.Point(startx,starty),100,20),Color.white);
            block.addToGame(this);
            blockList.add(block);
            startx +=100;
        }
        startx = 580;
        starty += 20;
        while(startx + 100 <=780) {
            Block block = new Block(new Rectangle(new geometry.Point(startx,starty),100,20),Color.cyan);
            block.addToGame(this);
            blockList.add(block);
            startx +=100;
        }
        for(Block block : blockList) {
            blockCounter.increase(1);
            block.addHitListener(this.blockRemover);
        }
    }

    // the following two methods will help us to delete an object from the game
    public void removeCollidable(Collidable c){
        gameEnv.removeCollidable(c);
    }
    public void removeSprite(Sprite s) {
        sprites.removeSprite(s);
    }
}
