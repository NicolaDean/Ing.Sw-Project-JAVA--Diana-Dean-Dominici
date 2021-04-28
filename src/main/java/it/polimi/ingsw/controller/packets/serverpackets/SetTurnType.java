package it.polimi.ingsw.controller.packets.serverpackets;

import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.controller.packets.Packet;
import it.polimi.ingsw.controller.packets.PacketManager;

public class SetTurnType  extends Packet implements PacketManager {

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
