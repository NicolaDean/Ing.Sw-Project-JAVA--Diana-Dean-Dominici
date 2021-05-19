package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ClientController;

public class SwapFailed extends  Packet<ClientController> implements PacketManager<ClientController> {
    int d1;
    int d2;
    int index;

    public SwapFailed(int d1, int d2)
    {
        super("SwapFailed");
        this.d1 = d1;
        this.d2 = d2;
    }

    @Override
    public Packet analyze(ClientController controller) {
        controller.askSwap(d1, d2, index);
        return null;
    }
}