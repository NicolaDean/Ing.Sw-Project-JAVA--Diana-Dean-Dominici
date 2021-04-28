package it.polimi.ingsw.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.polimi.ingsw.controller.packets.ACK;
import it.polimi.ingsw.controller.packets.BasicPacket;
import it.polimi.ingsw.controller.packets.PacketManager;
import it.polimi.ingsw.controller.packets.UpdatePosition;

public class BasicJsonInterpreter {

    JsonParser parser;

    public BasicJsonInterpreter()
    {
        this.parser = new JsonParser();

    }

    /**
     * Recive a json packet and extract its type and content
     * @param json a json packet in the "basic format" of our protocol
     */
    public void analyzePacket(String json)
    {
        //Parse packet
        JsonObject packet = this.parser.parse(json).getAsJsonObject();

        //Extract info
        String      type    = packet.get("type").getAsString();
        JsonObject  content = packet.get("content").getAsJsonObject();

        //Call the dispatcher function
        dispatchPacket(type,content);
    }

    public void dispatchPacket(String type, JsonObject content)
    {

    }

}
