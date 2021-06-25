package it.polimi.ingsw.exceptions;

import it.polimi.ingsw.enumeration.ErrorMessages;

/**
 * indicate this deposit is empty
 */
public class EmptyDeposit extends AckManager{

    public  EmptyDeposit(String e)
    {
        super("Empty deposit : "+ e);
        this.setErrorCode(ErrorMessages.ExtractionFailed);
    }
}
