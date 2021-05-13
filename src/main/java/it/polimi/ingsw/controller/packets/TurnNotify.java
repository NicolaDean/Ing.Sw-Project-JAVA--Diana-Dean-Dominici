package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ClientController;

public class TurnNotify  extends Packet<ClientController> implements PacketManager<ClientController>{

    public TurnNotify() {
        super("TurnNotify");
    }

    @Override
    public Packet analyze(ClientController controller)
    {
        System.out.println("Is your turn");
        return null;
    }
}
