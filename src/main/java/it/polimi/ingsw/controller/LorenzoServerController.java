package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.packets.LorenzoTurn;
import it.polimi.ingsw.controller.packets.Packet;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.lorenzo.Lorenzo;
import it.polimi.ingsw.model.lorenzo.LorenzoGame;

public class LorenzoServerController extends ServerController{
    LorenzoGame game;

    public LorenzoServerController() {
        super(true);
        game = new LorenzoGame();
    }

    /**
     *
     * @return Packet to send at client with
     */
    @Override
    public Packet nextTurn(){ //TODO nexturn di lorenzo
        ((LorenzoGame)game).nextTurn();
        if(game.checkEndGame()) lastTurn();
        if(game.IsEnded()) {
            endGame();
            return null;
        }
        return new LorenzoTurn(((LorenzoGame)game).getTokenDrawn());
    }

}
