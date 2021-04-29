package it.polimi.ingsw.controller.packets.serverpackets;

import com.google.gson.JsonObject;
import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.controller.packets.Packet;
import it.polimi.ingsw.controller.packets.PacketManager;

public class Login extends Packet implements PacketManager {

    String nickname;


    public Login(String nickname) {
        super("Login");
        this.nickname = nickname;
    }

    @Override
    public Packet analyze(ServerController controller) {
        return controller.login(this.nickname);
    }
}
