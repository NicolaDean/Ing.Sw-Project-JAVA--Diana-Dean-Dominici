package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.model.lorenzo.token.ActionToken;

public class LorenzoTurn extends Packet<ClientController> implements PacketManager<ClientController>{

    ActionToken actionToken;

    public LorenzoTurn(ActionToken actionToken) {
        super("LorenzoTurn");
        this.actionToken=actionToken;
    }


    @Override
    public Packet analyze(ClientController controller) {
        return null;
    }
}
