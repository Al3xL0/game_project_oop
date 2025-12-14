package game.events;

import shapes.Ball;
import shapes.Block;

public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;

    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }
    public void levelIsFinished() {
        this.currentScore.increase(100);
    }
    public void hitEvent(Block beingHit, Ball hitter) {
        this.currentScore.increase(5);
    }
}