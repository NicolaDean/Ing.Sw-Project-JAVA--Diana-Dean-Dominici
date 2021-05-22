package it.polimi.ingsw.view.scenes;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.view.GuiHelper;
import it.polimi.ingsw.view.observer.Observable;


//TODO TROVARE UN MODO DI RENDERE I CONTROLLORI DELLE SCENE VISIBILI DALLE FUNZIONI DELLA GUI
public class WaitingStartScene {

    public void startGame()
    {
        GuiHelper.getGui().notifyObserver(ClientController::startGame);
    }
}
