package it.polimi.ingsw.exceptions;

import it.polimi.ingsw.enumeration.ErrorMessages;

/**
 * If user try to access a deposit bonus that he dosnt own
 */
public class NoBonusDepositOwned extends AckManager{

    public NoBonusDepositOwned()
    {
        super("No Deposit Bonus Owned");
        this.setErrorCode(ErrorMessages.WrongPosition);
    }
}
