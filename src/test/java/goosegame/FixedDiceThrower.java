package goosegame;

public class FixedDiceThrower implements DiceThrower {
    private final int firstDice;
    private final int secondDice;

    public FixedDiceThrower() {
        this(1, 2);
    }

    public FixedDiceThrower(int firstDice, int secondDice) {
        this.firstDice = firstDice;
        this.secondDice = secondDice;
    }

    @Override
    public Dice roll() {
        return new Dice(firstDice, secondDice);
    }
}
