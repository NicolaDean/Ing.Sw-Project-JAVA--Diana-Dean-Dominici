package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.ResourceType;

import java.util.List;

public class LeaderTradeCard extends  LeaderCard implements Production{

    private List<Resource> rawMaterial;

    public LeaderTradeCard(List<Resource> cost, int victoryPoints) {
        super(cost, victoryPoints);
        this.rawMaterial = null;
    }

    @Override
    public void activate(Player p)
    {
        //Add himself to the Player production card list
    }


    //USER can select the card and call the method "changeRawMat()" or
    public void produce(Player P,ResourceType a, ResourceType b)
    {

    }

    @Override
    public boolean produce(Dashboard dashboard) {
        return false;
    }

    @Override
    public boolean produce(Dashboard dashboard, ResourceType obtain) {
        return false;
    }
}
