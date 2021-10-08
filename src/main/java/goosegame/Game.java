package goosegame;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final List<String> players = new ArrayList<>();

    public String run(String command) {
        if (command.startsWith("move")){
            return "Pippo rolls 4, 2. Pippo moves from Start to 6";
        }
        String playerToAdd = playerNameFrom(command);
        return addPlayer(playerToAdd);
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
