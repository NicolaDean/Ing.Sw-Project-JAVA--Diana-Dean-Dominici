package it.polimi.ingsw.exceptions;

import it.polimi.ingsw.enumeration.ErrorMessages;

public class LeaderActivated extends AckManager{
    public LeaderActivated(String message) {
        super("Leader activated" + message);
        this.setErrorCode(ErrorMessages.MatchFull);
    }
}