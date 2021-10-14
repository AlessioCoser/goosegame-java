package goosegame;

import java.util.ArrayList;
import java.util.List;

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
        int sum = parseInt(firstDie) + parseInt(secondDie);
        return player + " rolls " + firstDie + ", " + secondDie + ". " + player + " moves from Start to " + sum;
    }

    private String playerNameFrom(String command) {
        return command.split("add player ")[1];
    }

    private String addPlayer(String playerToAdd) {
        if(players.contains(playerToAdd)) {
            return playerToAdd + ": already existing player";
        }
        players.add(playerToAdd);

        return "players: " +  String.join(", ", players);
    }
}
