package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.model.PapalSpace;
import it.polimi.ingsw.model.factory.MapFactory;
import it.polimi.ingsw.utils.DebugMessages;
import java.util.List;

/**
 * packet that informs that a papalscore has been activated
 */
public class PapalScoreActiveted extends Packet<ClientController> implements PacketManager<ClientController>{

    public PapalScoreActiveted()
    {
        super("PapalScoreActiveted");
    }

    @Override
    public Packet analyze(ClientController controller)
    {
        controller.showMessage("Papal space activated!!!");
        return null;
    }
}