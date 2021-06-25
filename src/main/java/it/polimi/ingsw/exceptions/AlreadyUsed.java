package it.polimi.ingsw.exceptions;

import it.polimi.ingsw.enumeration.ErrorMessages;

/**
 * Indicate user already use this card/leader
 */
public class AlreadyUsed  extends AckManager{

    public  AlreadyUsed(String e)
    {
        super("Already used: "+ e);
        this.setErrorCode(ErrorMessages.AlreadyUsed);
    }
}