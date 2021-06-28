package it.polimi.ingsw.controller.interpreters;

import com.google.gson.JsonObject;
import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.controller.packets.*;
import it.polimi.ingsw.controller.packets.ACK;
import it.polimi.ingsw.controller.packets.UpdatePosition;

public class JsonInterpreterServer extends BasicJsonInterpreter {

    private ServerController controller;
    private int playerIndex;
    private String nickname;

    public JsonInterpreterServer(int playerIndex,ServerController serverController )
    {
        this.playerIndex = playerIndex;
        this.controller  = serverController;
        this.nickname    = null;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    /**
     *
     * @return the controller associated with this interpreter
     */
    public ServerController getController() {
        return controller;
    }

    /**
     * set player index
     * @param playerIndex
     */
    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }

    /**
     *  Dispatch the packet based on the "type" field
     * @param type      type of packet
     * @param content   custom type content
     */
    @Override
    public void dispatchPacket(String type,JsonObject content)
    {
        Packet<ServerController> packet = null;

        try {
            packet = BasicPacketFactory.getPacket(type,content, Class.forName("it.polimi.ingsw.controller.packets." + type));

            packet.setPlayerIndex(this.playerIndex);
            this.setResponse(packet.analyze(controller));

        } catch (ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }

    }

}


