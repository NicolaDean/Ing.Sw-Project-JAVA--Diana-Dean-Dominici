package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.ErrorManager;

public class ClientController {

    ErrorManager errorManager;

    public void exampleACK(int code)
    {
        errorManager.getErrorMessageFromCode(code);//TODO magari oltre al numero passo la view che chiamera "showError"
    }


}
