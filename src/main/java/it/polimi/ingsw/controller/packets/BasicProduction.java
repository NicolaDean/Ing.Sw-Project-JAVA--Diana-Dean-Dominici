package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.controller.packets.Packet;
import it.polimi.ingsw.controller.packets.PacketManager;
import it.polimi.ingsw.enumeration.ResourceType;


public class BasicProduction  extends Packet<ServerController> implements PacketManager<ServerController> {

    private ResourceType res1;
    private ResourceType res2;
    private ResourceType obt;
    private int index;

    public BasicProduction(ResourceType res1,ResourceType res2, ResourceType obt, int index)
    {
        super("BasicProduction");
        this.res1 = res1;
        this.res2 = res2;
        this.obt  = obt;
        this.index=index;

    }
    @Override
    public Packet analyze(ServerController controller) {
        return controller.basicProduction(res1,res2,obt,this.getClientIndex());
    }
}
