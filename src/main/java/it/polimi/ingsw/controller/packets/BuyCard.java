package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ServerController;

public class BuyCard extends Packet implements PacketManager {

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
