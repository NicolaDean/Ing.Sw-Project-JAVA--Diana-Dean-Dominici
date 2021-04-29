package it.polimi.ingsw.controller.interpreters;

import com.google.gson.JsonObject;
import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.controller.packets.*;
import it.polimi.ingsw.controller.packets.bidirectionalpackets.ACK;
import it.polimi.ingsw.controller.packets.clientpackets.UpdatePosition;
import it.polimi.ingsw.controller.packets.serverpackets.*;

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
                packet = BasicPacketFactory.getPacket(type,content, ACK.class);
                break;
            case "UpdatePosition":
                packet = BasicPacketFactory.getPacket(type,content, UpdatePosition.class);
                break;
            case "Login":
                packet = BasicPacketFactory.getPacket(type,content, Login.class);
                break;
            case "Production":
                packet = BasicPacketFactory.getPacket(type,content,Production.class);
                break;
            case "BasicProduction":
                packet = BasicPacketFactory.getPacket(type,content, BasicProduction.class);
                break;
            case "BonusProduction":
                packet = BasicPacketFactory.getPacket(type,content, BonusProduction.class);
                break;
            case "MarketExtraction":
                packet = BasicPacketFactory.getPacket(type,content,MarketExtraction.class);
                break;
            case "StorageMassExtraction":
                packet = BasicPacketFactory.getPacket(type,content,StorageMassExtraction.class);
                break;
            case "SwapDeposit":
                packet = BasicPacketFactory.getPacket(type,content,SwapDeposit.class);
                break;
            case "EndTurn":
                packet = BasicPacketFactory.getPacket(type,content, EndTurn.class);
                break;
            case "SetTurnType":
                packet = BasicPacketFactory.getPacket(type,content,SetTurnType.class);
                break;
            case "ActivateLeader":
                packet = BasicPacketFactory.getPacket(type,content, ActivateLeader.class);
                break;
            default:
                throw new IllegalStateException("Unknown  Packet type : " + type);
        }
        packet.setPlayerIndex(this.playerIndex);
        this.setResponse(packet.analyze(controller));

    }

}
