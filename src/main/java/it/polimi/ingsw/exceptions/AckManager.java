package it.polimi.ingsw.exceptions;

import it.polimi.ingsw.controller.packets.Packet;

public class AckManager extends Exception{

    public AckManager(String message)
    {
        super(message);
    }

    public Packet getAck()
    {
        return null;
    }
}
