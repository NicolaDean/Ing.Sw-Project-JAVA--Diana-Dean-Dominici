package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.model.lorenzo.token.ActionToken;
import it.polimi.ingsw.utils.DebugMessages;

public class EndGame extends Packet<ClientController> implements PacketManager<ClientController> {

    public EndGame() {
        super("EndGame");
        //TODO aggiungere classifica giocatori
    }

    @Override
    public Packet analyze(ClientController controller) {
        controller.endGame();
        return null;
    }
}
