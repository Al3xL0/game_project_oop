package gui;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import geometry.Line;
import geometry.Point;
import shapes.Collidable;

import java.awt.*;

public class Paddle implements Sprite, Collidable {
    private biuoop.KeyboardSensor keyboard;
    private Rectangle shape;
    private Color color;
    private double speed;
    public void moveLeft() {
        geometry.Point temp = shape.getUpperLeft();
        double x = temp.getX();
        double y = temp.getY();
        temp = new geometry.Point(x-speed,y);
        if(temp.getX() >= 20) {
            shape.setUpperLeft(temp);
        }
    }
    public void moveRight() {
        geometry.Point temp = shape.getUpperLeft();
        double x = temp.getX();
        double y = temp.getY();
        temp = new Point(x+speed,y);
        if(shape.getUpperRight().getX() <=780) {
            shape.setUpperLeft(temp);
        }
    }
    public Paddle(biuoop.KeyboardSensor keyboard) {
        this.keyboard = keyboard;
        this.color = Color.yellow;
        shape = new Rectangle(new geometry.Point(30,560),100,20);
        speed = 4.0;

    }

    // gui.Sprite
    public void timePassed() {
        if(keyboard.isPressed("a") || keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
           moveLeft();
        }
        if(keyboard.isPressed("d") || keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight();
        }
    }
    public void drawOn(DrawSurface d) {
        int x,y,width,height;
        x = (int) shape.getUpperLeft().getX();
        y = (int) shape.getUpperLeft().getY();
        width = (int) shape.getWidth();
        height = (int) shape.getHeight();
        d.setColor(this.color);
        d.drawRectangle(x,y,width,height);
        d.fillRectangle(x,y,width,height);
        Line lines[] = shape.getLines();
        for(Line line : lines) {
            geometry.Point start = line.start();
            geometry.Point end = line.end();
            d.setColor(Color.black);
            d.drawLine((int) start.getX(), (int) start.getY(), (int) end.getX(), (int) end.getY());

        }
    }
    // shapes.Collidable
    public Rectangle getCollisionRectangle() {
        return this.shape;
    }
    /*
     the size of the ball is
        width : 100
        height : 20
     */
    @Override
    public Velocity hit(geometry.Point collisionPoint, Velocity currentVelocity) {
        double paddleLeft = this.shape.getUpperLeft().getX();
        double regionWidth = this.shape.getWidth() / 5;
        double hitX = collisionPoint.getX();
        double speed = Math.sqrt(
            currentVelocity.getDx() * currentVelocity.getDx() +
            currentVelocity.getDy() * currentVelocity.getDy()
        );
        int region = (int)((hitX - paddleLeft) / regionWidth) + 1;
        double angle;
        switch (region) {
            case 1:
                angle = 300;
                break; // -60 deg
            case 2:
                angle = 330;
                break; // -30 deg
            case 3:
                angle = 0;
                break; // straight up
            case 4:
                angle = 30;
                break; // 30 deg
            case 5:
                angle = 60;
                break; // 60 deg
            default:
                angle = 0;
                break;
        }
        // Convert angle to radians
        double rad = Math.toRadians(angle);
        double dx = speed * Math.sin(rad);
        double dy = -speed * Math.cos(rad);
        return new Velocity(dx, dy);
    }

    // Add this paddle to the game.
    public void addToGame(Game g) {
        g.addSprite(this);
        g.addCollidable(this);
    }
    private int pickRegion(geometry.Point point) {
        int res =0;
        int x = (int) point.getX();
        if (x <= 20) {
            res = 1;
        } else if(x>20 && x<=40) {
            res = 2;
        } else if(x>40 && x<=60) {
            res = 3;
        } else if(x>60 && x<=80){
            res = 4;
        } else {
            res =5;
        }
        return res;
    }
}