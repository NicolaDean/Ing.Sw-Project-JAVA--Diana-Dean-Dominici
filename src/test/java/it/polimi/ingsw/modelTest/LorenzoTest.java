package it.polimi.ingsw.modelTest;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.lorenzo.Lorenzo;
import it.polimi.ingsw.model.lorenzo.LorenzoGame;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LorenzoTest {

    /**
     * test the correct increase of the player' s score when a scoreCell is reached
     */
    @Test
    public void scoreCellTest() {
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

    /**
     * test the correct incresing of players score when a papalspace is reached
     */
    @Test
    public void scorePapalSpaceTest()
    {
        LorenzoGame game = new LorenzoGame();

        Lorenzo l = game.getLorenzo();

        try { game.addPlayer("Richi"); } catch (Exception e) { e.printStackTrace(); }

        Player currPlayer = null;
        try { currPlayer = game.startGame(); } catch (Exception e) { e.printStackTrace(); }

        currPlayer.incrementPosition(3);
        currPlayer= game.nextTurn();
        currPlayer.incrementPosition(5);
        currPlayer= game.nextTurn();
        currPlayer.incrementPosition(6);
        currPlayer= game.nextTurn();
        currPlayer.incrementPosition(8);
        currPlayer=game.nextTurn();
        currPlayer.incrementPosition(13);
        currPlayer=game.nextTurn();
        currPlayer.incrementPosition(4);
        currPlayer=game.nextTurn();
        currPlayer.incrementPosition(6);
        currPlayer=game.nextTurn();
        currPlayer.incrementPosition(5);
        game.nextTurn();
        assertEquals(game.getPlayers().get(0).getScore(), 12);
        assertEquals(game.getPlayers().get(1).getScore(), 6);
        assertEquals(game.getPlayers().get(2).getScore(), 8);
        assertEquals(game.getPlayers().get(3).getScore(), 8);

    }

    @Test
    public void checkLorenzoGame(){
        LorenzoGame l=new LorenzoGame();
    }

}
