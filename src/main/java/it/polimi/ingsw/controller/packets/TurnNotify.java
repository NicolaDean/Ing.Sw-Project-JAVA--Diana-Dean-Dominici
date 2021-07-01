package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ClientController;

public class TurnNotify  extends Packet<ClientController> implements PacketManager<ClientController>{

    public TurnNotify() {
        super("TurnNotify");
    }

    @Override
    public Packet analyze(ClientController controller)
    {
        //controller.showMessage("It's your Turn");
        controller.abortHelp();
        controller.setMyTurn(true);
        controller.excecuteTurn();
        return null;
    }
}
