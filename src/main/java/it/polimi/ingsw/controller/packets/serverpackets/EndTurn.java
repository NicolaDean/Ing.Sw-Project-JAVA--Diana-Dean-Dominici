package it.polimi.ingsw.controller.packets.serverpackets;

import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.controller.packets.Packet;
import it.polimi.ingsw.controller.packets.PacketManager;

public class EndTurn  extends Packet implements PacketManager {

    public EndTurn()
    {
        super("EndTurn");
    }
    @Override
    public Packet analyze(ServerController controller) {
        return null;
    }
}