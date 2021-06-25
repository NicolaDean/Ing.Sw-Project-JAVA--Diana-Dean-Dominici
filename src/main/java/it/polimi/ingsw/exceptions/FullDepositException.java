package it.polimi.ingsw.exceptions;

import it.polimi.ingsw.enumeration.ErrorMessages;

/**
 * Indicate the deposit in which user try to insert was full
 */
public class FullDepositException extends AckManager{

    public FullDepositException()
    {
        super("Deposit is full, Quantity exced available space");
        this.setErrorCode(ErrorMessages.InsertionFailed);
    }
}
