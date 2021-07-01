package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.controller.packets.Packet;
import it.polimi.ingsw.controller.packets.PacketManager;

/**
 * packet that asks to activate a production on a card
 */
public class Production extends Packet<ServerController> implements PacketManager<ServerController> {

    int position;


    public Production(int pos)
    {
        super("Production");
        this.position = pos;
        //this.playerIndex = playerIndex;
    }

    @Override
    public Packet analyze(ServerController controller) {
        Packet p = controller.production(this.position,this.getClientIndex());


        if(p.getType().equals("ACK"))
        {
            controller.sendMessage(new ProdFailed(),this.getClientIndex());
            return p;
        }
        return p;


    }

}
