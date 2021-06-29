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

/**
 * wait clients login packet (after user login this thread die)
 */
public class WaitingRoom extends ClientHandler{

    ServerController fakeController;

    /**
     * Waiting room is a class that accept clients connection before login packet
     * @param socket           client socket
     * @param fakeController   a server controller without clientsHandler list
     */
    public WaitingRoom(Socket socket, ServerController fakeController)
    {
        super(socket,fakeController);
        this.fakeController     = fakeController;
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
            e.printStackTrace();
        }
        System.out.println("------------------------------------------");

    }



}
