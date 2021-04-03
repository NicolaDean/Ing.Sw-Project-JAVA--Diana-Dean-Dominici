package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.enumeration.ResourceType;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.resources.Resource;

public interface BonusProduction {


    public boolean produce(Player dashboard, ResourceType pay);

    public Resource getProdCost();
}
