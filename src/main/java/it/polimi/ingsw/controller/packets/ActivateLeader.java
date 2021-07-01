package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.controller.packets.Packet;
import it.polimi.ingsw.controller.packets.PacketManager;

/**
 * packet to send a request of leader activation
 */
public class ActivateLeader   extends Packet<ServerController> implements PacketManager<ServerController> {
    int pos;
    boolean action;//true activate false discard

    public ActivateLeader(int pos,boolean action)
    {
        super("ActivateLeader");
        this.action = action;
        this.pos = pos;
    }

    @Override
    public Packet analyze(ServerController controller) {
        return controller.activateLeader(this.pos,this.action,this.getClientIndex());
    }
}
