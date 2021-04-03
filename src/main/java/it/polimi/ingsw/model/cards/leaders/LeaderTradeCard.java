package it.polimi.ingsw.model.cards.leaders;

import it.polimi.ingsw.enumeration.ResourceType;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceOperator;

import java.util.List;

public class LeaderTradeCard extends LeaderCard implements BonusProduction {

    private ResourceType obtain;

    public LeaderTradeCard(List<Resource> cost, int victoryPoints, ResourceType type) {
        super(cost, victoryPoints, type);

        this.obtain = null;
    }


    @Override
    public boolean activate(Player p)
    {
        boolean out = super.activate(p);
        if(out)
        {
            p.addTradeBonus(this);
        }

        return out;

    }

    //USER can select the card and call the method "changeRawMat()" or
    @Override
    public boolean produce(Player p, ResourceType obtain)
    {
        int possession = ResourceOperator.extractQuantityOf(this.getType(),p.getDashboard().getAllAvailableResource());

        if(possession >= 1)
        {
            p.chestInsertion(new Resource(obtain,1));
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
