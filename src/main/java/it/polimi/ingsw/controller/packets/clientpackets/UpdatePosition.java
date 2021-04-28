package it.polimi.ingsw.controller.packets.clientpackets;

import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.controller.packets.Packet;
import it.polimi.ingsw.controller.packets.PacketManager;

public class UpdatePosition extends Packet implements PacketManager {

    int position;
    int player;

    public UpdatePosition(int pos,int playerIndex)
    {
        super("UpdatePosition");
        this.position          = pos;
        this.player  = playerIndex;
    }

    @Override
    public Packet analyze(ServerController controller) {

        System.out.println("Increment player "+ this.player+ " Position of : " + this.position);
        //Increment player position
        return null;
    }

}
