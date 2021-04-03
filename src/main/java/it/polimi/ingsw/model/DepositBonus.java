package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.ResourceType;

import java.util.List;

public class DepositBonus extends LeaderCard{

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


/*
    public void activate(Player p)
    {
        p.addDepositBonus(this.getType());
    }
 */