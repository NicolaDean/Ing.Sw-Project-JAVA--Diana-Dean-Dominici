package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ClientController;

public class GameStarted extends Packet<ClientController> implements PacketManager<ClientController>{

    public GameStarted() {
        super("GameStarted");
    }

    @Override
    public Packet analyze(ClientController controller)
    {
        controller.printGameStarted();

        return null;
    }
}
