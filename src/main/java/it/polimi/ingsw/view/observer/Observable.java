package it.polimi.ingsw.view.observer;

import it.polimi.ingsw.controller.ClientController;

import java.util.function.Consumer;

public abstract class Observable<T> {

    transient T controller;

    public void setObserver(T controller)
    {
        this.controller = controller;
    }


    public void notifyObserver(Consumer<T> action)
    {
        if(this.controller!=null)
            action.accept(this.controller);
    }

    public T getObserver()
    {
        return this.controller;
    }
}
