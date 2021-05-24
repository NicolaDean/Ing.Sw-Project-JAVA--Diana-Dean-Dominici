package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.utils.DebugMessages;

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
        DebugMessages.printWarning("Player " + controller.getMiniModel().getPlayers()[player].getNickname() + " increment his position by" + position);
        controller.incrementPositionPlayer(player,position);
        return null;
    }

}