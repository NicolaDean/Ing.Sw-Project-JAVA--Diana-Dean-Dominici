package it.polimi.ingsw.model;


import java.util.List;

public class PapalSpace {

    private int initialPosition;
    private int finalPosition;
    private int score;


    public PapalSpace(int start,int finish,int score)
    {
        this.initialPosition = start;
        this.finalPosition = finish;
        this.score = score;
    }

    public boolean checkPlayerPositions(Player p)
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
            if(this.checkPlayerPositions(p))
            {
                //Incrementa punteggio
                return true;
            }
        }
        return false;
    }
}
