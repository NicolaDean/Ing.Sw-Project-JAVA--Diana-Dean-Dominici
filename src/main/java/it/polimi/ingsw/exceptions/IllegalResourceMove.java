package it.polimi.ingsw.exceptions;

import it.polimi.ingsw.enumeration.ErrorMessages;

public class IllegalResourceMove extends AckManager{

    public IllegalResourceMove(String message)
    {
        super("Illegal move" + message);
        this.setErrorCode(ErrorMessages.IllegalResourceMove);
    }
}
