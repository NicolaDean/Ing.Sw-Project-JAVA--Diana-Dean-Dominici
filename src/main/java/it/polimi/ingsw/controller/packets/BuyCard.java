package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.controller.packets.Packet;
import it.polimi.ingsw.controller.packets.PacketManager;

public class BuyCard  extends Packet<ServerController> implements PacketManager<ServerController> {

    int x;
    int y;
    int position;


    public BuyCard(int x,int y,int pos,int playerIndex)
    {
        super("BuyCard",playerIndex);
        this.x =x;
        this.y =y;
        this.position = pos;
        //this.playerIndex = playerIndex;
    }
    @Override
    public Packet analyze(ServerController controller)
    {
       return  controller.buyCard(this.x,this.y,this.position,this.getPlayerIndex());
    }
}
