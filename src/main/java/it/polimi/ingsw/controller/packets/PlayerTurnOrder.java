package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ClientController;

import java.util.List;

public class PlayerTurnOrder extends Packet<ClientController> implements PacketManager<ClientController>{

    int [] indexes;

    public PlayerTurnOrder(int [] indexes)
    {
        super("PlayerTurnOrder");
        this.indexes = indexes;
    }

    @Override
    public Packet analyze(ClientController controller)
    {

        return null;
    }

}
