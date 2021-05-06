package it.polimi.ingsw.view.observer;

import it.polimi.ingsw.controller.ClientController;

import java.util.function.Consumer;

public abstract class Observable {

    ClientController controller;

    public void setObserver(ClientController controller)
    {
        this.controller = controller;
    }

    public void notifyObserver(Consumer<ClientController> action)
    {
        action.accept(this.controller);
    }

}
