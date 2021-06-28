package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.model.PapalSpace;
import it.polimi.ingsw.model.factory.MapFactory;
import it.polimi.ingsw.utils.DebugMessages;

import java.util.List;

public class PapalSpaceUpdate  extends Packet<ClientController> implements PacketManager<ClientController> {

    int index;
    boolean[] papalToken;

    public PapalSpaceUpdate(boolean[] papalToken,int index) {
        super("PapalSpaceUpdate");
        this.papalToken = papalToken;
        this.index = index;
    }

    @Override
    public Packet analyze(ClientController controller) {

        controller.updatePapalToken(papalToken,index);
        return null;
    }
}
