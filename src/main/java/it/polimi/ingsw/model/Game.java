package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.factory.CardFactory;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.ProductionCard;
import it.polimi.ingsw.model.factory.MapFactory;
import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.minimodel.MiniModel;
import it.polimi.ingsw.model.minimodel.MiniPlayer;
import it.polimi.ingsw.utils.ConstantValues;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Game {
    protected List<Player> players;
    protected LeaderCard[] leaders;
    protected Stack<ProductionCard>[][] productionDecks;
    protected Market market;
    protected List<CellScore> scorePositions ;
    protected List<PapalSpace> papalSpaces;
    protected int currentPapalSpaceToReach;
    protected int currentPlayer;
    protected int nofplayers=0;
    protected int leaderCount=0;
    protected boolean gamestarted=false;
    protected boolean isEnded = false;
    public static int GameMaxCell = 24;

    public Game()
    {
        this.market = new Market();
        this.productionDecks = CardFactory.loadProductionCardsFromJsonFile();
        this.leaders         = CardFactory.loadLeaderCardsFromJsonFile();
        this.papalSpaces     = MapFactory.loadPapalSpacesFromJsonFile();
        this.scorePositions  = MapFactory.loadCellScoresFromJsonFile();
        this.currentPlayer   =0;
        this.players = new ArrayList<>();
        this.currentPapalSpaceToReach = 0;
    }



    public ProductionCard drawProductionCard(int x, int y)
    {
        return this.getProductionDecks()[x][y].peek();
    }

    public int getCurrentPlayerIndex()
    {
        return this.currentPlayer;
    }

    public Player getCurrentPlayer()
    {
        return this.players.get(this.currentPlayer);
    }

    public Market getMarket()
    {
        return this.market;
    }

    public boolean checkNickname(String nickname)
    {
        for(Player p : this.players)
        {
            if(nickname.equals(p.getNickname())) return true;
        }
        return false;
    }

    public boolean isFull(String nickname) {
        return (this.nofplayers==4 || gamestarted || checkNickname(nickname));

    }




    public void removePlayer(int index)
    {
        this.players.remove(index);
    }

    /**
     * function to add a new player to the game
     * @param nickname the nickname of the player
     * @throws Exception
     */
    public void addPlayer(String nickname) throws Exception
    {
        if(nofplayers<4) {
            for (Player p: players) {
                if(p.getNickname().equals(nickname))
                    throw new NicknameAlreadyTaken(nickname);
            }
            players.add(new Player(nickname, scorePositions.size()));
            nofplayers++;
        }
        else
            throw new MatchFull("There are already 4 players");
    }

    /**
     * Return the player index position from the player
     * @param nickname
     * @return
     */
    public int getPlayerIndexFromNickname(String nickname)
    {
        int i=0;
        for(Player p:players)
        {
            if(p.getNickname().equals(nickname)) return i;
            i++;
        }
        return -1;
    }

    /**
     * method to set the leader cards of the player
     * @param p
     * @param l
     */
    public void setLeaders(Player p, LeaderCard[] l)
    {
        p.setLeaders(l);
    }

    /**
     * this method starts the game by shuffling the players and setting the currentPlayer (the one with the Inkwell)
     * @throws Exception if the are no players to start the game
     */

    public Player getPlayer(int index)
    {
        return players.get(index);
    }

    public int getRealPlayerHandlerIndex()
    {
        return this.getCurrentPlayer().getControllerIndex();
    }

    public int[] startGame() throws NotEnoughPlayers
    {

        if(nofplayers==0)
            throw new NotEnoughPlayers("");

        String [] initialOrder = new String[4];
        int    [] outIndexes   = new int[4];
        int i=0;
        for(Player p : players)
        {
            initialOrder[i] = p.getNickname();
            i++;
        }

        Collections.shuffle(players);

        int currIndex = 0;
        for(Player p : players)
        {
            i=0;
            for(String str : initialOrder)
            {
                if(p.getNickname().equals(str))
                {
                    outIndexes[currIndex] = i;
                    p.setControllerIndex(i);
                }
                i++;
            }
            currIndex++;
        }

        players.get(0).setInkwell();
        currentPlayer = 0;
        gamestarted=true;
        //return players.get(currentPlayer);
        return outIndexes;
    }


    /**
     * Discard resource of a player and increment other position
     * @param qty resources discarter
     */
    public void discardResource(int qty) {

        for(int i=0;i<this.players.size();i++)
        {
            if(i!=this.currentPlayer) this.players.get(i).incrementPosition(qty);
        }
    }


    /**
     * //Check if someone surpass a papal space and in case add the score of papalToken to the players
     */
    public void papalSpaceCheck()
    {
        //PAPAL SPACE
        if(this.currentPapalSpaceToReach < this.papalSpaces.size())
        {

            boolean out = this.papalSpaces.get(this.currentPapalSpaceToReach).checkPapalSpaceActivation(this.players);
            while(out == true && this.currentPapalSpaceToReach+1 < this.papalSpaces.size()){
                this.currentPapalSpaceToReach++;
                out = this.papalSpaces.get(this.currentPapalSpaceToReach).checkPapalSpaceActivation(this.players);
            }

        }
    }

    /**
     * check for each player if they surpassed a new scoreposition, in that case the player score is increased accordingly
     */
    public void checkFaithTrackScoreGain()
    {
        for (Player p:players) {

            int position = p.getPosition();

            int i = -1;

             /*
            int gainedPoints = 0;
            int scorePrecedente = 0;
            for(int j=p.getLastadded()+1;j<scorePositions.size();j++)
            {
                int cellPos = scorePositions.get(i).getPosition();


                if(p.getPosition() > cellPos)
                    gainedPoints += scorePositions.get(i).getScore();
                    gainedPoints -= scorePrecedente;
                scorePrecedente = scorePositions.get(i).getScore();
            }
            gainedPoints -= scorePositions.get(p.getLastadded()).getScore();
            */

            for (CellScore cell:scorePositions) {
                if (position >= cell.getPosition()) {
                    i++;
                }
            }

            //where the increase happens

            if(i!=-1 && !p.getSurpassedcells()[i])
            {
                p.getSurpassedcells()[i]=true;
                p.increaseScore(scorePositions.get(i).getScore());
                if(i>0)
                    p.decreaseScore(p.getLastadded());
                p.setLastadded(scorePositions.get(i).getScore());
            }
        }
    }
    /**
     * this function changes the turn and so the current player who is supposed to play
     * @return the new player that is supposed to play
     */
    public Player nextTurn(){
        if(currentPlayer == nofplayers -1)
            currentPlayer = 0;
        else
            currentPlayer++;

        //Papal space activation check
        papalSpaceCheck();

        //Real time faith points calculation
        checkFaithTrackScoreGain();

        return players.get(currentPlayer);
    }
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * gets 4 leaders from the leader deck
     * @return an array of 4 leaders
     */
    public LeaderCard[] get4leaders()
    {
        LeaderCard[] lead = new LeaderCard[ConstantValues.leaderCardsToDraw];
        for(int i = 0; i<ConstantValues.leaderCardsToDraw; i++)
        {
            lead[i] = leaders[leaderCount];
            leaders[leaderCount]=null;
            leaderCount++;
        }
        return lead;
    }

    /**
     *
     * @return true if the current player bought the 7th card
     */
    public boolean checkCardCondition()
    {
        Player p = this.players.get(currentPlayer);
        return p.getDashboard().countCard() >= ConstantValues.cardsWinningCondition;
    }

    /**
     *
     * @return true if someone reached the last position
     */
    public boolean checkLastCellReached()
    {
        for(Player p : this.players)
        {
            if(p.getPosition() >= GameMaxCell ) return true;
        }
        return false;
    }

    /**
     *
     * @return true if one of the ending condition is reached
     */
    public boolean checkEndGame()
    {
        boolean out =checkCardCondition() || checkLastCellReached();
        if(out) this.isEnded = true;
        return  out;
    }

    /**
     *
     * @return get the productin decks with available production card
     */
    public Stack<ProductionCard>[][] getProductionDecks() {
        return productionDecks;
    }

    /**
     *
     * @param currentPapalSpaceToReach set the next papal space to reach
     */
    public void setCurrentPapalSpaceToReach(int currentPapalSpaceToReach) {
        this.currentPapalSpaceToReach = currentPapalSpaceToReach;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setNofplayers(int nofplayers) {
        this.nofplayers = nofplayers;
    }

    public LeaderCard[] getLeaders() {
        return leaders;
    }

    public List<CellScore> getScorePositions() {
        return scorePositions;
    }

    public List<PapalSpace> getPapalSpaces() {
        return papalSpaces;
    }

    public int getCurrentPapalSpaceToReach() {
        return currentPapalSpaceToReach;
    }

    public int getNofplayers() {
        return nofplayers;
    }

    /**
     *
     * @return true if the last turn is activated and we reached the last player before inkweel
     */
    public boolean IsEnded()
    {
        return this.isEnded && this.currentPlayer == ConstantValues.numberOfPlayer;
    }

}
