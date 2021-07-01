package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ClientController;

public class ReconnectionFailed  extends  Packet<ClientController> implements PacketManager<ClientController> {

    public ReconnectionFailed()
    {
        super("ReconnectionFailed");
    }

    @Override
    public Packet analyze(ClientController controller) {
        controller.failedReconnection();
        return null;
    }
}
