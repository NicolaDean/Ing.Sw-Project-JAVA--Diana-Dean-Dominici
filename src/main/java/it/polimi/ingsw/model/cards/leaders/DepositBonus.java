package it.polimi.ingsw.model.cards.leaders;

import it.polimi.ingsw.enumeration.ResourceType;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.resources.Resource;

import java.util.List;

public class DepositBonus extends LeaderCard {

    public DepositBonus(List<Resource> cost, int victoryPoints, ResourceType resourcetype) {
        super(cost, victoryPoints,resourcetype);
    }

    /**
     * initialize the bonus deposit inside the player's storage
     * @param player the player whom the deposit bonus needs to be initialized
     */
    public void activate(Player player)
    {
        player.getDashboard().getStorage().initializeBonusDeposit(this.getType());
    }
}

