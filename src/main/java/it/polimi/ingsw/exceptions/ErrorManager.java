package it.polimi.ingsw.exceptions;

import it.polimi.ingsw.enumeration.ErrorMessages;

import java.util.Arrays;

public class ErrorManager {


    public void getErrorMessageFromCode(int code)
    {
        ErrorMessages[] m = ErrorMessages.values();
        System.out.println("Error code: " + m[code].toString());
    }
}
