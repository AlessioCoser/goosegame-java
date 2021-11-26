package goosegame;

import java.util.*;
import java.util.stream.Collectors;

class MoveCommand {
    private final String command;
    private final String[] arguments;

    public MoveCommand(String command) {
        this.command = command;
        this.arguments = this.command.split(" ");
    }

    public boolean canHandle() {
        return command.startsWith("move");
    }

    public String player() {
        return arguments[1];
    }

    public Dice getDice() {
        return new Dice(Integer.parseInt(firstDie()), Integer.parseInt(secondDie()));
    }

    private String firstDie() {
        return arguments[2].replace(",", "");
    }

    private String secondDie() {
        return arguments[3];
    }
}

public class Game {
    private final List<PlayerStatus> playersStatus = new ArrayList<>();
    private static final int LAST_CELL = 63;

    public String run(String command) {
        MoveCommand moveCommand = new MoveCommand(command);
        if (moveCommand.canHandle()) {
            return move(moveCommand);
        }

        String playerToAdd = playerNameFrom(command);
        return addPlayer(playerToAdd);
    }


    private String move(MoveCommand moveCommand) {
        String player = moveCommand.player();
        Dice dice = moveCommand.getDice();
        PlayerStatus status = playerStatusFrom(player);

        int positionAfterRoll = status.position() + dice.getFirst() + dice.getSecond();

        if (isBounces(positionAfterRoll)) {
            bounces(status, positionAfterRoll);
            return bouncesMessage(player, dice, status);
        }
        status.updatePosition(positionAfterRoll);

        if (isWin(positionAfterRoll)) {
            return getWinMessage(player, dice, status);
        }
        return getMoveMessage(player, dice, status);
    }

    private PlayerStatus playerStatusFrom(String player) {
        return playersStatus.stream()
                .filter((s) -> s.getPlayer().equals(player))
                .findFirst()
                .orElseThrow();
    }

    private String getMoveMessage(String player, Dice dice, PlayerStatus status) {
        return getPlayerRollAndCurrentPositionMessage(player, dice, status.previousPosition()) + status.position();
    }

    private String getWinMessage(String player, Dice dice, PlayerStatus status) {
        return getPlayerRollAndCurrentPositionMessage(player, dice, status.previousPosition()) + status.position() + ". " + player + " Wins!!";
    }

    private String bouncesMessage(String player, Dice dice, PlayerStatus status) {
        return getPlayerRollAndCurrentPositionMessage(player, dice, status.previousPosition()) + LAST_CELL + ". Pippo bounces! Pippo returns to " + status.position();
    }

    private String getPlayerRollAndCurrentPositionMessage(String player, Dice dice, int currentPosition) {
        return player + " rolls " + dice.getFirst() + ", " + dice.getSecond() + ". " + player + " moves from " + printCurrentPosition(currentPosition) + " to ";
    }

    private boolean isWin(int positionAfterRoll) {
        return positionAfterRoll == LAST_CELL;
    }

    private boolean isBounces(int positionAfterRoll) {
        return positionAfterRoll > LAST_CELL;
    }

    private int bounces(PlayerStatus status, int position) {
        int newPosition = (LAST_CELL - (position - LAST_CELL));
        status.updatePosition(newPosition);
        return newPosition;
    }

    private String printCurrentPosition(int currentPosition) {
        return currentPosition == 0 ? "Start" : String.valueOf(currentPosition);
    }

    private String playerNameFrom(String command) {
        return command.split("add player ")[1];
    }

    private String addPlayer(String playerToAdd) {
        if (playersStatus.stream().anyMatch((ps) -> ps.getPlayer().equals(playerToAdd))) {
            return playerToAdd + ": already existing player";
        }
        playersStatus.add(new PlayerStatus(playerToAdd));
        List<String> playerNames = playersStatus.stream().map(PlayerStatus::getPlayer).collect(Collectors.toList());
        return "players: " + String.join(", ", playerNames);
    }
}
