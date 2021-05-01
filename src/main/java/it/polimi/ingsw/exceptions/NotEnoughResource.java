package it.polimi.ingsw.exceptions;

public class NotEnoughResource extends AckManager
{
    public NotEnoughResource(String message)
    {
        super("NotEnoughResource to :" + message);
    }

}
