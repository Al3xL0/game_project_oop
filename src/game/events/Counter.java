package game.events;

public class Counter {
    int count;

    public Counter( ){
        count = 0;
    }
    // add number to current count.
    public void increase(int number) {
        count += number;
    }
    // subtract number from current count.
    public void decrease(int number) {
        count -= number;
    }
    // get current count.
    public int getValue() {
        return count;
    }
}