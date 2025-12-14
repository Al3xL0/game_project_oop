package shapes;

import biuoop.DrawSurface;
import game.Game;
import game.Sprite;
import game.events.Counter;

import java.awt.*;

public class ScoreIndicator implements Sprite {
    private Counter scoreCounter;

    public ScoreIndicator(Counter scoreCounter) {
        this.scoreCounter = scoreCounter;
    }
    @Override
    public void drawOn(DrawSurface d) {
        String scoreCount = String.valueOf(scoreCounter.getValue());
        d.setColor(Color.WHITE);
        d.drawRectangle(0,0,800,20);
        d.fillRectangle(0,0,800,20);
        d.setColor(Color.black);
        d.drawText(400, 15, scoreCount ,20);
    }

    @Override
    public void timePassed() {

    }

    @Override
    public void addToGame(Game game) {
        game.addSprite(this);
    }
}
