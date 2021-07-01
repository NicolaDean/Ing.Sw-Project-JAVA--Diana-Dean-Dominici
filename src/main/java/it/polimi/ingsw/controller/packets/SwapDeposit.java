package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.controller.packets.Packet;
import it.polimi.ingsw.controller.packets.PacketManager;

/**
 * packet to ask the server to perform a swap
 */
public class SwapDeposit  extends Packet<ServerController> implements PacketManager<ServerController> {

    int pos1;
    int pos2;

    public SwapDeposit(int pos1,int pos2)
    {
        super("SwapDeposit");
        this.pos1 = pos1;
        this.pos2 = pos2;
    }

    @Override
    public Packet analyze(ServerController controller) {

        return null;
    }
}
