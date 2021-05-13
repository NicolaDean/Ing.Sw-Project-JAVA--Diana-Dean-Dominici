package it.polimi.ingsw.exceptions;

import it.polimi.ingsw.enumeration.ErrorMessages;

public class IllegalSwap extends AckManager{

    public IllegalSwap(String message)
    {
        super("Illegal swap" + message);
        this.setErrorCode(ErrorMessages.IllegalSwap);
    }
}
