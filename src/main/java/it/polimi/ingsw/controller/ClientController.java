package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.packets.Login;
import it.polimi.ingsw.controller.packets.LoginSinglePlayer;
import it.polimi.ingsw.controller.packets.Packet;
import it.polimi.ingsw.utils.ErrorManager;
import it.polimi.ingsw.view.View;

public class ClientController{

    ErrorManager errorManager;
    View         view;   //Interface with all view methods


    public void exampleACK(int code)
    {
        errorManager.getErrorMessageFromCode(code);//TODO magari oltre al numero passo la view che chiamera "showError"
    }

    /**
     * Open a connection with this server
     * @param Ip server ip
     * @param port server port
     */
    public void selectServer(String Ip,int port)
    {
        //Crea thread per la connessione
    }

    /**
     * when user calla "login"
     * @param nickname
     * @param singlePlayer
     * @return
     */
    public Login setNickname(String nickname,boolean singlePlayer)
    {
        if(singlePlayer)
            return new LoginSinglePlayer(nickname);
        else
            return new Login(nickname);
    }

}
