package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.ResourceType;

import java.util.List;

public class DepositBonus extends LeaderCard{

    public DepositBonus(List<Resource> cost, int victoryPoints, ResourceType resourcetype) {
        super(cost, victoryPoints,resourcetype);
    }

    /**
     * activate the bonus of the card adding a deposit in the storage
     * @param storage
     */
    public void activate(Storage storage)
    {
        storage.initializeBonusDeposit(this.getType());
    }
}


/*
    public void activate(Player p)
    {
        p.addDepositBonus(this.getType());
    }
 */