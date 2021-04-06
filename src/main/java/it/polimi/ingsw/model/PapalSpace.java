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

    public void CheckPlayerPositions(List<Player> Player)
    {
        //Poi riceverà List<observer> invece che player
        //Player.notifyPlayer();
    }
}
