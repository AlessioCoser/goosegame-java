package goosegame;

public class MoveCommand {
    private final String command;
    private DiceThrower diceThrower;
    private final String[] arguments;

    public MoveCommand(String command, DiceThrower diceThrower) {
        this.command = command;
        this.diceThrower = diceThrower;
        this.arguments = this.command.split(" ");
    }

    public boolean canHandle() {
        return command.startsWith("move");
    }

    public String player() {
        return arguments[1];
    }

    public Dice getDice() {
        if(arguments.length < 3) {
            return diceThrower.roll();
        }
        return new Dice(Integer.parseInt(firstDie()), Integer.parseInt(secondDie()));
    }

    private String firstDie() {
        return arguments[2].replace(",", "");
    }

    private String secondDie() {
        return arguments[3];
    }
}
