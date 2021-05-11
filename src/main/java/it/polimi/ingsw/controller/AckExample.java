package it.polimi.ingsw.controller;

import it.polimi.ingsw.view.View;

import java.util.function.Consumer;

public class AckExample {

    private Consumer<View> action;

    public void AckExample()
    {
        action = null;
    }


    public void setAction(Consumer<View> action)
    {
        this.action = action;
    }

    public void useAckManagerAction(View view)
    {
        action.accept(view);
        action = null;
    }
}
