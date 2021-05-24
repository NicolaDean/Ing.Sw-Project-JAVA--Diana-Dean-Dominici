package it.polimi.ingsw.model.cards.leaders;

import it.polimi.ingsw.controller.packets.LeaderTradeUpdate;
import it.polimi.ingsw.controller.packets.Packet;
import it.polimi.ingsw.enumeration.ResourceType;
import it.polimi.ingsw.exceptions.LeaderActivated;
import it.polimi.ingsw.exceptions.NotEnoughResource;
import it.polimi.ingsw.exceptions.NotSoddisfedPrerequisite;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.PrerequisiteCard;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceOperator;

import java.util.List;

public class LeaderTradeCard extends LeaderCard implements BonusProductionInterface{

    private ResourceType obtain;

    public LeaderTradeCard(List<Resource> cost, List<PrerequisiteCard> cardPrequisite, int victoryPoints, ResourceType type) {
        super(cost,cardPrequisite, victoryPoints, type);

        this.obtain = null;
    }


    @Override
    public void activate(Player p) throws NotSoddisfedPrerequisite, LeaderActivated {
        super.activate(p);
        p.addTradeBonus(this);
    }

    //USER can select the card and call the method "changeRawMat()" or

    public void produce(Player p, ResourceType obtain) throws NotEnoughResource {
        int possession = ResourceOperator.extractQuantityOf(this.getType(),p.getDashboard().getAllAvailableResource());

        if(possession >= 1)
        {
            p.chestInsertion(new Resource(obtain,1));
            p.incrementPosition(); //Get a faith point
        }
        else
        {
            throw new NotEnoughResource("");
        }

    }

    @Override
    public Resource getProdCost()
    {
        return new Resource(this.getType(),1);
    }

    @Override
    public Packet updateMiniModel(Player p,int index)
    {
        return new LeaderTradeUpdate(p.getBonusProduductions(),index);
    }

}
