package it.polimi.ingsw.model.cards.leaders;

import it.polimi.ingsw.controller.packets.LeaderDiscountUpdate;
import it.polimi.ingsw.controller.packets.Packet;
import it.polimi.ingsw.enumeration.ResourceType;
import it.polimi.ingsw.exceptions.LeaderActivated;
import it.polimi.ingsw.exceptions.NotSoddisfedPrerequisite;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.PrerequisiteCard;
import it.polimi.ingsw.model.resources.Resource;

import java.util.List;

public class LeaderDiscountCard extends LeaderCard{


    public LeaderDiscountCard(List<Resource> cost, List<PrerequisiteCard> cardPrequisite, int victoryPoints, ResourceType type) {
        super(cost,cardPrequisite, victoryPoints, type);

    }

    @Override
    public void activate(Player p) throws NotSoddisfedPrerequisite, LeaderActivated {
        super.activate(p);
        p.addDiscount(new Resource(this.getType(), 1));
    }


    @Override
    public Packet updateMiniModel(Player p,int index)
    {
        return new LeaderDiscountUpdate(p.getDashboard().getDiscount(),index);
    }
}
