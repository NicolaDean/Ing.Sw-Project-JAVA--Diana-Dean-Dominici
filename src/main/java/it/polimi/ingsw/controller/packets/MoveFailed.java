package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ClientController;

import java.util.concurrent.TimeUnit;

public class MoveFailed extends  Packet<ClientController> implements PacketManager<ClientController> {


    public MoveFailed()
    {
        super("SwapFailed");

    }

    @Override
    public Packet analyze(ClientController controller)  {

        //System.out.println("swap failed inviato");
        controller.getView().askMoveResources();
        return null;
    }
}