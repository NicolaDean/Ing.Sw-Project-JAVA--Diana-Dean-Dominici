package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.model.cards.LeaderCard;

public class SelectLeader extends Packet<ServerController> implements PacketManager<ServerController> {

        LeaderCard[] leaders;
        int l1;
        int l2;

        public SelectLeader(LeaderCard[] leaders,int l1,int l2)
        {
            super("SelectLeader");
            this.leaders = leaders;
            this.l1 = l1;
            this.l2 = l2;
            //this.playerIndex = playerIndex;
        }

            @Override
            public Packet analyze(ServerController controller) {

            return  controller.setLeaders(this.leaders, l1, l2,this.getClientIndex());
            }

}


