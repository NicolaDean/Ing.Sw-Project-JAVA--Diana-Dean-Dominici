package it.polimi.ingsw.exceptions;

public class IllegalSwap extends AckManager{

    public IllegalSwap(String message)
    {
        super("Illegal swap" + message);
    }
}
