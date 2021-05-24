package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ServerController;

public class AskSwap extends Packet<ServerController> implements PacketManager<ServerController>{

    int d1;
    int d2;
    int index;

    public AskSwap(int d1, int d2, int index) {
        super("AskSwap");
        this.d1 = d1;
        this.d2 = d2;
        this.index = index;
    }

    public Packet analyze(ServerController controller)
    {
        Packet p = controller.swapDeposit(this.d1,this.d2,index);

        if(p!=null)
        {
        if(p.getType().equals("ACK"))
        {
            //controller.sendMessage(new SwapFailed(this.d1, this.d2),index);
            return p;
        }}
        return p;
    }

}
