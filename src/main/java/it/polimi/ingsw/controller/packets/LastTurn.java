package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ClientController;

/**
 * packet that notifies that this is the last turn
 */
public class LastTurn   extends Packet<ClientController> implements PacketManager<ClientController>{

    public LastTurn() {
        super("LastTurn");
    }

    @Override
    public Packet analyze(ClientController controller)
    {
        controller.showMessage("Game Finish Prerequisites reached, Last Turn");
        return null;
    }
}