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
    /**
     *
     * @return the position inside faith track
     */
    public int getPosition() {
        return position;
    }

    /**
     *
     * @return the score of this cell
     */
    public int getScore() {
        return score;
    }
}
