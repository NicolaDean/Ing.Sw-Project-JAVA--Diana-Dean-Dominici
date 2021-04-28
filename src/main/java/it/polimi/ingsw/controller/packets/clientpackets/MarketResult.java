package it.polimi.ingsw.controller.packets.clientpackets;

import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.controller.packets.Packet;
import it.polimi.ingsw.controller.packets.PacketManager;
import it.polimi.ingsw.model.resources.Resource;

import java.util.List;

public class MarketResult  extends Packet implements PacketManager {

    List<Resource> gainedResources;
    int whiteBalls;

    public MarketResult(List<Resource> gainedResources,int white)
    {
        super("MarketResult");
        this.gainedResources = gainedResources;
        this.whiteBalls      = white;
    }
    @Override
    public Packet analyze(ServerController controller) {
        return null;
    }
}
