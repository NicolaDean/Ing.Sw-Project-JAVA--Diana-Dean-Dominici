package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.controller.ServerController;

/**
 * packet used to ping the clients
 */
public class Ping extends Packet<ClientController> implements PacketManager<ClientController> {

    int index;

    public Ping(int index) {
        super("Ping");
        this.index = index;
    }
    @Override
    public Packet analyze(ClientController controller)
    {
        if(controller.isConnected())
            controller.getPongController().setPonged();
        controller.setIndex(this.index);

        return new Pong(index);
    }
}
