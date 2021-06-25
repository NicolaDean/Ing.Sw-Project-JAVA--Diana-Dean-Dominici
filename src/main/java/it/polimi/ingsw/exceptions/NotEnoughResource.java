package it.polimi.ingsw.exceptions;

import it.polimi.ingsw.enumeration.ErrorMessages;

/**
 * occur when player dont have resources to pay
 */
public class NotEnoughResource extends AckManager
{
    public NotEnoughResource(String message)
    {
        super("NotEnoughResource to :" + message);
        this.setErrorCode(ErrorMessages.NotEnoughResources);
    }

}
