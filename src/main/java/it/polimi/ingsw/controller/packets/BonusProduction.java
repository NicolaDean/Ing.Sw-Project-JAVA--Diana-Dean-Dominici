package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.controller.packets.Packet;
import it.polimi.ingsw.controller.packets.PacketManager;
import it.polimi.ingsw.enumeration.ResourceType;

/**
 * packet to ask a bonus production
 */
public class BonusProduction   extends Packet<ServerController> implements PacketManager<ServerController>{

    private int position;
    private ResourceType obt;

    public BonusProduction(int position,ResourceType obt)
    {
        super("BonusProduction");
        this.obt  = obt;
        this.position = position;

    }

    @Override
    public Packet analyze(ServerController controller) {
        Packet p=  controller.bonusProduction(this.position,this.obt,this.getClientIndex());

        if(p.getType().equals("ACK"))
        {
            controller.sendMessage(new  ProdFailed(),this.getClientIndex());
            return p;
        }
        return p;
    }
}
