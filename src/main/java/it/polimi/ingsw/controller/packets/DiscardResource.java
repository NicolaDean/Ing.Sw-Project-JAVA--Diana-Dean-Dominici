package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.controller.packets.Packet;
import it.polimi.ingsw.controller.packets.PacketManager;
import it.polimi.ingsw.enumeration.ResourceType;
import it.polimi.ingsw.model.resources.Resource;


/**
 * packet used to discard resources
 */
public class DiscardResource extends Packet<ServerController> implements PacketManager<ServerController>{
    int quantity;
    ResourceType type;

    public DiscardResource(int quantity,ResourceType type) {
        super("DiscardResource");
        this.quantity = quantity;
        this.type = type;
    }


    @Override
    public Packet analyze(ServerController controller) {
        controller.getPendingGain().remove(new Resource(type,quantity));
        return controller.discardResource(this.quantity,this.getClientIndex());
    }
}
