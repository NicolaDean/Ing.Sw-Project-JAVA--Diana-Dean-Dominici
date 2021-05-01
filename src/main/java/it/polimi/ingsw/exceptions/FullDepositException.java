package it.polimi.ingsw.exceptions;

public class FullDepositException extends AckManager{

    public FullDepositException()
    {
        super("Deposit is full, Quantity exced available space");
    }
}
