package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.model.lorenzo.token.ActionToken;
import it.polimi.ingsw.utils.ConstantValues;
import it.polimi.ingsw.utils.DebugMessages;

/**
 * packet that informs that is lorenzo's turn
 */
public class LorenzoTurn extends Packet<ClientController> implements PacketManager<ClientController>{

    transient ActionToken actionToken;
    String CliColor;
    String image;
    //String guiResource

    public LorenzoTurn(ActionToken actionToken) {
        super("LorenzoTurn");
        this.image    =actionToken.getId();
        this.CliColor = actionToken.getColor();
    }


    @Override
    public Packet analyze(ClientController controller) {

        controller.lorenzoTurn(this.CliColor,this.image);

        return null;
    }
}
