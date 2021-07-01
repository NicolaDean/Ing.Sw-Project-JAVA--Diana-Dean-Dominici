package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.utils.DebugMessages;

/**
 * packet to update the leaders
 */
public class UpdateLeaders  extends Packet<ClientController> implements PacketManager<ClientController> {

    LeaderCard[] leaderCards;
    int player;

    public UpdateLeaders(LeaderCard[] leaderCards, int playerIndex) {
        super("UpdateLeaders");
        this.leaderCards = leaderCards;
        this.player = playerIndex;
    }

    @Override
    public Packet analyze(ClientController controller) {
        controller.updateLeader(leaderCards,player);
        return null;
    }
}