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
                    wait(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("did the client " + this.index + " pong back? -> " + this.isPinged);
            if (!this.isPinged) {
                //TODO client che non risponde, va disconnesso???

                dead = true;
            }
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("client "+ index + " DISCONNECTED, NO PONG RECEIVED");


    }

    public void sendPing()
    {
        out.println(new Ping(this.index).generateJson());
        out.flush();
        this.isPinged = false;
        System.out.println("Ping sent");
    }

    public void setPinged()
    {
        synchronized (this)
        {
            System.out.println("RECEIVED PONG FROM CLIENT "+ this.index);
            this.isPinged = true;
            this.notify();
        }

    }

    public boolean getPinged()
    {
        return this.isPinged;
    }
}
