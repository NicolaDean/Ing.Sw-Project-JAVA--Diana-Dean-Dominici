package it.polimi.ingsw.controller.interpreters;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.polimi.ingsw.WaitingRoom;
import it.polimi.ingsw.controller.packets.PacketManager;

/**
 * Elaborate String json packets and execute operation on model through an "observer style" approach used inside class PACKET
 */
public class BasicJsonInterpreter {

    JsonParser parser;
    PacketManager response;
    boolean responseAvailable;


    public BasicJsonInterpreter()
    {
        this.responseAvailable = false;
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

    /**
     *
     * @return true if is available a response
     */
    public boolean responseAvailable()
    {
        return this.responseAvailable;
    }

    /**
     *
     * @return get back the response available in json format
     */
    public String getResponse() {
        if(this.responseAvailable ==true)
        {
            this.responseAvailable = false;


            return this.response.generateJson();
        }
        else
        {
            return null;
        }
    }

    /**
     * Set a response packet
     * @param response packet to use as a response
     */
    public void setResponse(PacketManager response) {

        if(response != null)
        {
            this.responseAvailable = true;
            this.response = response;
        }

    }

}
