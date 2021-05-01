package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ServerController;

public interface PacketManager<T>{

    /**
     * It allow to read packet content and execute necesary operations
     * @param controller  the client or server controller
     * @return
     */
    public Packet <T>  analyze(T controller);

    /**
     * Generate a string with the json corresponding to its packetType
     * @return string with the json corresponding to its packetType
     */
    public String generateJson();

    /**
     * Set the owner of the packet
     */
    public void setPlayerIndex(int index);
}
