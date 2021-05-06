package it.polimi.ingsw.controller;
import it.polimi.ingsw.controller.packets.Ping;

import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public class PingController implements Runnable{

    int index;
    PrintWriter out;
    public boolean isPinged;
    public boolean dead = false;
    public PingController(int index, PrintWriter out)
    {
        this.index = index;
        this.out = out;
        this.isPinged = false;
    }

    @Override
    public void run() {
        while(!dead)
        {
            synchronized (this)
            {

                this.sendPing();
                try {
                    wait(15000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //System.out.println(this.index + " IS PINGED? : " + this.isPinged);
            if (!this.isPinged) {
                //TODO client che non risponde, va disconnesso???

                dead = true;
            }
            else
            {
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
        System.out.println("Il client "+ index + " Si  è disconnesso, nessun pong ricevuto");


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

    public boolean getPinged()
    {
        return this.isPinged;
    }
}
