package it.polimi.ingsw.controller.packets.serverpackets;

import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.controller.packets.Packet;
import it.polimi.ingsw.controller.packets.PacketManager;

public class DiscardResource extends Packet implements PacketManager {
    int quantity;

    public DiscardResource(int quantity) {
        super("DiscardResource");
        this.quantity = quantity;
    }


    @Override
    public Packet analyze(ServerController controller) {
        return null;
    }
}
