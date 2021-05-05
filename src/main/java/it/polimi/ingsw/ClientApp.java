package it.polimi.ingsw;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.controller.interpreters.JsonInterpreterClient;

public class ClientApp {

    JsonInterpreterClient interpreter;
    ClientController      controller;

    public ClientApp()
    {
        this.controller = new ClientController();
        this.interpreter = new JsonInterpreterClient(this.controller);
    }
}
