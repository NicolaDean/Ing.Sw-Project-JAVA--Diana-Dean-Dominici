package it.polimi.ingsw.model;


import it.polimi.ingsw.model.lorenzo.Lorenzo;

import java.io.Serializable;
import java.util.List;

public class PapalSpace  implements Serializable {

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

    public boolean checkPlayerInsidePapalSpace(Player p)
    {
        if(p.getPosition() >= this.initialPosition)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

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

    public boolean checkPapalSpaceActivation(List<Player> players)
    {
        boolean out = checkPlayersPositions(players);

        if(out && !isAlreadyActivate)
        {
            isAlreadyActivate=true;
            for(Player p:players)
            {
                if(this.checkPlayerInsidePapalSpace(p))
                {
                    p.increaseScore(this.score);
                }
            }
        }

        return out;

    }

    public boolean checkPapalSpaceActivation(List<Player> players, Lorenzo l) {
        boolean out = ((l.getPosition()>=this.finalPosition) || (players.get(0).getPosition() >=this.finalPosition));

        if(out)
        {
            isAlreadyActivate=true;
            for(Player p:players)
            {
                if(this.checkPlayerInsidePapalSpace(p))
                {
                    p.increaseScore(this.score);
                }
            }
        }

        return out;
    }
}
