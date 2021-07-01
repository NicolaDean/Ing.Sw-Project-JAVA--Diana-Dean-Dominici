package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ClientController;

/**
 * packet that notifies that a production actually failed
 */
public class ProdFailed extends  Packet<ClientController> implements PacketManager<ClientController> {

        public ProdFailed()
        {
            super("ProdFailed");
        }

        @Override
        public Packet analyze(ClientController controller) {
            controller.askProduction();
        return null;
        }
}