package it.polimi.ingsw.model;


import it.polimi.ingsw.model.lorenzo.Lorenzo;

import java.io.Serializable;
import java.util.List;

public class PapalSpace  implements Serializable {

    private int index;
    private int initialPosition;
    private int finalPosition;
    private int score;
    private boolean isAlreadyActivate;

    public PapalSpace(int start,int finish,int score)
    {
        this.initialPosition = start;
        this.finalPosition = finish;
        this.score = score;
        isAlreadyActivate=false;
    }

    public int getInitialPosition() {
        return initialPosition;
    }

    public int getFinalPosition() {
        return finalPosition;
    }

    public int getScore() {
        return score;
    }

    /**
     * check if a specific player is inside papal space or surpassed
     * @param p plauer to check
     * @return true if inside or surpassed
     */
    public boolean checkPlayerInsidePapalSpace(Player p)
    {
        if(p.getPosition() >= this.initialPosition)
        {
            p.increaseScore(this.score);
            p.setPapalToken(index);
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * check if a specific player surpassed the papal space
     * @param p player to check
     * @return true if surpassed
     */
    public boolean checkPlayerSurpassPapalSpace(Player p)
    {
        if(p.getPosition() >= this.finalPosition)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    /**
     *
     * @param players the list of players
     * @return true if some player reach this papalSpace
     */
    public boolean checkPlayersPositions(List<Player> players)
    {
        //Modifica i player a seconda dlela loro pos
        for(Player p:players)
        {
            if(this.checkPlayerSurpassPapalSpace(p))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * check if papal space is surpassed inside multiplayer
     * @param players list of all players
     * @return true if surpssed
     */
    public boolean checkPapalSpaceActivation(List<Player> players)
    {
        boolean out = checkPlayersPositions(players);

        if(out && !isAlreadyActivate)
        {
            isAlreadyActivate=true;
            for(Player p:players)
            {
                this.checkPlayerInsidePapalSpace(p);
            }
        }

        return out;
    }

    /**
     * check if papal space is surpassed in single player
     * @param players  list of all player
     * @param l        lorenzo position
     * @return         true if surpassed
     */
    public boolean checkPapalSpaceActivation(List<Player> players, Lorenzo l) {
        boolean out = ((l.getPosition()>=this.finalPosition) || (players.get(0).getPosition() >=this.finalPosition));

        if(out && !isAlreadyActivate)
        {
            isAlreadyActivate=true;
            for(Player p:players)
            {
                this.checkPlayerInsidePapalSpace(p);
            }
        }

        return out;
    }

    /**
     *
     * @param i relative position inside the map
     */
    public void setIndex(int i) {
        this.index = i;
    }
}
