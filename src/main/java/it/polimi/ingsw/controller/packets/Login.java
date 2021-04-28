package it.polimi.ingsw.controller.packets;

import com.google.gson.JsonObject;
import it.polimi.ingsw.controller.ServerController;

public class Login extends Packet implements PacketManager{

    String nickname;

    public Login(JsonObject content) {
        super("Login");

        this.nickname = content.get("nickname").getAsString();
    }

    public Login(String nickname) {
        super("Login");
        this.nickname = nickname;
    }

    @Override
    public void analyze(ServerController controller) {
        controller.login(this.nickname);
    }
}
