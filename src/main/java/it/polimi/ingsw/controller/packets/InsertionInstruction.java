package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.model.resources.Resource;

/**
 * packet that sends the informations about an insertion (position)
 */
public class InsertionInstruction {

    Resource resource;
    int position;

    public InsertionInstruction(Resource resource, int position)
    {
        this.resource       = resource;
        this.position       = position;
    }


    public Packet apply(ServerController controller, int player)
    {
        return controller.storageInsertion(this.resource,this.position,player);
    }

    public Resource getResource()
    {
        return this.resource;
    }
}