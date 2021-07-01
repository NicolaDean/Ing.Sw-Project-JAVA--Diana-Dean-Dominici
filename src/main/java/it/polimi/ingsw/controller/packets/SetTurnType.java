package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.controller.packets.Packet;
import it.polimi.ingsw.controller.packets.PacketManager;

/**
 * set what type of turn the player wants to perform
 */
public class SetTurnType  extends Packet<ServerController> implements PacketManager<ServerController>{

    int turnType;

    public SetTurnType(int turnType)
    {
        super("SetTurnType");
        this.turnType = turnType;
    }
    @Override
    public Packet analyze(ServerController controller) {
        return null;
    }
}
