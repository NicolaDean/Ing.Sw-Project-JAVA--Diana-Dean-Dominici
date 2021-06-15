package it.polimi.ingsw.model.cards.leaders;

import it.polimi.ingsw.enumeration.ResourceType;
import it.polimi.ingsw.exceptions.AlreadyUsed;
import it.polimi.ingsw.exceptions.NotEnoughResource;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.resources.Resource;

public interface BonusProductionInterface {

    public void produce(Player player, ResourceType pay) throws NotEnoughResource, AlreadyUsed;

    public Resource getProdCost();

    public void reset();
}
