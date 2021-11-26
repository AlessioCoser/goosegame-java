package goosegame;

public class PlayerStatus {

    private final String player;
    private int position;
    private int previousPosition;

    public PlayerStatus(String player) {
        this.player = player;
        this.position = 0;
        this.previousPosition = -1;
    }

    public String getPlayer() {
        return this.player;
    }

    public int position() {
        return position;
    }

    public int updatePosition(int newPosition) {
        previousPosition = position;
        position = newPosition;
        return position();
    }

    public int previousPosition() {
        return previousPosition;
    }
}
