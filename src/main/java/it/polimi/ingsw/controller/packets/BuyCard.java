package it.polimi.ingsw.controller.packets;

import com.google.gson.JsonObject;
import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.ProductionCard;

public class BuyCard extends Packet implements PacketManager {

    int x;
    int y;
    int position;

    public BuyCard(JsonObject content, int playerIndex) {
        super("BuyCard",playerIndex);

        this.x = content.get("x").getAsInt();
        this.y = content.get("y").getAsInt();
        this.position    = content.get("position").getAsInt();
        //this.playerIndex = playerIndex;
    }

    public BuyCard(int x,int y,int pos,int playerIndex)
    {
        super("BuyCard",playerIndex);
        this.x =x;
        this.y =y;
        this.position = pos;
        //this.playerIndex = playerIndex;
    }
    @Override
    public void analyze(ServerController controller)
    {
       controller.buyCard(this.x,this.y,this.position,this.getPlayerIndex());
    }
}
