package it.polimi.ingsw.controller.packets;

import com.google.gson.Gson;

public abstract class  Packet implements PacketManager
{
    private transient  String type;
    private transient int playerIndex;

    public Packet(String type) {
        this.type = type;
    }

    public  Packet(String type,int playerIndex)
    {
        this.type = type;
        this.playerIndex = playerIndex;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String generateJson()
    {
        BasicPacket packet = new BasicPacket(this.getType(), this);

        return packet.toJson();
    }

    public int getPlayerIndex() {
        return playerIndex;
    }
    public void setPlayerIndex(int playerIndex)
    {
        this.playerIndex = playerIndex;
    }
}
