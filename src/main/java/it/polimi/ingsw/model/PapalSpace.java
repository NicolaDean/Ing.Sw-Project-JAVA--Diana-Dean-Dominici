package it.polimi.ingsw.model;


import java.util.List;

public class PapalSpace {
    int InitialPosition;
    int FinalPosition;


    public PapalSpace(int start,int finish)
    {
        this.InitialPosition = start;
        this.FinalPosition = finish;
    }

    public void CheckPlayerPositions(List<Player> Player)
    {
        //Poi riceverà List<observer> invece che player
        //Player.notifyPlayer();
    }
}
