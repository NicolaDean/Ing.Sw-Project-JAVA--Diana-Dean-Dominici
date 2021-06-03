package it.polimi.ingsw.model.cards.leaders;

import it.polimi.ingsw.controller.packets.Packet;
import it.polimi.ingsw.controller.packets.StorageUpdate;
import it.polimi.ingsw.enumeration.ResourceType;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.PrerequisiteCard;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.utils.DebugMessages;

import java.util.List;

public class DepositBonus extends LeaderCard{


    public DepositBonus(List<Resource> cost, List<PrerequisiteCard> cardPrequisite, int victoryPoints, ResourceType resourcetype) {
        super(cost,cardPrequisite, victoryPoints, resourcetype);
    }

    /**
     * initialize the bonus deposit inside the player's storage
     * @param player the player whom the deposit bonus needs to be initialized
     */
    public void activate(Player player) throws NotSoddisfedPrerequisite, LeaderActivated {
        super.activate(player);
        //System.out.println(this.getType());
        int d=4;
        if(player.getDashboard().getStorage().getStorage()[3] == null)
            d=3;
        player.getDashboard().getStorage().initializeBonusDeposit(this.getType());

        if(DebugMessages.infiniteResources)
        {
            try {
                player.getDashboard().getStorage().safeInsertion(new Resource(this.getType(), 2), d);
            } catch (NoBonusDepositOwned noBonusDepositOwned) {
                noBonusDepositOwned.printStackTrace();
            } catch (WrongPosition wrongPosition) {
                wrongPosition.printStackTrace();
            } catch (FullDepositException e) {
                e.printStackTrace();
            }
        }

    }

    public Packet updateMiniModel(Player p,int index)
    {
        return new StorageUpdate(p.getDashboard().getStorage().getDeposits(),index);
    }
}

