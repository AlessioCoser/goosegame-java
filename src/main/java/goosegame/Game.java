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

    public String firstDie() {
        return arguments[2].replace(",", "");
    }

    public String secondDie() {
        return arguments[3];
    }
}

public class Game {
    private final List<String> players = new ArrayList<>();
    private final Map<String, Integer> playersAndPositions = new HashMap<>();
    private static final int LAST_POSITION = 63;

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
        String firstDie = moveCommand.firstDie();
        String secondDie = moveCommand.secondDie();
        int currentPosition = playersAndPositions.get(player);
        int newPosition = currentPosition + parseInt(firstDie) + parseInt(secondDie);
        boolean isBounces = false;
        if (newPosition > LAST_POSITION){
            newPosition = (LAST_POSITION - (newPosition - LAST_POSITION));
            isBounces = true;
        }
        playersAndPositions.put(player, newPosition);
        String message = player + " rolls " + firstDie + ", " + secondDie + ". " + player + " moves from " + printCurrentPosition(currentPosition) + " to " + newPosition;
        if (newPosition == LAST_POSITION){
            return  message + ". " + player + " Wins!!";
        }
        if (isBounces){
            return  player + " rolls " + firstDie + ", " + secondDie + ". " + player + " moves from " + printCurrentPosition(currentPosition) + " to " + LAST_POSITION +". Pippo bounces! Pippo returns to "+ newPosition;
        }
        return message;
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
