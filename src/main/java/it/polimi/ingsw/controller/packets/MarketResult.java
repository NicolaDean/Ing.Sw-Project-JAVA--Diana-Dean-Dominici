package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.controller.packets.Packet;
import it.polimi.ingsw.controller.packets.PacketManager;
import it.polimi.ingsw.model.resources.Resource;

import java.util.List;

public class MarketResult   extends Packet<ClientController> implements PacketManager<ClientController> {

    List<Resource> gainedResources;
    int whiteBalls;

    public MarketResult(List<Resource> gainedResources,int white)
    {
        super("MarketResult");
        this.gainedResources = gainedResources;
        this.whiteBalls      = white;
    }

    @Override
    public Packet analyze(ClientController controller) {
        controller.showMarketResult(this.gainedResources,this.whiteBalls);
        return null;
    }
}
