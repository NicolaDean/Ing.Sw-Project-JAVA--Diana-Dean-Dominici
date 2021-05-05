package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ServerController;

public class LoginSinglePlayer extends Login{


    public LoginSinglePlayer(String nickname) {
        super(nickname);
    }

    @Override
    public Packet analyze(ServerController controller)
    {
        controller.setSinglePlayer();
        return controller.login(this.nickname);
    }
}
