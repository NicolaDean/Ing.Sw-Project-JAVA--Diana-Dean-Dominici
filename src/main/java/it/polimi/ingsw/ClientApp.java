package it.polimi.ingsw;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.controller.interpreters.JsonInterpreterClient;

public class ClientApp {

    ClientController controller;


    public ClientApp()
    {

    }

    public void setViewType(boolean type)
    {
        this.controller = new ClientController(type);
    }

    /**
     * Show welcome page
     */
    public void start()
    {
        this.controller.startGame();
    }

    public static void main(String[] args)
    {
        ClientApp app = new ClientApp();
        app.setViewType(true);//CLI poi il bool verra caricato da args
        app.start();
    }
}
