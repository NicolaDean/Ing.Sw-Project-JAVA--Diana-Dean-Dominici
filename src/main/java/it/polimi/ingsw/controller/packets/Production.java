package it.polimi.ingsw.controller.packets;

import com.google.gson.JsonObject;
import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.model.Player;

public class Production extends Packet implements PacketManager{

    int position;

    public Production(JsonObject content,int playerIndex) {
        super("Production",playerIndex);

        this.position    = content.get("position").getAsInt();
        //this.playerIndex = playerIndex;
    }

    public Production(int pos,int playerIndex)
    {
        super("Production",playerIndex);
        this.position = pos;
        //this.playerIndex = playerIndex;
    }

    @Override
    public void analyze(ServerController controller) {

        controller.production(this.position,this.getPlayerIndex());
    }

}
