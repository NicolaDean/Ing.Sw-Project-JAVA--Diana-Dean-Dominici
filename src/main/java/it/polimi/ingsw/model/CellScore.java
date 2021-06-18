package it.polimi.ingsw.model;

import java.io.Serializable;

public class CellScore  implements Serializable {
    private int position;
    private int score;


    public CellScore(int position,int score)
    {
        this.position = position;
        this.score = score;
    }
    boolean checkSurpassed(int pos)
    {
        return pos >= this.position;
    }

    public int getPosition() {
        return position;
    }

    public int getScore() {
        return score;
    }
}
