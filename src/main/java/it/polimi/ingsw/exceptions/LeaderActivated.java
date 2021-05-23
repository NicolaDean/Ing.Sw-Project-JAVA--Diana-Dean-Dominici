package it.polimi.ingsw.exceptions;

import it.polimi.ingsw.enumeration.ErrorMessages;

public class LeaderActivated extends AckManager{
    public LeaderActivated(String message) {
        super("Leader already activated" + message);
        this.setErrorCode(ErrorMessages.LeaderActivated);
    }
}