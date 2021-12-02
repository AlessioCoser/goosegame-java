package goosegame;

public class RandomDiceThrower implements DiceThrower {
    @Override
    public Dice roll() {
        return new Dice(throwDie(), throwDie());
    }

    private int throwDie() {
        return (int)(Math.random() * 6) + 1;
    }
}
