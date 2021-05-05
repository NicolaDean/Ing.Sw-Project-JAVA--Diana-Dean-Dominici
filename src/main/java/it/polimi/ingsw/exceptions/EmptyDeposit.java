package it.polimi.ingsw.exceptions;

import it.polimi.ingsw.enumeration.ErrorMessages;

public class EmptyDeposit extends AckManager{

    public  EmptyDeposit(String e)
    {
        super("Empty deposit : "+ e);
        this.setErrorCode(ErrorMessages.ExtractionFailed.ordinal());
    }
}
