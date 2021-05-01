package it.polimi.ingsw.exceptions;

public class NotSoddisfedPrerequisite extends AckManager{

    public NotSoddisfedPrerequisite(String message)
    {
       super("NotSoddisfedPrerequisite: " + message);
    }
}
