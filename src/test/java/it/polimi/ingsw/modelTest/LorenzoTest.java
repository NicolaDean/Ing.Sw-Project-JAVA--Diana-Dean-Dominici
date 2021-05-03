package it.polimi.ingsw.modelTest;

import it.polimi.ingsw.enumeration.CardType;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.lorenzo.Lorenzo;
import it.polimi.ingsw.model.lorenzo.LorenzoGame;
import it.polimi.ingsw.model.lorenzo.token.ActionToken;
import it.polimi.ingsw.model.lorenzo.token.ColoredActionToken;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.enumeration.CardType.*;
import static org.junit.jupiter.api.Assertions.*;

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
     * test the correct working of Lorenzo turn
     */
    @Test
    public void lorenzoTurn()
    {
        LorenzoGame game = new LorenzoGame();
        ActionToken at;
        Lorenzo l = game.getLorenzo();

        int nofcard=0,tmpPos=0,tmpB=0,tmpY=0,tmpV=0,tmpG=0;

        try { game.addPlayer("Richi"); } catch (Exception e) { fail(); }

        Player currPlayer = null;
        try { currPlayer = game.startGame(); } catch (Exception e) { fail(); }

        for(int i=0;i<7;i++) {
            currPlayer = game.nextTurn();
            at = game.getTokenDrawn();
            if(at.isSpecial()) {
                tmpPos++;
                break;
            }
            if(at.getType()==null)
                tmpPos++;
            if(at.getType() == BLUE)
                tmpB++;
            if(at.getType() == YELLOW)
                tmpY++;
            if(at.getType() == PURPLE)
                tmpV++;
            if(at.getType() == GREEN)
                tmpG++;
        }
        //check yellow token
        for(int i=0;i<3;i++)
            nofcard+=game.getProductionDecks()[i][0].size();
        assertEquals( 12 - (2*tmpB) , nofcard );

        //TODO altri colori
    }

    @Test
    public void checkLorenzoGame(){
        LorenzoGame l=new LorenzoGame();
    }

    //TODO test per le celle papali

}
