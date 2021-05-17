package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.controller.packets.Packet;
import it.polimi.ingsw.controller.packets.PacketManager;
import it.polimi.ingsw.utils.DebugMessages;

public class UpdatePosition extends Packet<ClientController> implements PacketManager<ClientController>{

    int position;
    int player;

    public UpdatePosition(int pos,int playerIndex)
    {
        super("UpdatePosition");
        this.position          = pos;
        this.player            = playerIndex;
    }

    @Override
    public Packet analyze(ClientController controller)
    {
        DebugMessages.printWarning("Player " + this.player + " discarded " + this.position + "resources so you incrementr your position by " + this.position);
        //Increment player position
        return null;
    }

}
