package it.polimi.ingsw.exceptions;

import it.polimi.ingsw.enumeration.ErrorMessages;

/**
 * Inompatible resource swap, if user try to do a swap wich broke game rules
 */
public class IllegalSwap extends AckManager{

    public IllegalSwap(String message)
    {
        super("Illegal swap" + message);
        this.setErrorCode(ErrorMessages.IllegalSwap);
    }
}
