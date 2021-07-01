package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.controller.packets.Packet;
import it.polimi.ingsw.controller.packets.PacketManager;
import it.polimi.ingsw.model.resources.Resource;

import java.util.List;

/**
 * packet that shows the result of an extraction
 */
public class MarketResult   extends Packet<ClientController> implements PacketManager<ClientController> {

    List<Resource>  gainedResources;
    int             whiteBalls;
    boolean         resended;

    public MarketResult(List<Resource> gainedResources,int white)
    {
        super("MarketResult");
        resended = false;
        this.gainedResources = gainedResources;
        this.whiteBalls      = white;
    }

    public MarketResult(List<Resource> gainedResources)
    {
        super("MarketResult");
        resended             = true;
        this.gainedResources = gainedResources;
        this.whiteBalls      = 0;
    }

    @Override
    public Packet analyze(ClientController controller) {
        controller.showMarketResult(this.gainedResources,this.whiteBalls);
        return null;
    }
}
