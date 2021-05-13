package it.polimi.ingsw.model.lorenzo;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.CellScore;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.lorenzo.token.ActionToken;
import it.polimi.ingsw.model.lorenzo.token.BlackCrossToken;
import it.polimi.ingsw.model.lorenzo.token.ColoredActionToken;
import it.polimi.ingsw.model.lorenzo.token.SpecialBlackCrossToken;
import it.polimi.ingsw.model.resources.Resource;

import java.util.Collections;
import java.util.Stack;

import static it.polimi.ingsw.enumeration.CardType.*;

public class LorenzoGame extends Game {
    ActionToken tokenDrawn;
    Lorenzo lorenzo;
    Stack<ActionToken> tokenDeck = new Stack<>();

    public LorenzoGame() {
        super();
        resetDeckToken();
        this.lorenzo=new Lorenzo();
    }

    public Lorenzo getLorenzo() {
        return lorenzo;
    }

    public int getCurrentPlayerIndex() {
        return 0;
    }

    public boolean isFull() {
        return getNofplayers()==1;

    }

    /**
     * function to add a new player to the game
     * @param nickname the nickname of the player
     * @throws Exception
     */
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
     * Discard resource of a player and increment other position
     * @param p player that want to discard
     * @param res resource to discard
     * @param pos deposit pos
     * @throws Exception wrong deposit
     */
    public void discardResource(Player p, Resource res, int pos) throws EmptyDeposit, WrongPosition {
        p.getDashboard().getStorage().safeSubtraction(res,pos);
        lorenzo.incrementPosition(res.getQuantity());
    }

    /**
     * //Check if someone surpass a papal space and in case add the score of papalToken to the players
     */
    public void papalSpaceCheck() {
        if(getCurrentPapalSpaceToReach() < getPapalSpaces().size())
        {
            //Check if someone surpass a papal space and in case add the score of papalToken to the players
            boolean out = getPapalSpaces().get(getCurrentPapalSpaceToReach()).checkPapalSpaceActivation(getPlayers(),lorenzo);
            while(out == true && getCurrentPapalSpaceToReach()+1 < getPapalSpaces().size()){
                setCurrentPapalSpaceToReach(getCurrentPapalSpaceToReach()+1);
                out = getPapalSpaces().get(getCurrentPapalSpaceToReach()).checkPapalSpaceActivation(getPlayers(),lorenzo);
            }

        }
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

    private boolean productionDeckIsEmty() {
        return ((productionDecks[3][0].size()==0)||(productionDecks[3][1].size()==0)||(productionDecks[3][2].size()==0)||(productionDecks[3][3].size()==0));
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

    /**
     *
     * @return true if the last turn is activated and we reached the last player before inkweel
     */
    public boolean IsEnded()
    {
        return this.isEnded;
    }
}
