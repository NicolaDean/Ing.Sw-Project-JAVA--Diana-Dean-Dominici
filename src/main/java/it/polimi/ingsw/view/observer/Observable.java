package it.polimi.ingsw.view.observer;

import it.polimi.ingsw.controller.ClientController;

import java.util.function.Consumer;

public abstract class Observable<T> {

    transient T controller;

    /**
     * allow to set a generic T observer to this class
     * @param controller observer
     */
    public void setObserver(T controller)
    {
        this.controller = controller;
    }


    /**
     * execute a lambda function on the observer subscribed
     * @param action lambda function that accept "T" as parameters
     */
    public void notifyObserver(Consumer<T> action)
    {
        if(this.controller!=null)
            action.accept(this.controller);
    }

    /**
     *
     * @return the subscribed observer
     */
    public T getObserver()
    {
        return this.controller;
    }

}
