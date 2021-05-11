package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.model.lorenzo.token.ActionToken;

public class EndGame extends Packet<ClientController> implements PacketManager<ClientController> {

    public EndGame() {
        super("EndGame");
        //TODO aggiungere classifica giocatori
    }

    @Override
    public Packet analyze(ClientController controller) {
        return null;
    }
}
