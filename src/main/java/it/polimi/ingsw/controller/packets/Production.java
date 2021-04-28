package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ServerController;

public class Production extends Packet implements PacketManager{

    int position;


    public Production(int pos,int playerIndex)
    {
        super("Production",playerIndex);
        this.position = pos;
        //this.playerIndex = playerIndex;
    }

    @Override
    public Packet analyze(ServerController controller) {

        return controller.production(this.position,this.getPlayerIndex());
    }

}
