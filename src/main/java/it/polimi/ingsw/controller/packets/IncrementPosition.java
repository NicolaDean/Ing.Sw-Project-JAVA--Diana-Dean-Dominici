package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.utils.DebugMessages;

/**
 * packet that tells the new position of a player
 */
public class IncrementPosition extends Packet<ClientController> implements PacketManager<ClientController>{

    int position;
    int player;

    public IncrementPosition(int pos,int playerIndex)
    {
        super("IncrementPosition");
        this.position          = pos;
        this.player            = playerIndex;
    }

    @Override
    public Packet analyze(ClientController controller)
    {
        DebugMessages.printWarning("" + controller.getMiniModel().getPlayers()[player].getNickname() + " new position is -> " + position);
        controller.incrementPositionPlayer(player,position);
        return null;
    }

}