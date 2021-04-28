package it.polimi.ingsw.controller.packets.serverpackets;

import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.controller.packets.Packet;
import it.polimi.ingsw.controller.packets.PacketManager;

public class SwapDeposit  extends Packet implements PacketManager {

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
        return controller.swapDeposit(this.pos1,this.pos2,this.getPlayerIndex());
    }
}
