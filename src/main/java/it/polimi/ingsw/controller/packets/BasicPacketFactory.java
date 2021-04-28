package it.polimi.ingsw.controller.packets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.controller.packets.Packet;
import it.polimi.ingsw.controller.packets.PacketManager;

import java.lang.reflect.Type;

public class BasicPacketFactory
{
    String type;
    PacketManager content;

    public BasicPacketFactory(String type, PacketManager content)
    {
        this.type = type;
        this.content = content;
    }

    public String toJson()
    {
        Gson gson = new Gson();

        return  gson.toJson(this);
    }

    /**
     *
     * @param type "type" of basic packet
     * @param content json object with basicPacket "content"
     * @param classType type of packet
     * @param playerIndex sender player
     * @return a Basic packet incapsuling the custom "packetManager" packet
     */
    public static Packet getPacket(String type, JsonObject content, Type classType, int playerIndex)
    {
        Packet p=getPacket(type,content,classType);
        p.setPlayerIndex(playerIndex);
        return p;
    }

    /**
     *
     * @param type "type" of basic packet
     * @param content json object with basicPacket "content"
     * @param classType type of packet
     * @return a Basic packet incapsuling the custom "packetManager" packet
     */
    public static Packet getPacket(String type,JsonObject content,Type classType)
    {
        Gson gson = new Gson();
        Packet p = gson.fromJson(content,classType);
        p.setType(type);
        return p;
    }

}
