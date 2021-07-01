package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ServerController;

/**
 * packet to notify the start of the game
 */
public class StartGame extends Packet<ServerController> implements PacketManager<ServerController>{

    public StartGame() {
        super("StartGame");
    }

    @Override
    public Packet analyze(ServerController controller) {
        try {
            controller.startGame();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
