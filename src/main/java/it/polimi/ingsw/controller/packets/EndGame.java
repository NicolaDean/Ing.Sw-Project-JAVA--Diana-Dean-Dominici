package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.model.lorenzo.token.ActionToken;
import it.polimi.ingsw.utils.DebugMessages;

public class EndGame extends Packet<ClientController> implements PacketManager<ClientController> {

    String [] charts;

    public EndGame(String [] charts) {
        super("EndGame");
        this.charts=charts;
    }

    @Override
    public Packet analyze(ClientController controller) {
        controller.endGame(charts);
        return null;
    }


}
