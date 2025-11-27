import biuoop.DrawSurface;

import java.util.ArrayList;

public class SpriteCollection {
    private ArrayList<Sprite> sprites;

    public SpriteCollection() {
        sprites = new ArrayList<>();
    }
    public void addSprite(Sprite s) {
        sprites.add(s);
    }

    // call timePassed() on all sprites.
    public void notifyAllTimePassed() {
        for(Sprite sprite : sprites) {
            sprite.timePassed();
        }

    }

    // call drawOn(d) on all sprites.
    public void drawAllOn(DrawSurface d) {
        for(Sprite sprite: sprites) {
            sprite.drawOn(d);
        }
    }
}