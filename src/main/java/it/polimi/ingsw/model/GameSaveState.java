package it.polimi.ingsw.model;

import java.io.Serializable;

public class GameSaveState implements Serializable {

    private static final long serialVersionUID = 6970123627732910952L;
    private final Game model;

    public GameSaveState(Game model) {
        this.model = model;
    }
}
