package it.polimi.ingsw.model.lorenzo.token;

import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.enumeration.CardType;
import it.polimi.ingsw.model.lorenzo.LorenzoGame;
import it.polimi.ingsw.view.observer.Observable;

import java.io.Serializable;

public abstract  class BasicToken extends Observable<ServerController> implements ActionToken, Serializable {

    String id;
    @Override
    public boolean isSpecial() {
        return false;
    }

    /**
     *
     * @return image name for gui
     */
    public String getId()
    {
        return id;
    }

    /**
     * set image name for gui
     * @param id image name
     */
    public void setId(String id) {
        this.id = id;
    }
}
