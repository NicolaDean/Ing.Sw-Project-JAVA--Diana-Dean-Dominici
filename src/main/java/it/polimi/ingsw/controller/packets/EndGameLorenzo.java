package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ClientController;

/**
 * packet that the server sends when the game ends
 */
public class EndGameLorenzo  extends Packet<ClientController> implements PacketManager<ClientController> {
    Boolean lorenzoWin;
    int VP;

    public EndGameLorenzo(Boolean lorenzoWin,int VP) {
        super("EndGameLorenzo");
        this.lorenzoWin=lorenzoWin;
        this.VP=VP;
    }

    @Override
    public Packet analyze(ClientController controller) {
        controller.endGameLorenzo(lorenzoWin,VP);
        return null;
    }


}