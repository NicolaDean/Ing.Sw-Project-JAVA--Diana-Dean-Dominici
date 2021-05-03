package it.polimi.ingsw.model.lorenzo;

import it.polimi.ingsw.model.CellScore;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.PapalSpace;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.ProductionCard;
import it.polimi.ingsw.model.factory.CardFactory;
import it.polimi.ingsw.model.factory.MapFactory;
import it.polimi.ingsw.model.lorenzo.token.ActionToken;
import it.polimi.ingsw.model.lorenzo.token.BlackCrossToken;
import it.polimi.ingsw.model.lorenzo.token.ColoredActionToken;
import it.polimi.ingsw.model.lorenzo.token.SpecialBlackCrossToken;
import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.resources.Resource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import static it.polimi.ingsw.enumeration.CardType.*;

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
    ActionToken tokenDrawn;
    Lorenzo lorenzo;
    Stack<ActionToken> tokenDeck = new Stack<>();

    public LorenzoGame()
    {
        resetDeckToken();
        this.lorenzo=new Lorenzo();
        this.market = new Market();
        this.productionDecks = CardFactory.loadProductionCardsFromJsonFile();
        this.leaders         = CardFactory.loadLeaderCardsFromJsonFile();
        this.papalSpaces     = MapFactory.loadPapalSpacesFromJsonFile();
        this.scorePositions  = MapFactory.loadCellScoresFromJsonFile();
        this.currentPapalSpaceToReach = 0;
        this.players = new ArrayList<>();
    }

    public Lorenzo getLorenzo() {
        return lorenzo;
    }

    public int getCurrentPlayerIndex() {
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
        if(nofplayers<1) {
            for (Player p: players) {
                if(p.getNickname().equals(nickname))
                    throw new Exception("Nickname already taken, please choose another nickname");
            }
            players.add(new Player(nickname, scorePositions.size()));
            this.nofplayers++;
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
        p.getDashboard().getStorage().safeSubtraction(res,pos);
        lorenzo.incresePosition(res.getQuantity());
    }

    /**
     * this function changes the turn and so the current player who is supposed to play
     * @return the new player that is supposed to play
     */
    public Player nextTurn(){
        currentPlayer=0;
        if(currentPlayer==-1)


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

        //increment score for current player
        int position = players.get(currentPlayer).getPosition();
        int i=-1;

        for (CellScore cell:scorePositions) {
            if (position >= cell.getPosition()) {
                i++;
            }
        }

        if(i!=-1 && !players.get(currentPlayer).getSurpassedcells()[i])
        {
            players.get(currentPlayer).getSurpassedcells()[i]=true;
            players.get(currentPlayer).increaseScore(scorePositions.get(i).getScore());
            if(i>0)
                players.get(currentPlayer).decreaseScore(players.get(currentPlayer).getLastadded());
            players.get(currentPlayer).setLastadded(scorePositions.get(i).getScore());
        }

        lorenzoTurn();
        return players.get(currentPlayer);
    }

    public Player startGame() throws Exception {
        if(nofplayers==0)
            throw new Exception("There are no players");
        Collections.shuffle(players);
        players.get(0).setInkwell();
        currentPlayer = 0;
        return players.get(currentPlayer);
    }

    public ActionToken getTokenDrawn() {
        return tokenDrawn;
    }

    public void lorenzoTurn() {
        currentPlayer=-1;
        tokenDrawn=drawTocken();
        lorenzo.activateToken(this,tokenDrawn);
    }

    /**
     * shuffle all token together
     */
    public void resetDeckToken(){
        int nofcrosstoken=3,
                nofspecialcrosstoken=1,
                nofytoken=1,
                nofbtoken=1,
                nofgtoken=1,
                nofvtoken=1,
                ntotal=nofbtoken+nofvtoken+nofgtoken+nofytoken+nofspecialcrosstoken+nofcrosstoken;

        tokenDeck=new Stack<>();

        for(int i=0;i<ntotal;i++) {
            if (nofbtoken > 0) {
                tokenDeck.add(new ColoredActionToken(BLUE));
                nofbtoken--;
            }
            if(nofytoken>0) {
                tokenDeck.add(new ColoredActionToken(YELLOW));
                nofytoken--;
            }
            if(nofgtoken>0){
                tokenDeck.add(new ColoredActionToken(GREEN));
                nofgtoken--;
            }
            if(nofvtoken>0) {
                tokenDeck.add(new ColoredActionToken(PURPLE));
                nofvtoken--;
            }
            if(nofcrosstoken>0) {
                tokenDeck.add(new BlackCrossToken());
                nofcrosstoken--;
            }
            if(nofspecialcrosstoken>0) {
                tokenDeck.add(new SpecialBlackCrossToken());
                nofspecialcrosstoken--;
            }
        }
        Collections.shuffle(tokenDeck);
    }

    public ActionToken drawTocken(){
        return tokenDeck.pop();
    }

    public void discardProductionDeck(int x,int y) {
        productionDecks[x][y].pop();
    }

}
