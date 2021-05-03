package it.polimi.ingsw.exceptions;

import it.polimi.ingsw.controller.packets.ACK;
import it.polimi.ingsw.controller.packets.Packet;

public class AckManager extends Exception{

    int errorCode;
    public AckManager(String message)
    {
        super(message);
    }

    public ACK getAck()
    {
        return new ACK(errorCode);
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
