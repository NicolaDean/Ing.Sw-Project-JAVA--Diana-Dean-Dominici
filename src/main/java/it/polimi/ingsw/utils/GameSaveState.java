package it.polimi.ingsw.utils;

import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.model.Game;

import java.io.Serializable;

public class GameSaveState implements Serializable {

    private static final long serialVersionUID = 6970123627732910952L;
    private final ServerController model;

    public GameSaveState(ServerController model) {
        this.model = model;
    }

    public void restart(ServerController c)
    {

    }
}
