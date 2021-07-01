package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.enumeration.ResourceType;
import it.polimi.ingsw.model.resources.Resource;

import java.util.List;

/**
 * pakcet that informs that a white ball leader is active
 */
public class LeaderWhiteUpdate   extends Packet<ClientController> implements PacketManager<ClientController> {

    ResourceType[] resourceType;
    int index;
    public LeaderWhiteUpdate(List<ResourceType> resourceType, int index) {
        super("LeaderWhiteUpdate");

        this.resourceType = new ResourceType[resourceType.size()];

        for(int i=0;i<resourceType.size();i++) this.resourceType[i] = resourceType.get(i);
        this.index = index;
    }

    @Override
    public Packet analyze(ClientController controller)
    {
        controller.updateWhiteBalls(this.resourceType,index);
        return null;
    }


}