package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ServerController;

public interface PacketManager {

    /**
     * It allow to read packet content and execute necesary operations
     * @param controller  the client or server controller
     */
    public void   analyze(ServerController controller);

    /**
     * Generate a string with the json corresponding to its packetType
     * @return string with the json corresponding to its packetType
     */
    public String generateJson();
}
