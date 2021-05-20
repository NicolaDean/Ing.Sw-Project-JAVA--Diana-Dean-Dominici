package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.model.resources.Resource;

import java.util.List;

public class ChestUpdate extends  Packet<ClientController> implements PacketManager<ClientController> {

    public List<Resource> chest;
    int index;
    public ChestUpdate(List<Resource> chest,int index)
    {
        super("ChestUpdate");
        this.chest = chest;
        this.index = index;
    }

    @Override
    public Packet analyze(ClientController controller)
    {
        controller.chestUpdate(this.chest,this.index);
        return null;
    }
}

