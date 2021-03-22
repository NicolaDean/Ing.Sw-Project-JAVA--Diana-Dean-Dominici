package it.polimi.ingsw.model;

public class CellScore {
    int Position;
    int Score;

    boolean checkSurpassed(int pos)
    {
        return pos >= this.Position;
    }

    public int getScore() {
        return Score;
    }
}
