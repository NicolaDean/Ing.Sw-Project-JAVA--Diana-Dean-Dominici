package it.polimi.ingsw.exceptions;

import it.polimi.ingsw.enumeration.ErrorMessages;

public class AlreadyUsed  extends AckManager{

    public  AlreadyUsed(String e)
    {
        super("Already used: "+ e);
        this.setErrorCode(ErrorMessages.AlreadyUsed);
    }
}