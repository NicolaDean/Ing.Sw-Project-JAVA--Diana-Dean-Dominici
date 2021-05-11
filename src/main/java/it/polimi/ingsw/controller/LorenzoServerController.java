package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.packets.LorenzoTurn;
import it.polimi.ingsw.controller.packets.Packet;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.lorenzo.Lorenzo;
import it.polimi.ingsw.model.lorenzo.LorenzoGame;

public class LorenzoServerController extends ServerController{
    LorenzoGame game;

    public LorenzoServerController() {
        super();
        game=new LorenzoGame();
    }
    @Override
    public Packet nextTurn(){ //TODO nexturn di lorenzo
        setGame(game);
        ((LorenzoGame)game).nextTurn();
        //chiamerà checkEndGame() di game
        //se risulterà positivo chiudera anche la connessione in maniera safe
        return new LorenzoTurn(game.getTokenDrawn());
    }

}
