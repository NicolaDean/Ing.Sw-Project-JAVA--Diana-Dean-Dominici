package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.ResourceType;

public interface BonusProduction {


    public boolean produce(Player dashboard, ResourceType pay);

    public Resource getProdCost();
}
