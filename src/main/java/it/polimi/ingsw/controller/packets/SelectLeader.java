package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.model.cards.LeaderCard;

public class SelectLeader extends Packet<ServerController> implements PacketManager<ServerController> {

        LeaderCard[] leaders;

        public SelectLeader(LeaderCard[] leaders)
        {
            super("SelectLeader");
            this.leaders = leaders;
            //this.playerIndex = playerIndex;
        }

            @Override
            public Packet analyze(ServerController controller) {

            return  controller.setLeaders(this.leaders,this.getPlayerIndex());
            }

}


