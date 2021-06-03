package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.model.cards.leaders.BonusProductionInterface;
import it.polimi.ingsw.model.cards.leaders.LeaderTradeCard;

import java.util.List;

public class LeaderTradeUpdate extends Packet<ClientController> implements PacketManager<ClientController> {


    LeaderTradeCard[] production;
    int index;
    public LeaderTradeUpdate(List<LeaderTradeCard> production, int index) {
        super("LeaderTradeUpdate");
        this.production = new LeaderTradeCard[production.size()];

        for(int i=0;i<production.size();i++)
        {
            this.production[i] = production.get(i);
        }

        this.index = index;
    }

    @Override
    public Packet analyze(ClientController controller)
    {
        controller.updateTrade(this.production,index);
        return null;
    }


}