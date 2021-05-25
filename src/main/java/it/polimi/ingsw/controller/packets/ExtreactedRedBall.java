package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.utils.DebugMessages;

public class ExtreactedRedBall extends Packet<ClientController> implements PacketManager<ClientController>{

    int position;
    int player;

    public ExtreactedRedBall(int pos,int playerIndex)
    {
        super("ExtreactedRedBall");
        this.position          = pos;
        this.player            = playerIndex;
    }

    @Override
    public Packet analyze(ClientController controller)
    {
        DebugMessages.printWarning(controller.getMiniModel().getPlayers()[player].getNickname()+" goes "+position+" cells forward");
        //controller.incrementPositionPlayer(player,position);

        //Commented becouse now is done by increment position packet
        // (if decomented player will be incremented 2 times in the minimodel but only one in the server)
        return null;
    }

}
