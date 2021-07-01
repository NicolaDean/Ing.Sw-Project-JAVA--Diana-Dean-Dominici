package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.controller.packets.Packet;
import it.polimi.ingsw.controller.packets.PacketManager;
import it.polimi.ingsw.utils.DebugMessages;
import it.polimi.ingsw.view.GuiHelper;

/**
 * packet used to update the mini market for each client
 */
public class UpdateMiniMarket extends Packet<ClientController> implements PacketManager<ClientController>{
    Boolean dir;
    int pos;

    public UpdateMiniMarket(Boolean dir,int pos) {
        super("UpdateMiniMarket");
        this.dir=dir;
        this.pos=pos;
    }

    @Override
    public Packet analyze(ClientController controller)
    {
        if(dir) controller.exstractColumn(pos);
        else controller.exstractRow(pos);
        return null;
    }
}
