package it.polimi.ingsw.model;


import java.util.List;

public class PapalSpace {
    int initialPosition;
    int finalPosition;


    public PapalSpace(int start,int finish)
    {
        this.initialPosition = start;
        this.finalPosition = finish;
    }

    public void CheckPlayerPositions(List<Player> Player)
    {
        //Poi riceverà List<observer> invece che player
        //Player.notifyPlayer();
    }
}
