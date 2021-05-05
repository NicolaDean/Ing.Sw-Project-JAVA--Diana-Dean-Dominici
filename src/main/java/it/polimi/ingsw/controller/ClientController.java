package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.interpreters.JsonInterpreterClient;
import it.polimi.ingsw.controller.packets.Login;
import it.polimi.ingsw.controller.packets.LoginSinglePlayer;
import it.polimi.ingsw.utils.ErrorManager;
import it.polimi.ingsw.view.CLI;
import it.polimi.ingsw.view.View;

public class ClientController implements Runnable{

    ErrorManager          errorManager;
    View                  view;   //Interface with all view methods
    JsonInterpreterClient interpreter;

    public ClientController(boolean type)
    {
        if(type)view = new CLI();
        //else view = new GUI()

    }

    public void exampleACK(int code)
    {
        errorManager.getErrorMessageFromCode(code);//TODO magari oltre al numero passo la view che chiamera "showError"
    }


    public void startGame()
    {
        //View.HomePage()
        System.out.println("Start Game");
    }
    /**
     * Open a connection with this server
     * @param Ip server ip
     * @param port server port
     */
    public void selectServer(String Ip,int port)
    {
        //view.askServer
        //Crea thread per la connessione
    }

    public Login setNickname(String nickname,boolean singlePlayer)
    {
        if(singlePlayer)
            return new LoginSinglePlayer(nickname);
        else
            return new Login(nickname);
    }

    @Override
    public void run() {
        //Thread con server
    }
}
