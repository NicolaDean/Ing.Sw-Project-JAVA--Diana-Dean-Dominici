package it.polimi.ingsw.model.cards.leaders;

import it.polimi.ingsw.enumeration.resourceType;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.resources.Resource;

public interface BonusProduction {

    public boolean produce(Player dashboard, resourceType pay);

    public Resource getProdCost();
}
