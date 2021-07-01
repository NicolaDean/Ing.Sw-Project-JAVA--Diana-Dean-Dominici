package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.model.cards.LeaderCard;

/**
 * packet to inform the server which leader the client selected
 */
public class SelectLeader extends Packet<ServerController> implements PacketManager<ServerController> {

        int l1;
        int l2;

        public SelectLeader(int l1,int l2)
        {
            super("SelectLeader");
            this.l1 = l1;
            this.l2 = l2;
            //this.playerIndex = playerIndex;
        }

            @Override
            public Packet analyze(ServerController controller) {

            return  controller.setLeaders(l1, l2,this.getClientIndex());
            }

}


