package it.polimi.ingsw.controller.packets;

import com.google.gson.JsonObject;
import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceList;

import java.util.List;

public class PendingCost  extends Packet implements PacketManager{

    List<Resource> pendingCost;

    public PendingCost(JsonObject content)
    {
        super("PendingCost");
    }

    public PendingCost(List<Resource> list)
    {
        super("PendingCost");
        this.pendingCost = list;
    }

    public List<Resource> getPendingCost() {
        return pendingCost;
    }

    @Override
    public void analyze(ServerController controller) {

    }
}
