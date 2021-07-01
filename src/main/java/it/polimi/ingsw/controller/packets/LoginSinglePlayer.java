package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.utils.DebugMessages;

/**
 * packet used to perform a single plauyer login
 */
public class LoginSinglePlayer  extends Packet<ServerController> implements PacketManager<ServerController> {

        String nickname;


public LoginSinglePlayer(String nickname) {
        super("LoginSinglePlayer");
        this.nickname = nickname;
        }

    @Override
    public Packet analyze(ServerController controller)
    {
        DebugMessages.printError("SINGLE PLAYER LOGGED");
        controller.setSinglePlayer();
        return controller.login(this.nickname);
    }
}
