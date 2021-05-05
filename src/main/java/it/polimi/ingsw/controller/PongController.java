package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.PingController;
import it.polimi.ingsw.controller.packets.Ping;
import it.polimi.ingsw.controller.packets.Pong;

import java.io.PrintWriter;

public class PongController extends PingController {


    public PongController(int index, PrintWriter out) {
        super(index, out);
    }

    @Override
    public void sendPing()
    {
        out.println(new Pong(this.index).generateJson());
        out.flush();
        this.isPinged = false;
        System.out.println("Ping sended");
    }

    public void setIndex(int index)
    {
        this.index = index;
    }
}
