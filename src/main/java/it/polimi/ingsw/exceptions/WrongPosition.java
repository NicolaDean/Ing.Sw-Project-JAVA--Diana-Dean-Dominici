package it.polimi.ingsw.exceptions;

import it.polimi.ingsw.enumeration.ErrorMessages;

public class WrongPosition extends AckManager
{
    public WrongPosition(String message)
    {
        super("Card setted in Not valid position" +message);
        this.setErrorCode(ErrorMessages.WrongPosition);
    }
}
