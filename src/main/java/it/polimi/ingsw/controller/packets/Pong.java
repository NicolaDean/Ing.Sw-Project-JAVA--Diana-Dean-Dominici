package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ServerController;

public class Pong extends Packet<ServerController> implements PacketManager<ServerController>{

    int index;

    public Pong(int index) {
        super("Pong");
        this.index=index;
    }

    @Override
    public Packet analyze(ServerController controller) {
        controller.getClients().get(index).notify();
        return null;
    }
}
