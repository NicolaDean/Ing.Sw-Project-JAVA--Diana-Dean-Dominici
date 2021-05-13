package it.polimi.ingsw.modelTest;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.resources.ResourceList;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.cards.PrerequisiteCard;
import it.polimi.ingsw.model.cards.leaders.DepositBonus;
import it.polimi.ingsw.model.dashboard.Storage;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceOperator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.enumeration.ResourceType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ScoreTest {

    /**
     * test if the score is correctly increased when a player buys a prod. card
     */
    @RepeatedTest(15)
    public void productionScoreTest() throws Exception {
        //created a game with 4 players and added the current player to "currPlayer"
        Game game = new Game();
        try {
            game.addPlayer("Fede");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            game.addPlayer("Richi");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            game.addPlayer("Nico");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            game.addPlayer("Luigi");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Player currPlayer = null;
        try {
            game.startGame();
            currPlayer = game.getPlayer(0);
        } catch (Exception e) {
            e.printStackTrace();

        }

        Resource coins = new Resource(COIN, 999);
        Resource shields = new Resource(SHIELD, 999);
        Resource servants = new Resource(SERVANT, 999);
        Resource rocks = new Resource(ROCK, 999);
        assert currPlayer != null;
        currPlayer.getDashboard().chestInsertion(coins);
        currPlayer.getDashboard().chestInsertion(shields);
        currPlayer.getDashboard().chestInsertion(servants);
        currPlayer.getDashboard().chestInsertion(rocks);

        for(int i = 0; i<4; i++)
        {
            currPlayer.getDashboard().chestInsertion(coins);
            currPlayer.getDashboard().chestInsertion(shields);
            currPlayer.getDashboard().chestInsertion(servants);
            currPlayer.getDashboard().chestInsertion(rocks);

            game.getProductionDecks()[0][i].peek().buy(currPlayer, 0);
            game.getProductionDecks()[1][i].peek().buy(currPlayer, 0);
            game.getProductionDecks()[2][i].peek().buy(currPlayer, 0);

            int a = game.getProductionDecks()[0][i].pop().getScore() +
                    game.getProductionDecks()[1][i].pop().getScore() +
                    game.getProductionDecks()[2][i].pop().getScore();

            assertEquals(currPlayer.getScore(), a);
            currPlayer = game.nextTurn();
        }

    }

    /**
     * test the correnc score increas of players when a leader is activated
     */
    @RepeatedTest(15)
    public void leaderScoreTest() throws Exception {
        Game game = new Game();
        Resource coins = new Resource(COIN, 999);
        Resource shields = new Resource(SHIELD, 999);
        Resource servants = new Resource(SERVANT, 999);
        Resource rocks = new Resource(ROCK, 999);
        try {
            game.addPlayer("Fede");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            game.addPlayer("Richi");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            game.addPlayer("Nico");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            game.addPlayer("Luigi");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Player currPlayer = null;
        try {
            game.startGame();
            currPlayer = game.getPlayer(0);
        } catch (Exception e) {
            e.printStackTrace();

        }
        currPlayer.getDashboard().chestInsertion(coins);
        currPlayer.getDashboard().chestInsertion(shields);
        currPlayer.getDashboard().chestInsertion(servants);
        currPlayer.getDashboard().chestInsertion(rocks);
        LeaderCard[] leader = game.get4leaders();
        int costs = 0;
        for (int i= 0; i<4; i++) {
            leader[i].setCardPrequisite(null);
            leader[i].activate(currPlayer);
            costs= costs + leader[i].getScore();
        }
        assertEquals(currPlayer.getScore(), costs);
    }

    /**
     * test the correct increase of the player' s score when a scoreCell is reached
     */
    @Test
    public void scoreCellTest()
    {
        Game game = new Game();
        try {
            game.addPlayer("Fede");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            game.addPlayer("Richi");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            game.addPlayer("Nico");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            game.addPlayer("Luigi");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Player currPlayer = null;
        try {
            game.startGame();
            currPlayer = game.getPlayer(0);
        } catch (Exception e) {
            e.printStackTrace();

        }

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
        Game game = new Game();
        try {
            game.addPlayer("Fede");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            game.addPlayer("Richi");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            game.addPlayer("Nico");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            game.addPlayer("Luigi");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Player currPlayer = null;
        try {
            game.startGame();
            currPlayer = game.getPlayer(0);
        } catch (Exception e) {
            e.printStackTrace();

        }

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

}
