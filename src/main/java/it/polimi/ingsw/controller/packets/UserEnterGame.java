package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ClientController;

public class UserEnterGame extends Packet<ClientController> implements PacketManager<ClientController>
{
    private String nickname;
    private int playerIndex;

    public UserEnterGame(int index,String nickname)
    {
        super("UserEnterGame");
        this.playerIndex = index;
        this.nickname = nickname;
    }

    @Override
    public Packet analyze(ClientController controller)
    {
        controller.addPlayer(this.playerIndex,this.nickname);
        return null;
    }
}
