package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ServerController;

public class AskMove extends Packet<ServerController> implements PacketManager<ServerController>{

    int d1;
    int d2;
    int q;

    public AskMove(int d1, int d2, int q) {
        super("AskMove");
        this.d1 = d1;
        this.d2 = d2;
        this.q = q;
    }

    public Packet analyze(ServerController controller)
    {
        Packet p = controller.MoveResources(this.d1,this.d2,q,this.getClientIndex());
        if(p!=null)
        {
            if(p.getType().equals("ACK"))
            {
                controller.sendMessage(new MoveFailed(),this.getClientIndex());
                return p;
            }}
        return p;
    }

}
