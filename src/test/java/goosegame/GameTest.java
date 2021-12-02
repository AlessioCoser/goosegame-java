package goosegame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {
    private Game game;

    @BeforeEach
    public void newGame() {
        game = new Game(new FixedDiceThrower());
    }

    @Test
    public void addFirstPlayer() {
        String result = game.run("add player Pippo");

        assertEquals("players: Pippo", result);
    }

    @Test
    void addTwoPlayer() {
        game.run("add player Pippo");

        String result = game.run("add player Pluto");

        assertEquals("players: Pippo, Pluto", result);
    }

    @Test
    void addDuplicatedPlayer() {
        game.run("add player Pippo");

        String result = game.run("add player Pippo");

        assertEquals("Pippo: already existing player", result);
    }

    @Test
    void movePlayerFromStart() {
        game.run("add player Pippo");

        String result = game.run("move Pippo 4, 2");

        assertEquals("Pippo rolls 4, 2. Pippo moves from Start to 6", result);
    }

    @Test
    void movePlayerFromStartBy12() {
        game.run("add player Pluto");

        String result = game.run("move Pluto 6, 6");

        assertEquals("Pluto rolls 6, 6. Pluto moves from Start to 12", result);
    }

    @Test
    void movePlayerFromPreviousPosition() {
        game.run("add player Pluto");
        game.run("move Pluto 4, 2");

        String result = game.run("move Pluto 2, 3");

        assertEquals("Pluto rolls 2, 3. Pluto moves from 6 to 11", result);
    }

    @Test
    void moveTwoPlayersFromPreviousPosition() {
        game.run("add player Pluto");
        game.run("add player Pippo");
        game.run("move Pluto 4, 2");
        game.run("move Pippo 5, 1");

        String plutoResult = game.run("move Pluto 2, 3");
        String pippoResult = game.run("move Pippo 2, 4");

        assertEquals("Pluto rolls 2, 3. Pluto moves from 6 to 11", plutoResult);
        assertEquals("Pippo rolls 2, 4. Pippo moves from 6 to 12", pippoResult);
    }

    @Test
    void movePlayerToVictoryPosition() {
        game.run("add player Pippo");
        game.run("move Pippo 60, 0");

        String result = game.run("move Pippo 1, 2");

        assertEquals("Pippo rolls 1, 2. Pippo moves from 60 to 63. Pippo Wins!!", result);
    }

    @Test
    void movePlayerBouncesBackWhenPassingVictoryPosition() {
        game.run("add player Pippo");
        game.run("move Pippo 60, 0");

        String result = game.run("move Pippo 3, 2");

        assertEquals("Pippo rolls 3, 2. Pippo moves from 60 to 63. Pippo bounces! Pippo returns to 61", result);
    }

    @Test
    void movePlayerBouncesPrintsThePlayerName() {
        game.run("add player Pluto");
        game.run("move Pluto 60, 0");

        String result = game.run("move Pluto 3, 2");

        assertEquals("Pluto rolls 3, 2. Pluto moves from 60 to 63. Pluto bounces! Pluto returns to 61", result);
    }

    @Test
    void movePlayerTheGameThrowsTheDice() {
        Game game = new Game(new FixedDiceThrower(1, 2));
        game.run("add player Pippo");
        game.run("move Pippo 2, 2");

        String result = game.run("move Pippo");

        assertEquals("Pippo rolls 1, 2. Pippo moves from 4 to 7", result);
    }
}
