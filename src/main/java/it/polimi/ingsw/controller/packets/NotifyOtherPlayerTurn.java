package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ClientController;

import java.util.concurrent.TimeUnit;

/**
 * packet that informs the players about who is currently playing
 */
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



        if(!nickname.equals(controller.getMiniModel().getPersonalPlayer().getNickname()))
            controller.showMessage("It's "+nickname+"'s turn...");
        else
            controller.showMessage("It's your turn!");
        controller.getMiniModel().setCurrentlyplaying(nickname);

        return null;
    }
}
