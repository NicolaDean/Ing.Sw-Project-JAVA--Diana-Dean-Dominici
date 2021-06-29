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
import it.polimi.ingsw.utils.DebugMessages;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * Game model, contain a general GAME STATUS of this match
 */
public class Game implements Serializable {
    private List<Player> players;
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


    /**
     *
     * @param x col
     * @param y row
     * @return get a specific production card from shop
     */
    public ProductionCard drawProductionCard(int x, int y)
    {
        return this.getProductionDecks()[x][y].peek();
    }

    /**
     *
     * @return index of current player inside players list
     */
    public int getCurrentPlayerIndex()
    {
        return this.currentPlayer;
    }

    /**
     *
     * @return player at "currentIndex" position
     */
    public Player getCurrentPlayer()
    {
        return this.players.get(this.currentPlayer);
    }

    /**
     *
     * @return the market
     */
    public Market getMarket()
    {
        return this.market;
    }

    /**
     *
     * @param nickname nickname to check
     * @return true if already exist
     */
    public boolean checkNickname(String nickname)
    {
        for(Player p : this.players)
        {
            if(nickname.equals(p.getNickname())) return true;
        }
        return false;
    }

    /**
     *
     * @return true if match is ended
     */
    public boolean isEnded()
    {
        return isEnded;
    }

    /**
     *
     * @return true if match is started
     */
    public boolean isGamestarted()
    {
        return gamestarted;
    }

    /**
     *
     * @param nickname nickname of player who want to enter
     * @return true if thers no space of nickname already taken
     */
    public boolean isFull(String nickname) {
        return (this.nofplayers==4 || gamestarted || checkNickname(nickname));

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
     * this method starts the game by shuffling the players and setting the currentPlayer (the one with the Inkwell)
     * @throws Exception if the are no players to start the game
     */

    public Player getPlayer(int index)
    {
        return players.get(index);
    }

    /**
     * start the game and shuffle players
     * @return a list of integer corresponding to the new order of players after initial shyffle
     * @throws NotEnoughPlayers if thers only one playr
     */
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
                    outIndexes[i] = currIndex;
                    System.out.println(i + " -->"+ currIndex);
                    p.setControllerIndex(i);
                }
                i++;
            }
            currIndex++;
        }


        gamestarted=true;

        currentPlayer = 0;

        return outIndexes;
    }


    /**
     * add positions bonus to player as from rules
     */
    public void setFirstTurnAdvantage()
    {
        players.get(0).setInkwell();

        //First turn advantage
        if(players.size() >2)
        {
            players.get(2).incrementPosition();
        }
        if(players.size() >3)
        {
            players.get(3).incrementPosition();
        }
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
    public boolean papalSpaceCheck()
    {
        boolean out = false;
        //PAPAL SPACE
        if(this.currentPapalSpaceToReach < this.papalSpaces.size())
        {
            out =  this.papalSpaces.get(this.currentPapalSpaceToReach).checkPapalSpaceActivation(this.players);
            while(out && this.currentPapalSpaceToReach+1 < this.papalSpaces.size()){
                this.currentPapalSpaceToReach++;
                out = this.papalSpaces.get(this.currentPapalSpaceToReach).checkPapalSpaceActivation(this.players);
            }

        }

        return out;
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

    /**
     *
     * @return players list
     */
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

        DebugMessages.printError("Card count = "+ p.getDashboard().countCard());
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

    /**
     * set current player who had turn right
     * @param currentPlayer  current player turn
     */
    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * set nofplayer in this game
     * @param nofplayers  number of player
     */
    public void setNofplayers(int nofplayers) {
        this.nofplayers = nofplayers;
    }

    /**
     *
     * @return deck of leaders
     */
    public LeaderCard[] getLeaders() {
        return leaders;
    }

    /**
     *
     * @return list of faith track cells
     */
    public List<CellScore> getScorePositions() {
        return scorePositions;
    }

    /**
     *
     * @return list of papal spaces
     */
    public List<PapalSpace> getPapalSpaces() {
        return papalSpaces;
    }

    /**
     *
     * @return the index corresponding to the current papal space to reach
     */
    public int getCurrentPapalSpaceToReach() {
        return currentPapalSpaceToReach;
    }

    /**
     *
     * @return number of player in this match
     */
    public int getNofplayers() {
        return nofplayers;
    }

    /**
     *
     * @return true if the last turn is activated and we reached the last player before inkweel
     */
    public boolean IsEnded()
    {
        return this.isEnded && this.currentPlayer == 0;
    }

    /**
     * Draw 4 leaders for each player inside game
     */
    public void initializeLeaders()
    {
        for(Player p:this.players)
        {
            LeaderCard[] leaderCards = this.get4leaders();
            p.setLeaders(leaderCards);
        }
    }
}
