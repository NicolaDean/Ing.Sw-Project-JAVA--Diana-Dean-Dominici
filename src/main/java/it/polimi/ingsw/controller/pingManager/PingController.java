package it.polimi.ingsw.controller.pingManager;
import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.controller.packets.Ping;

import java.io.PrintWriter;

public class PingController extends GenericPing<ServerController>{


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

    public void setPinged()
    {
        synchronized (this)
        {
            //System.out.println("PING SETTATO A TRUE per "+ this.index);
            this.isPinged = true;
            this.notify();
        }

    }

    public void setGameStarted()
    {
        this.gameStarted = true;
    }

    public boolean getPinged()
    {
        return this.isPinged;
    }

    /**
     * If game not started remove player from game and server controller (making space for a new palyer)
     * If game is started interrupt the match (or FA for connection resistance)
     */
    @Override
    public void customOnDisconnect()
    {
        System.out.println("Il client "+ index + " Si  è disconnesso, nessun pong ricevuto");

        if(!gameStarted) notifyObserver(controller -> {controller.removeClient(this.index);});
        //else -> interrompi la partita

    }
}