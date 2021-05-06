package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.PingController;
import it.polimi.ingsw.controller.packets.Ping;
import it.polimi.ingsw.controller.packets.Pong;

import java.io.PrintWriter;

public class PongController extends PingController {


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


    public void setIndex(int index)
    {
        this.index = index;
    }
}
