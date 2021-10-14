package goosegame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {
    private Game game;

    @BeforeEach
    public void newGame(){
        game = new Game();
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
}

