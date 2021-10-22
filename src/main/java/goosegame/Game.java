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
        int newPosition = playersAndPositions.get(player) + parseInt(firstDie) + parseInt(secondDie);
        String oldPosition = playersAndPositions.get(player) == 0 ? "Start" : playersAndPositions.get(player).toString();
        String result = player + " rolls " + firstDie + ", " + secondDie + ". " + player + " moves from " + oldPosition + " to " + newPosition;
        playersAndPositions.put(player, newPosition);
        return result;
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
