package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.ResourceType;

import java.util.List;

public class LeaderDiscountCard extends LeaderCard{


    public LeaderDiscountCard(List<Resource> cost, int victoryPoints, ResourceType type) {
        super(cost, victoryPoints, type);
    }

    @Override
    public void activate(Player p)
    {
        p.addDiscount(new Resource(this.getType(),1));
    }
}
