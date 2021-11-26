package goosegame;

public class MoveCommand {
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
