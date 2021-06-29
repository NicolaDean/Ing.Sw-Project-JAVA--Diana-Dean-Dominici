package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ClientController;

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