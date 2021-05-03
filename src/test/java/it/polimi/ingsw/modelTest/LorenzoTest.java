package it.polimi.ingsw.modelTest;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.lorenzo.LorenzoGame;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LorenzoTest {

    /**
     * test the correct increase of the player' s score when a scoreCell is reached
     */
    @Test
    public void scoreCellTest()
    {
        LorenzoGame game = new LorenzoGame();

        try { game.addPlayer("Richi"); } catch (Exception e) { e.printStackTrace(); }

        Player currPlayer = null;
        try { currPlayer = game.startGame(); } catch (Exception e) { e.printStackTrace(); }

        currPlayer.incrementPosition(3);
        game.nextTurn();
        assertEquals(currPlayer.getScore(), 1);

        currPlayer.incrementPosition(12);
        game.nextTurn();
        assertEquals(currPlayer.getScore(), 11);

        currPlayer.incrementPosition(9);
        game.nextTurn();
        assertEquals(currPlayer.getScore(), 29);

    }

}
