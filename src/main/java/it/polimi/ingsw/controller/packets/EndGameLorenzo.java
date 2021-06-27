package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ClientController;

public class EndGameLorenzo  extends Packet<ClientController> implements PacketManager<ClientController> {
    Boolean lorenzoWin;

    public EndGameLorenzo(Boolean lorenzoWin) {
        super("EndGameLorenzo");
        this.lorenzoWin=lorenzoWin;
    }

    @Override
    public Packet analyze(ClientController controller) {
        controller.endGameLorenzo(lorenzoWin);
        return null;
    }


}