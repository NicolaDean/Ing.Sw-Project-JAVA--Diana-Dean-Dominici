package it.polimi.ingsw.controller.packets.serverpackets;

import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.controller.packets.Packet;
import it.polimi.ingsw.controller.packets.PacketManager;

public class Production extends Packet implements PacketManager {

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
