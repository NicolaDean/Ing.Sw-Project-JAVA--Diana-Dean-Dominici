package it.polimi.ingsw.controller.packets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.controller.ServerController;

import java.lang.reflect.Type;

public class BasicPacket
{
    String type;
    PacketManager content;

    public BasicPacket(String type, PacketManager content)
    {
        this.type = type;
        this.content = content;
    }

    public String toJson()
    {
        Gson gson = new Gson();

        return  gson.toJson(this);
    }

    public static Packet getPacket(String type,JsonObject content,Type classType,int playerIndex)
    {
        Packet p=getPacket(type,content,classType);
        p.setPlayerIndex(playerIndex);
        return p;
    }
    public static Packet getPacket(String type,JsonObject content,Type classType)
    {
        Gson gson = new Gson();
        Packet p = gson.fromJson(content,classType);
        p.setType(type);
        return p;
    }

}
