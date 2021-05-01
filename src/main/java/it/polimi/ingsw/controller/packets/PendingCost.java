package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.controller.packets.Packet;
import it.polimi.ingsw.controller.packets.PacketManager;
import it.polimi.ingsw.model.resources.Resource;

import java.util.List;

public class PendingCost  extends Packet<ClientController> implements PacketManager<ClientController> {

    List<Resource> pendingCost;


    public PendingCost(List<Resource> list)
    {
        super("PendingCost");
        this.pendingCost = list;
    }

    public List<Resource> getPendingCost() {
        return pendingCost;
    }

    @Override
    public Packet analyze(ClientController controller) {
        return null;
    }
}
