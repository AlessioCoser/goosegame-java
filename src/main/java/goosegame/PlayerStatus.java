package goosegame;

public class PlayerStatus {

    private int position;
    private final String player;

    public PlayerStatus(String player) {
        this.player = player;
        this.position = 0;
    }

    public String getPlayer() {
        return this.player;
    }

    public int position() {
        return position;
    }

    public int updatePosition(int newPosition) {
        position = newPosition;
        return position();
    }
}
