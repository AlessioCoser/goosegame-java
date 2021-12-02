package goosegame;

public class TestableDiceThrower implements DiceThrower {
    private final int firstDice;
    private final int secondDice;

    public TestableDiceThrower() {
        this(1, 2);
    }

    public TestableDiceThrower(int firstDice, int secondDice) {
        this.firstDice = firstDice;
        this.secondDice = secondDice;
    }

    @Override
    public Dice roll() {
        return new Dice(firstDice, secondDice);
    }
}
