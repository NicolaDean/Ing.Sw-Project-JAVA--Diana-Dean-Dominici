package it.polimi.ingsw.model;

public class CellScore {
    int position;
    int score;

    boolean checkSurpassed(int pos)
    {
        return pos >= this.position;
    }

    public int getScore() {
        return score;
    }
}
