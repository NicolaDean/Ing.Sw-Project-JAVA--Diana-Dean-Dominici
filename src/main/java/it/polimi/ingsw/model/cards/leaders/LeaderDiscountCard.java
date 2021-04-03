package it.polimi.ingsw.model.cards.leaders;

import it.polimi.ingsw.enumeration.ResourceType;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.resources.Resource;

import java.util.List;

public class LeaderDiscountCard extends LeaderCard {


    public LeaderDiscountCard(List<Resource> cost, int victoryPoints, ResourceType type) {
        super(cost, victoryPoints, type);
    }

    @Override
    public boolean activate(Player p)
    {
        boolean out = super.activate(p);
        if(out) {
            p.addDiscount(new Resource(this.getType(), 1));
        }
        return out;
    }
}