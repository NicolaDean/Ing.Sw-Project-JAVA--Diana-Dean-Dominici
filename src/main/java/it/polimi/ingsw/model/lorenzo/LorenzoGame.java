package it.polimi.ingsw.model.lorenzo;

import it.polimi.ingsw.model.CellScore;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.PapalSpace;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.ProductionCard;
import it.polimi.ingsw.model.factory.CardFactory;
import it.polimi.ingsw.model.factory.MapFactory;
import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.resources.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class LorenzoGame extends Game {
    List<Player> players;
    LeaderCard[] leaders;
    Stack<ProductionCard>[][] productionDecks;
    Market market;
    List<CellScore> scorePositions ;
    List<PapalSpace> papalSpaces;
    int currentPapalSpaceToReach;
    int currentPlayer=0;
    int nofplayers=0;
    private int leaderCount=0;
    Lorenzo lorenzo=new Lorenzo();

    public LorenzoGame()
    {
        this.market = new Market();
        this.productionDecks = CardFactory.loadProductionCardsFromJsonFile();
        this.leaders         = CardFactory.loadLeaderCardsFromJsonFile();
        this.papalSpaces     = MapFactory.loadPapalSpacesFromJsonFile();
        this.scorePositions  = MapFactory.loadCellScoresFromJsonFile();
        this.currentPapalSpaceToReach = 0;
    }

    public int getCurrentPlayerIndex()
    {
        return 0;
    }

    public boolean isFull() {
        return this.nofplayers==1;
    }

    /**
     * function to add a new player to the game
     * @param nickname the nickname of the player
     * @throws Exception
     */
    public void addPlayer(String nickname) throws Exception
    {
        if(nofplayers==1) {
            for (Player p: players) {
                if(p.getNickname().equals(nickname))
                    throw new Exception("Nickname already taken, please choose another nickname");
            }
            players.add(new Player(nickname, scorePositions.size()));
            nofplayers++;
        }
        else
            throw new Exception("There are already 1 players");
    }

    /**
     * Discard resource of a player and increment other position
     * @param p player that want to discard
     * @param res resource to discard
     * @param pos deposit pos
     * @throws Exception wrong deposit
     */
    public void discardResource(Player p, Resource res, int pos) throws Exception {
        //TODO
    }

    /**
     * this function changes the turn and so the current player who is supposed to play
     * @return the new player that is supposed to play
     */
    public Player nextTurn() {
        currentPlayer=0;

        //PAPAL SPACE

        if(this.currentPapalSpaceToReach < this.papalSpaces.size())
        {
            //Check if someone surpass a papal space and in case add the score of papalToken to the players
            boolean out = this.papalSpaces.get(this.currentPapalSpaceToReach).checkPapalSpaceActivation(this.players,lorenzo);

            while(out == true && this.currentPapalSpaceToReach+1 < this.papalSpaces.size()){
                this.currentPapalSpaceToReach++;
                out = this.papalSpaces.get(this.currentPapalSpaceToReach).checkPapalSpaceActivation(this.players);
            }

        }

        int position = lorenzo.getPosition();
        int i=-1;

        for (CellScore cell:scorePositions) {
            if (position >= cell.getPosition()) {
                i++;
            }
        }

        // TODO FIX ⬇⬇⬇
        if(i!=-1 && !players.get(currentPlayer).getSurpassedcells()[i])
        {
            players.get(currentPlayer).getSurpassedcells()[i]=true;
            players.get(currentPlayer).increaseScore(scorePositions.get(i).getScore());
            if(i>0)
                players.get(currentPlayer).decreaseScore(players.get(currentPlayer).getLastadded());
            players.get(currentPlayer).setLastadded(scorePositions.get(i).getScore());
        }


        return players.get(currentPlayer);
    }

    public void lorenzoTurn() {
        //TODO
        currentPlayer=-1;
    }

    /**
     * shuffle all token together
     */
    public void resetStockTocken(){
        //TODO
    }

}
