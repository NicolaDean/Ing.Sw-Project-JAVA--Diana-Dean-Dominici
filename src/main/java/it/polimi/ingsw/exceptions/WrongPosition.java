package it.polimi.ingsw.exceptions;

import it.polimi.ingsw.enumeration.ErrorMessages;

/**
 * Not existing position, out of range (eg deposit number 10)
 */
public class WrongPosition extends AckManager
{
    public WrongPosition(String message)
    {
        super("Card setted in Not valid position" +message);
        this.setErrorCode(ErrorMessages.WrongPosition);
    }
}
