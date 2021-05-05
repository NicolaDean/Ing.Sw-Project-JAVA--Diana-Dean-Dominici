package it.polimi.ingsw.exceptions;

import it.polimi.ingsw.enumeration.ErrorMessages;

public class NicknameAlreadyTaken extends AckManager{

    public NicknameAlreadyTaken(String message) {
        super("Nickname already taken: " + message + "Chose another one");
        this.setErrorCode(ErrorMessages.NicknameAlreadyTaken.ordinal());
    }
}
