package game.events;

import shapes.Ball;
import shapes.Block;
import game.Game;
public class BallRemover implements  HitListener{
    Counter ballCounter;
    Game game;
    public BallRemover(Game game, Counter counter) {
        this.game = game;
        this.ballCounter = counter;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        ballCounter.decrease(1);
        hitter.removeFromGame(this.game);
    }
}
