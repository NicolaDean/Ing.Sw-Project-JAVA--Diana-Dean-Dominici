package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ServerController;

public class Ping extends Packet<ServerController> implements PacketManager<ServerController>{

    int index;

    public Ping(int index) {
        super("Ping");
        this.index = index;
    }
    @Override
    public Packet analyze(ServerController controller) {
        return new Pong(index);
    }
}
