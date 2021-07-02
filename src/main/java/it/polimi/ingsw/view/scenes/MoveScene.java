package it.polimi.ingsw.view.scenes;

import javafx.fxml.FXML;
import javafx.scene.control.DialogPane;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * ask how much res he want to move in a deposit
 */
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
