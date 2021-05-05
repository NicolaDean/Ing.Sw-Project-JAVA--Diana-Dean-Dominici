package it.polimi.ingsw.exceptions;

import it.polimi.ingsw.enumeration.ErrorMessages;

public class NoBonusDepositOwned extends AckManager{

    public NoBonusDepositOwned()
    {
        super("No Deposit Bonus Owned");
        this.setErrorCode(ErrorMessages.WrongPosition.ordinal());
    }
}
