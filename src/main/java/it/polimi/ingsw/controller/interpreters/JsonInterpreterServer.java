package it.polimi.ingsw.controller.interpreters;

import com.google.gson.JsonObject;
import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.controller.packets.*;

public class JsonInterpreterServer extends BasicJsonInterpreter {

    private ServerController controller;
    private int playerIndex;


    public JsonInterpreterServer(int playerIndex,ServerController serverController )
    {
        this.playerIndex = playerIndex;
        this.controller = serverController;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public ServerController getController() {
        return controller;
    }


    //TODO: Nota: il controller contiene la view quindi al suo interno c'è tutto ciò che serve per eseguire il pacchetto
    /**
     *  Dispatch the packet based on the "type" field
     * @param type      type of packet
     * @param content   custom type content
     */
    public void dispatchPacket(String type,JsonObject content)
    {
        PacketManager packet;

        switch (type)
        {
            case "ACK":
                packet = BasicPacket.getPacket(type,content,ACK.class);
                //packet = new ACK(content);
                break;
            case "UpdatePosition":
                packet = BasicPacket.getPacket(type,content,UpdatePosition.class);
                //packet = new UpdatePosition(content);
                break;
            case "Login":
                packet = BasicPacket.getPacket(type,content,Login.class);
                //packet = new Login(content);
                System.out.println("DEBUG: -> " + packet.generateJson());
                break;
            case "Production":
                packet = BasicPacket.getPacket(type,content,Production.class);
                //packet = new Production(content,this.playerIndex);
                break;
            case "BasicProduction":
                packet = BasicPacket.getPacket(type,content,BasicProduction.class);
                break;
            case "MarketExtraction":
                packet = BasicPacket.getPacket(type,content,MarketExtraction.class);
                break;
            default:
                throw new IllegalStateException("Unknown  Packet type : " + type);
        }

        this.setResponse(packet.analyze(controller));
    }

}
