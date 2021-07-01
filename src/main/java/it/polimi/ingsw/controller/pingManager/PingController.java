package it.polimi.ingsw.controller.pingManager;
import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.controller.packets.Ping;
import it.polimi.ingsw.utils.DebugMessages;

import java.io.PrintWriter;
import java.io.Serializable;

/**
 * Pong from server to client
 */
public class PingController extends GenericPing<ServerController> implements Serializable {

    boolean gameStarted = false;
    public PingController(int index, PrintWriter out) {
        super(index, out);
    }

    public void sendPing()
    {
        out.println(new Ping(this.index).generateJson());
        out.flush();
        this.isPinged = false;
        //System.out.println("Ping sended");
    }

    /**
     * awake the ping thread causing the ping to be sended back
     * called when pong is recived
     */
    public void setPinged()
    {
        synchronized (this)
        {
            //System.out.println("PING SETTATO A TRUE per "+ this.index);
            this.isPinged = true;
            this.notify();
        }

    }

    /**
     * a boolean that rappresent the game status, if game is not started when user disconnect is removed both from model and controller
     * if a user disconnect when this boolean is true the player inside the model will NOT be removed (instead his handler will)
     */
    public void setGameStarted()
    {
        this.gameStarted = true;
    }

    /**
     * If game not started remove player from game and server controller (making space for a new palyer)
     * If game is started interrupt the match (or FA for connection resistance)
     */
    @Override
    public void customOnDisconnect()
    {
        DebugMessages.printWarning("\nIl client "+ index + " Si  è disconnesso, nessun pong ricevuto\n");

        if(!gameStarted){
            notifyObserver(controller -> {controller.removeClient(this.index);});
        }
        else {
            notifyObserver(controller -> {controller.removeClient(this.index);});
        }
        //else -> interrompi la partita

    }
}
