package goosegame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;

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
        return new Dice(Integer.parseInt(firstDie()),Integer.parseInt(secondDie()));
    }

    private String firstDie() {
        return arguments[2].replace(",", "");
    }

    private String secondDie() {
        return arguments[3];
    }
}

public class Game {
    private final List<String> players = new ArrayList<>();
    private final Map<String, Integer> playersAndPositions = new HashMap<>();
    private static final int LAST_CELL = 63;

    public String run(String command) {
        MoveCommand moveCommand = new MoveCommand(command);
        if (moveCommand.canHandle()){
            return move(moveCommand);
        }

        String playerToAdd = playerNameFrom(command);
        return addPlayer(playerToAdd);
    }


    private String move(MoveCommand moveCommand) {
        String player = moveCommand.player();
        // CODE SMELL Feature envy
        Dice dice = moveCommand.getDice();

        int currentPosition = playersAndPositions.get(player);
        int positionAfterRoll = currentPosition + dice.getFirst() + dice.getSecond() ;
        String rollMessage = player + " rolls " + dice.getFirst() + ", " + dice.getSecond() + ". " + player + " moves from " + printCurrentPosition(currentPosition) + " to ";
        if (isBounces(positionAfterRoll)){
            int newPosition = bounces(player, positionAfterRoll);
            return  rollMessage + LAST_CELL + ". Pippo bounces! Pippo returns to "+ newPosition;
        }
        playersAndPositions.put(player, positionAfterRoll);


        if (isWin(positionAfterRoll)){
            return  rollMessage + positionAfterRoll + ". " + player + " Wins!!";
        }
        return rollMessage + positionAfterRoll;
    }

    private boolean isWin(int positionAfterRoll) {
        return positionAfterRoll == LAST_CELL;
    }

    private boolean isBounces(int positionAfterRoll) {
        return positionAfterRoll > LAST_CELL;
    }

    private int bounces(String player, int position) {
        int newPosition = (LAST_CELL - (position - LAST_CELL));
        playersAndPositions.put(player, newPosition);
        return newPosition;
    }

    private String printCurrentPosition(int currentPosition) {
        return currentPosition == 0 ? "Start" : String.valueOf(currentPosition);
    }

    private String playerNameFrom(String command) {
        return command.split("add player ")[1];
    }

    private String addPlayer(String playerToAdd) {
        if(players.contains(playerToAdd)) {
            return playerToAdd + ": already existing player";
        }
        players.add(playerToAdd);
        playersAndPositions.put(playerToAdd, 0);
        return "players: " +  String.join(", ", playersAndPositions.keySet());
    }
}
