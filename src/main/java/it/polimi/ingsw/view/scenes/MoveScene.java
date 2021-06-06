package it.polimi.ingsw.view.scenes;

import java.util.concurrent.atomic.AtomicInteger;

public class MoveScene extends BasicDialog{
    AtomicInteger value;

    public MoveScene(AtomicInteger a) {
        value=a;
    }

    public void selected1()
    {
        value.set(1);
    }

    public void selected2()
    {
        value.set(2);
    }
}
