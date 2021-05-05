package it.polimi.ingsw.exceptions;

import it.polimi.ingsw.enumeration.ErrorMessages;

public class FullDepositException extends AckManager{

    public FullDepositException()
    {
        super("Deposit is full, Quantity exced available space");
        this.setErrorCode(ErrorMessages.InsertionFailed.ordinal());
    }
}
