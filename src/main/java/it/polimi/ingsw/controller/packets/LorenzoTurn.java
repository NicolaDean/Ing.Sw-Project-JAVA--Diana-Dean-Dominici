package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.model.lorenzo.token.ActionToken;
import it.polimi.ingsw.utils.ConstantValues;
import it.polimi.ingsw.utils.DebugMessages;

public class LorenzoTurn extends Packet<ClientController> implements PacketManager<ClientController>{

    transient ActionToken actionToken;
    String CliColor;
    //String guiResource

    public LorenzoTurn(ActionToken actionToken) {
        super("LorenzoTurn");
        this.actionToken=actionToken;
        this.CliColor = actionToken.getColor();
    }


    @Override
    public Packet analyze(ClientController controller) {

        DebugMessages.printWarning("Lorenzo drawed "  +this.CliColor + " Token ");
        return null;
    }
}
