package it.polimi.ingsw.exceptions;

public class EmptyDeposit extends AckManager{

    public  EmptyDeposit(String e)
    {
        super("Empty deposit : "+ e);
        setErrorCode(5);
    }
}
