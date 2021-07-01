package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.model.resources.Resource;

import java.util.List;

/**
 * pakcet that informs that a discount leader is active
 */
public class LeaderDiscountUpdate  extends Packet<ClientController> implements PacketManager<ClientController> {

    List<Resource> discount;
    int index;
    public LeaderDiscountUpdate(List<Resource> discount,int index) {
        super("LeaderDiscountUpdate");
        this.discount = discount;
        this.index = index;
    }

    @Override
    public Packet analyze(ClientController controller)
    {
        controller.updateDiscount(this.discount,index);
        return null;
    }


}