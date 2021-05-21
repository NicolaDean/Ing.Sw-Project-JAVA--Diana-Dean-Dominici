package it.polimi.ingsw.model.cards.leaders;

import it.polimi.ingsw.enumeration.ResourceType;
import it.polimi.ingsw.exceptions.NotSoddisfedPrerequisite;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.PrerequisiteCard;
import it.polimi.ingsw.model.resources.Resource;

import java.util.List;

public class DepositBonus extends LeaderCard {


    public DepositBonus(List<Resource> cost, List<PrerequisiteCard> cardPrequisite, int victoryPoints, ResourceType resourcetype) {
        super(cost,cardPrequisite, victoryPoints, resourcetype);
    }

    /**
     * initialize the bonus deposit inside the player's storage
     * @param player the player whom the deposit bonus needs to be initialized
     */
    public void activate(Player player)throws NotSoddisfedPrerequisite {

        super.activate(player);
        player.getDashboard().getStorage().initializeBonusDeposit(this.getType());

    }
}

