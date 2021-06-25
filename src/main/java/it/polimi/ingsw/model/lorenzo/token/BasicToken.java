package it.polimi.ingsw.model.lorenzo.token;

import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.enumeration.CardType;
import it.polimi.ingsw.model.lorenzo.LorenzoGame;
import it.polimi.ingsw.view.observer.Observable;

import java.io.Serializable;

public abstract  class BasicToken extends Observable<ServerController> implements ActionToken, Serializable {

    @Override
    public boolean isSpecial() {
        return false;
    }
}
