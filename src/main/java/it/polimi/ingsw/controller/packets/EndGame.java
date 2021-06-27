package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.model.lorenzo.token.ActionToken;
import it.polimi.ingsw.utils.DebugMessages;

public class EndGame extends Packet<ClientController> implements PacketManager<ClientController> {

    String [] charts;
    int [] score;

    public EndGame(String [] charts,int [] score) {
        super("EndGame");
        this.charts=charts;
        this.score=score;
    }

    @Override
    public Packet analyze(ClientController controller) {
        controller.endGame(charts,score);
        return null;
    }


}
