package it.polimi.ingsw.model.lorenzo;

import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.controller.packets.LorenzoPositionUpdate;
import it.polimi.ingsw.enumeration.CardType;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.CellScore;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.ProductionCard;
import it.polimi.ingsw.model.factory.TokenFactory;
import it.polimi.ingsw.model.lorenzo.token.*;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.utils.ConstantValues;

import java.util.Collections;
import java.util.Stack;

public class  LorenzoGame extends Game {
    ActionToken tokenDrawn;
    Lorenzo lorenzo;
    Stack<BasicToken> tokenDeck = new Stack<>();
    ServerController notifier;

    public LorenzoGame() {
        super();
        this.lorenzo=new Lorenzo();
    }

    public void initializeTokens(ServerController controller)
    {
        this.notifier=controller;
        resetDeckToken();
    }

    public Lorenzo getLorenzo() {
        return lorenzo;
    }

    public int getCurrentPlayerIndex() {
        return 0;
    }


    /**
     *
     * Discard resource of a player and increment other position
     * @param qty resources discarter
     */
    public void discardResource(int qty)
    {
        lorenzo.incrementPosition(qty);
        this.notifier.broadcastMessage(-1,new LorenzoPositionUpdate(lorenzo.getPosition()));
    }
    /**
     * function to add a new player to the game
     * @param nickname the nickname of the player
     * @throws Exception
     */
    @Override
    public void addPlayer(String nickname) throws NicknameAlreadyTaken, MatchFull {
        if(getNofplayers()<1) {
            for (Player p: getPlayers()) {
                if(p.getNickname().equals(nickname))
                    throw new NicknameAlreadyTaken(nickname);
            }
            getPlayers().add(new Player(nickname, getScorePositions().size()));
            setNofplayers(getNofplayers()+1);
        }
        else
            throw new MatchFull("There are already 1 players");
    }

    /**
     * //Check if someone surpass a papal space and in case add the score of papalToken to the players
     */
    public boolean papalSpaceCheck() {
        boolean out = false;
        if(getCurrentPapalSpaceToReach() < getPapalSpaces().size())
        {
            //Check if someone surpass a papal space and in case add the score of papalToken to the players
            out = getPapalSpaces().get(getCurrentPapalSpaceToReach()).checkPapalSpaceActivation(getPlayers(),lorenzo);
            while(out && getCurrentPapalSpaceToReach()+1 < getPapalSpaces().size()){
                setCurrentPapalSpaceToReach(getCurrentPapalSpaceToReach()+1);
                out = getPapalSpaces().get(getCurrentPapalSpaceToReach()).checkPapalSpaceActivation(getPlayers(),lorenzo);
            }

        }

        return out;
    }


    /**
     * this function changes the turn and so the current player who is supposed to play
     * @return the new player that is supposed to play
     */
    public Player nextTurn(){
        setCurrentPlayer(0);

        papalSpaceCheck();
        checkFaithTrackScoreGain();
        lorenzoTurn();

        return getCurrentPlayer();
    }

    /**
     * check for each player if they surpassed a new scoreposition, in that case the player score is increased accordingly
     */
    public void checkFaithTrackScoreGain() {
        //increment score for current player
        int position = getCurrentPlayer().getPosition();
        int i=-1;

        for (CellScore cell:getScorePositions()) {
            if (position >= cell.getPosition()) {
                i++;
            }
        }

        if(i!=-1 && !getCurrentPlayer().getSurpassedcells()[i])
        {
            getCurrentPlayer().getSurpassedcells()[i]=true;
            getCurrentPlayer().increaseScore(getScorePositions().get(i).getScore());
            if(i>0)
                getCurrentPlayer().decreaseScore(getCurrentPlayer().getLastadded());
            getCurrentPlayer().setLastadded(getScorePositions().get(i).getScore());
        }
    }

    /**
     *
     * @return true if one of the ending condition is reached
     */
    public boolean checkEndGame(){
        boolean out = checkCardCondition() || checkLastCellReached() || (lorenzo.getPosition() >= GameMaxCell) || productionDeckIsEmty();
        if(out) this.isEnded = true;
        return  out;
    }

    /**
     *
     * @return true if thers an antire row of cards empty inside the shop (lorenzo discarded all)
     */
    private boolean productionDeckIsEmty() {

        for(int i=0;i< ConstantValues.colDeck;i++)
        {
            int countEmptyDeck =0;
            for(int j=0;j<ConstantValues.rowDeck;j++)
            {
                if(productionDecks[j][i].isEmpty())countEmptyDeck++;
            }

            if(countEmptyDeck==ConstantValues.rowDeck) return true;
        }
        return false;
    }

    public ActionToken getTokenDrawn() {
        return tokenDrawn;
    }

    private void lorenzoTurn() {
        tokenDrawn=drawTocken();
        lorenzo.activateToken(this,tokenDrawn);
    }


    /**
     * shuffle all token together
     */
    public void resetDeckToken(){

        tokenDeck = TokenFactory.loadTokenDeckFromJson(notifier);
        Collections.shuffle(tokenDeck);

    }

    /**
     * get action token drawed by lorenzo
     * @return action token drawed
     */
    public ActionToken drawTocken(){
        return tokenDeck.pop();
    }

    /**
     * discard one card by production deck
     * @param x x position
     * @param y y position
     */
    public void discardProductionDeck(int x,int y) {
        getProductionDecks()[x][y].pop();
    }

    public Stack<ProductionCard>[][] getDeck()
    {
        return this.productionDecks;
    }
    /**
     *
     * @return true if the last turn is activated and we reached the last player before inkweel
     */
    public boolean IsEnded()
    {
        return this.isEnded;
    }

    /**
     * Discard resource of a player and increment other position
     * @param qty resources discarter
     */

}
