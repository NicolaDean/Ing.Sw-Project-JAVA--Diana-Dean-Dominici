package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.controller.packets.Packet;
import it.polimi.ingsw.controller.packets.PacketManager;

/**
 * packet that informs a client that someone activated a leader
 */
public class LeaderActivated   extends Packet<ClientController> implements PacketManager<ClientController> {
    int card;

    public LeaderActivated(int card)
    {
        super("LeaderActivated");
        this.card=card;
    }


    @Override
    public Packet analyze(ClientController controller) {
        System.out.println("ECCOLO!");
        controller.setActivatedLeaders(card);
        return null;
    }
}
