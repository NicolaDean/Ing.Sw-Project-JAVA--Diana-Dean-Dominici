package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.utils.DebugMessages;

/**
 * packet that informs about a lorenzo's action
 */
public class LorenzoPositionUpdate extends Packet<ClientController> implements PacketManager<ClientController> {

    int pos;
    public LorenzoPositionUpdate(int pos)
    {
        super("LorenzoPositionUpdate");
        this.pos = pos;
    }

    @Override
    public Packet analyze(ClientController controller) {

        controller.showMessage("Lorenzo new Pos" + pos);
        controller.lorenzoPositionUpdate(pos);
        return null;
    }
}
