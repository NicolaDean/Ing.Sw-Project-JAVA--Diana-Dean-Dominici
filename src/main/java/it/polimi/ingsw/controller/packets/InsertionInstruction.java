package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.controller.packets.Packet;
import it.polimi.ingsw.model.resources.Resource;

public class InsertionInstruction {

    boolean location; // true storage, false chest
    Resource resource;
    int position;

    public  InsertionInstruction(Resource resource,int position)
    {
        this.location       = true;
        this.resource       = resource;
        this.position       = position;
    }
    public  InsertionInstruction(Resource resource)
    {
        this.location       = false;
        this.resource       = resource;
        this.position       = 0;
    }

    public Packet apply(ServerController controller, int player)
    {
        if(this.location)
        {
            return controller.storageExtraction(this.resource,this.position,player);
        }
        else
        {
            return controller.chestExtraction(resource,player);
        }
    }
}
