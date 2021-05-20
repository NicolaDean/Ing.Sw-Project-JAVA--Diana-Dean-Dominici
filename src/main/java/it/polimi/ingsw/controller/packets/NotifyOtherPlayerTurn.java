package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ClientController;

import java.util.concurrent.TimeUnit;

public class NotifyOtherPlayerTurn  extends Packet<ClientController> implements PacketManager<ClientController>{

    String nickname;

    public NotifyOtherPlayerTurn(String nickname) {
        super("NotifyOtherPlayerTurn");
        this.nickname = nickname;
    }

    @Override
    public Packet analyze(ClientController controller)

    {
        try {
            TimeUnit.MILLISECONDS.sleep(150);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("It's "+nickname+"'s turn...");

        return null;
    }
}
