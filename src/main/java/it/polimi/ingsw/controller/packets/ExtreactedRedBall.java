package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.utils.DebugMessages;

public class ExtreactedRedBall extends Packet<ClientController> implements PacketManager<ClientController>{

    int position;
    int player;

    public ExtreactedRedBall(int pos,int playerIndex)
    {
        super("UpdatePosition");
        this.position          = pos;
        this.player            = playerIndex;
    }

    @Override
    public Packet analyze(ClientController controller)
    {
        controller.incrementPositionPlayer(player,position);
        return null;
    }

}
