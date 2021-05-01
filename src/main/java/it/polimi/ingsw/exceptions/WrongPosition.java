package it.polimi.ingsw.exceptions;

public class WrongPosition extends AckManager
{
    public WrongPosition(String message)
    {
        super("Card setted in Not valid position" +message);
    }
}
