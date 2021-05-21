package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.model.cards.leaders.BonusProductionInterface;

public class LeaderTradeUpdate extends Packet<ClientController> implements PacketManager<ClientController> {


    BonusProductionInterface[] production;
    int index;
    public LeaderTradeUpdate(BonusProductionInterface[] production,int index) {
        super("LeaderBonusUpdate");
        this.production = production;
        this.index = index;
    }

    @Override
    public Packet analyze(ClientController controller)
    {
        controller.updateTrade(this.production,index);
        return null;
    }


}