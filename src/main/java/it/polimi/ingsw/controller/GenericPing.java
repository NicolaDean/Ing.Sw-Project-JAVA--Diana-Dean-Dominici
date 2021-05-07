package it.polimi.ingsw.controller;

import it.polimi.ingsw.view.observer.Observable;

import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public abstract class GenericPing<T> extends Observable<T> implements Runnable{

    protected int index;
    protected PrintWriter out;
    protected boolean isPinged;
    public boolean dead = false;


    public GenericPing(int index, PrintWriter out)
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



        //Remove client from server controller
        customOnDisconnect();
    }

    public void customOnDisconnect()
    {

    }

    public void setIndex(int index)
    {
        this.index = index;
    }

    public void sendPing()
    {

    }




}
