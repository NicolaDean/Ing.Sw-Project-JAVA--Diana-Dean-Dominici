package it.polimi.ingsw;

import it.polimi.ingsw.controller.LorenzoServerController;
import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.controller.packets.ACK;
import it.polimi.ingsw.controller.packets.Packet;
import it.polimi.ingsw.controller.packets.Reconnect;
import it.polimi.ingsw.enumeration.ErrorMessages;
import it.polimi.ingsw.utils.DebugMessages;
import it.polimi.ingsw.view.utils.CliColors;
import it.polimi.ingsw.view.utils.Logger;

import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class WaitingRoom extends ClientHandler{


    List<ServerController> controllers;
    ServerController fakeController;
    private final ExecutorService executor;
    int currentClient = 0;

    public WaitingRoom(Socket socket, List<ServerController> controllers, ServerController fakeController, ExecutorService executor)
    {
        super(socket,fakeController);
        this.fakeController     = fakeController;
        this.controllers        = controllers;
        this.executor           = executor;
    }


    @Override
    public void run() {
        System.out.println("Waiting room...");
        waitClientMassages();
    }

    /**
     *
     * @return true if the player Logged in (Game players list of fake controller is not empty)
     */
    @Override
    public boolean exitCondition()
    {
        //boolean out = !(fakeController.getGame().getPlayers().size() == 1) && !fakeController.isReconnected();
        boolean out = !fakeController.isPaused();

        return out;
    }

    /**
     * execute packet on fake controller (becouse not logged yet)
     * @param message packets recived
     */
    @Override
    public void readMessage(String message)
    {
        System.out.println("------------------------------------------");
        try {

            this.getInterpreter().analyzePacket(message); //
            //this.checkLogin(message);
            System.out.println("COMMAND: -> " + message);
        } catch (Exception e) {
            System.out.println("NotJson: -> " + message);
        }
        System.out.println("------------------------------------------");

    }



}
