package it.polimi.ingsw.exceptions;

import it.polimi.ingsw.enumeration.ErrorMessages;

/**
 * Its impossible with all the check we do but this exception occur if thers 2 player with same name
 */
public class NicknameAlreadyTaken extends AckManager{

    public NicknameAlreadyTaken(String message) {
        super("Nickname already taken: " + message + "Chose another one");
        this.setErrorCode(ErrorMessages.NicknameAlreadyTaken);
    }
}
