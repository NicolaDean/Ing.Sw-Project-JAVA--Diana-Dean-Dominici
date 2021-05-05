package it.polimi.ingsw.controller.interpreters;

import com.google.gson.JsonObject;
import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.controller.packets.BasicPacketFactory;
import it.polimi.ingsw.controller.packets.Packet;

public class JsonInterpreterClient extends BasicJsonInterpreter{

    private ClientController controller;


    public JsonInterpreterClient(ClientController clientController)
    {
        this.controller = clientController;
    }

    public void dispatchPacket(String type, JsonObject content)
    {
        Packet<ClientController> packet = null;

        try {
            packet = BasicPacketFactory.getPacket(type,content, Class.forName("it.polimi.ingsw.controller.packets." + type));
            this.setResponse(packet.analyze(controller));

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
