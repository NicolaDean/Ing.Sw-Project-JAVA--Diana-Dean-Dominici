package it.polimi.ingsw.exceptions;

import it.polimi.ingsw.enumeration.ErrorMessages;

/**
 * Not enought player to start match
 */
public class NotEnoughPlayers extends AckManager{

    public NotEnoughPlayers(String message) {
        super("Not enough player to start" + message);
        this.setErrorCode(ErrorMessages.NotEnoughPlayer);
    }
}
