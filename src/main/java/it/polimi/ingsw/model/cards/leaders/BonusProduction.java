package it.polimi.ingsw.model.cards.leaders;

import it.polimi.ingsw.enumeration.ResourceType;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.resources.Resource;

public interface BonusProduction {

    public void produce(Player dashboard, ResourceType pay) throws Exception;

    public Resource getProdCost();
}
