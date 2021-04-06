package it.polimi.ingsw.model;

import it.polimi.ingsw.model.factory.CardFactory;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.ProductionCard;
import it.polimi.ingsw.model.factory.MapFactory;
import it.polimi.ingsw.model.market.Market;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Game {
    List<Player> players;
    LeaderCard[] leaders;
    Stack<ProductionCard>[][] productionDecks;
    Market market;
    List<CellScore> scorePositions ;
    List<PapalSpace> papalSpaces;
    int currentPapalSpaceToReach;
    int currentPlayer;
    int nofplayers=0;

    public Game()
    {
        this.market = new Market();
        this.productionDecks = CardFactory.loadProductionCardsFromJsonFile();
        this.leaders         = CardFactory.loadLeaderCardsFromJsonFile();
        this.papalSpaces     = MapFactory.loadPapalSpacesFromJsonFile();
        this.scorePositions  = MapFactory.loadCellScoresFromJsonFile();
    }

    public void addPlayer(String nickname, LeaderCard[] leaders) throws Exception
    {
        if(nofplayers<4) {
            for (Player p: players) {
                if(p.getNickname().equals(nickname))
                    throw new Exception("Nickname already taken, please choose another nickname");
            }
            players.add(new Player(nickname));
            nofplayers++;
        }
        else
            throw new Exception("There are already 4 players");
    }

    /**
     * this method starts the game by shuffling the players and setting the currentPlayer (the one with the Inkwell)
     * @throws Exception if the are no players to start the game
     */
    public void startGame() throws Exception
    {
        if(nofplayers==0)
            throw new Exception("There are no players");
        Collections.shuffle(players);
        players.get(0).setInkwell();
        currentPlayer = 0;
    }

    /**
     * this function changes the turn and so the current player who is supposed to play
     * @return
     */
    public Player nextTurn()
    {
        if(currentPlayer == nofplayers -1)
            currentPlayer = 0;
        else
            currentPlayer++;
        return players.get(currentPlayer);
    }

}
