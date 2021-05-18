package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ClientController;

public class NotifyOtherPlayerTurn  extends Packet<ClientController> implements PacketManager<ClientController>{

    String nickname;

    public NotifyOtherPlayerTurn(String nickname) {
        super("NotifyOtherPlayerTurn");
        this.nickname = nickname;
    }

    @Override
    public Packet analyze(ClientController controller)
    {
        System.out.println("It's "+nickname+"'s turn...");

        return null;
    }
}
