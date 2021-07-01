package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.controller.packets.Packet;
import it.polimi.ingsw.controller.packets.PacketManager;

/**
 * packet to ask a marcket extraction
 */
public class MarketExtraction  extends Packet<ServerController> implements PacketManager<ServerController> {

    private boolean direction;
    private int pos;

    public MarketExtraction(boolean direction,int pos)
    {
        super("MarketExtraction");
        this.direction = direction;
        this.pos = pos;
    }

    @Override
    public Packet analyze(ServerController controller) {
        return controller.marketExtraction(this.direction,this.pos,this.getClientIndex());
    }
}
