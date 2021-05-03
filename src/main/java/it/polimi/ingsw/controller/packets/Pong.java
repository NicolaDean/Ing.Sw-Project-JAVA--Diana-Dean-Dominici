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

        try
        {
            System.out.println("PONG of "+index );
            controller.getClients().get(index).notify();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
