package it.polimi.ingsw.view.observer;

import it.polimi.ingsw.controller.ClientController;

import java.util.function.Consumer;

public abstract class Observable<T> {

    T controller;

    public void setObserver(T controller)
    {
        this.controller = controller;
    }

    public void notifyObserver(Consumer<T> action)
    {
        action.accept(this.controller);
    }

}
