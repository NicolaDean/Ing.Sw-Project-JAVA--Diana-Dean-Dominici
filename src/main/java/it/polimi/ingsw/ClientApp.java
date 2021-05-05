package it.polimi.ingsw;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.controller.interpreters.JsonInterpreterClient;

import java.io.IOException;

public class ClientApp {

    ClientController controller;


    public ClientApp()
    {

    }

    public  ClientController getController()
    {
        return this.controller;
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

        try {
            System.in.read();
            app.getController().selectServer("localhost",1234);
            app.getController().setNickname("Nicola",false);
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }


}
