package it.polimi.ingsw.controller.packets;

import com.google.gson.JsonObject;
import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.enumeration.ResourceType;

public class BasicProduction extends Packet implements PacketManager{

    transient int playerIndex;
    private ResourceType res1;
    private ResourceType res2;
    private ResourceType obt;


    public BasicProduction(JsonObject content,int playerIndex)
    {
        super("BasicProduction",playerIndex);
        this.res1 = ResourceType.valueOf(content.get("res1").getAsString());
        this.res2 = ResourceType.valueOf(content.get("res2").getAsString());
        this.obt  = ResourceType.valueOf(content.get("obt").getAsString());
    }
    public BasicProduction(ResourceType res1,ResourceType res2, ResourceType obt)
    {
        super("BasicProduction");
        this.res1 = res1;
        this.res2 = res2;
        this.obt  = obt;

    }
    @Override
    public void analyze(ServerController controller) {
        controller.basicProduction(res1,res2,obt,playerIndex);
    }
}
