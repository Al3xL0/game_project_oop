package game;

import biuoop.DrawSurface;

import java.util.ArrayList;
import java.util.List;

public class SpriteCollection {
    private ArrayList<Sprite> sprites;

    public SpriteCollection() {
        sprites = new ArrayList<>();
    }
    public void addSprite(Sprite s) {
        sprites.add(s);
    }
    public void removeSprite(Sprite s) {
        sprites.remove(s);
    }
    // call timePassed() on all sprites.
    public void notifyAllTimePassed() {
        List<Sprite> copy = new ArrayList<>(sprites);
        for(Sprite sprite : copy) {
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