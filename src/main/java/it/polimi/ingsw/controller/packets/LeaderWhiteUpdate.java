package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.enumeration.ResourceType;
import it.polimi.ingsw.model.resources.Resource;

public class LeaderWhiteUpdate   extends Packet<ClientController> implements PacketManager<ClientController> {

    ResourceType resourceType;
    int index;
    public LeaderWhiteUpdate(ResourceType resourceType,int index) {
        super("LeaderWhiteUpdate");

        this.resourceType = resourceType;
        this.index = index;
    }

    @Override
    public Packet analyze(ClientController controller)
    {
        return null;
    }


}