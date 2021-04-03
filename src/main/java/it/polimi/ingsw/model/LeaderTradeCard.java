package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.ResourceType;

import java.util.List;

public class LeaderTradeCard extends  LeaderCard implements BonusProduction {

    private ResourceType obtain;

    public LeaderTradeCard(List<Resource> cost, int victoryPoints, ResourceType type) {
        super(cost, victoryPoints, type);

        this.obtain = null;
    }


    @Override
    public void activate(Player p)
    {
        //Add himself to the Player production card list
    }

    public void setupProduction(){

    }

    //USER can select the card and call the method "changeRawMat()" or
    @Override
    public boolean produce(Player p, ResourceType obtain)
    {
        int possession = ResourceOperator.extractQuantityOf(obtain,p.getDashboard().getAllAvailableResource());

        if(possession > 1)
        {
            p.chestInsertion(new Resource(this.getType(),1));
            p.incrementPosition(); //Get a faith point
            return true;
        }
        else
        {
            return false;
        }

    }

    @Override
    public Resource getProdCost()
    {
        return new Resource(this.getType(),1);
    }


}
