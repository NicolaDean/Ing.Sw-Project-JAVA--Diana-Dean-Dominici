package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.controller.packets.Packet;
import it.polimi.ingsw.controller.packets.PacketManager;

/**
 * packet that the client sends to notify the server that he/she finisched the turn
 */
public class EndTurn   extends Packet<ServerController> implements PacketManager<ServerController>{

    public EndTurn()
    {
        super("EndTurn");
    }

    @Override
    public Packet analyze(ServerController controller) {
        return controller.nextTurn();
    }
}
