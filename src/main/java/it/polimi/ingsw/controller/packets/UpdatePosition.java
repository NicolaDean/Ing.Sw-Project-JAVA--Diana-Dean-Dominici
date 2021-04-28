package it.polimi.ingsw.controller.packets;

import com.google.gson.JsonObject;
import it.polimi.ingsw.controller.ServerController;

public class UpdatePosition extends  Packet implements PacketManager{

    int position;
    int player;

    public UpdatePosition(JsonObject content) {
        super("UpdatePosition");
        this.position          = content.get("position").getAsInt();
        this.player  = content.get("player").getAsInt();
    }

    public UpdatePosition(int pos,int playerIndex)
    {
        super("UpdatePosition");
        this.position          = pos;
        this.player  = playerIndex;
    }

    @Override
    public void analyze(ServerController controller) {

        System.out.println("Increment player "+ this.player+ " Position of : " + this.position);
        //Increment player position
    }

}
