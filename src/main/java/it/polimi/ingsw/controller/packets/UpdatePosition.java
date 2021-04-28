package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ServerController;

public class UpdatePosition extends  Packet implements PacketManager{

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
