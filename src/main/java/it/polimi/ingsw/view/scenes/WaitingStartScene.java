package it.polimi.ingsw.view.scenes;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.view.GuiHelper;
import it.polimi.ingsw.view.events.StorageUpdateEvent;
import it.polimi.ingsw.view.observer.Observable;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.stage.Stage;


//TODO TROVARE UN MODO DI RENDERE I CONTROLLORI DELLE SCENE VISIBILI DALLE FUNZIONI DELLA GUI
public class WaitingStartScene {


    @FXML
    Button start;

    public void startGame()
    {
        System.out.println("StartGame");
        GuiHelper.getGui().notifyObserver(ClientController::sendStartCommand);
        Stage root = GuiHelper.getStage();
        root.addEventFilter(StorageUpdateEvent.ANY,this::showMsg);
    }

    public void showMsg(StorageUpdateEvent event)
    {
        System.out.println(event.getMsg());
    }
}
