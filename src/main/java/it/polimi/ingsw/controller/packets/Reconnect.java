package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.WaitingRoom;
import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.utils.DebugMessages;

public class Reconnect extends Packet<ServerController> implements PacketManager<ServerController> {

       String  nickname;
       long    id;
       String  ip;
       int     port;

    public Reconnect(String nickname,String ip,int port,long id)
    {
        super("Reconnect");
        this.nickname   = nickname;
        this.ip         = ip;
        this.port       = port;
        this.id         = id;

        //this.playerIndex = playerIndex;
    }

    @Override
    public Packet analyze(ServerController controller) {

        controller.setReconnect(this);
        return null;
    }

    public String getIp()
    {
        return this.ip;
    }

    public int getPort()
    {
        return this.port;
    }

    public String getNickname() {
        return nickname;
    }

    public long getId() {
        return this.id;
    }
}