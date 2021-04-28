package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ServerController;

public class MarketExtraction extends Packet implements PacketManager{

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
        return null;
    }
}
