package it.polimi.ingsw.controller.pingManager;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.utils.DebugMessages;

import java.io.PrintWriter;

/**
 * pong from Client to Server
 */
public class PongController extends GenericPing<ClientController> {


    public PongController(int index, PrintWriter out) {
        super(index, out);
    }

    public void setPonged()
    {
        synchronized (this)
        {
            //System.out.println("PONG SETTATO A TRUE");
            this.isPinged = true;
            this.notify();
        }
    }


    /**
     * set index of this pong controller
     * @param index player index
     */
    public void setIndex(int index)
    {
        this.index = index;
    }

    @Override
    public void customOnDisconnect()
    {
        DebugMessages.printError("Il server  si  è disconnesso, nessun pong ricevuto");
    }
}
