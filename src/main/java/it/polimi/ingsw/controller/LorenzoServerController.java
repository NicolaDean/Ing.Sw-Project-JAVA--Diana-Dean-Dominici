package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.packets.*;
import it.polimi.ingsw.exceptions.MatchFull;
import it.polimi.ingsw.exceptions.NicknameAlreadyTaken;
import it.polimi.ingsw.exceptions.NotEnoughPlayers;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.lorenzo.Lorenzo;
import it.polimi.ingsw.model.lorenzo.LorenzoGame;
import it.polimi.ingsw.utils.DebugMessages;

import javax.swing.plaf.metal.MetalBorders;
import java.util.concurrent.TimeUnit;

public class LorenzoServerController extends ServerController{

    public LorenzoServerController() {
        super(true);
        this.game = new LorenzoGame();
        DebugMessages.printError("SINGLE PLAYER GAME CREATED");
    }


    //TODO create a "lorenzoCardDrawedUpdate" (si puo mettere in Lorenzo game una lista di "drawedCard" come fa il player per BuycardUpdate)
    //Stessa cosa per la position di lorenzo (lorenzoUpdatePosition) e inserire lorenzo nel minimodel

    @Override
    public Packet login(String nickname)
    {
        try {
            this.game.addPlayer(nickname);

        } catch (NicknameAlreadyTaken nicknameAlreadyTaken) {
            nicknameAlreadyTaken.printStackTrace();
        } catch (Exception matchFull) {
            matchFull.printStackTrace();
        }

        return new ACK(0);

    }
    /**
     * nextTurn
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
        this.clients.get(0).sendToClient(new TurnNotify());
        return new LorenzoTurn(((LorenzoGame)game).getTokenDrawn());
    }

    @Override
    public void startGame()
    {
        try {
            this.game.startGame();
            this.clients.get(0).sendToClient(this.generateGameStartedPacket(this.generateMiniPlayer(),0));

            this.clients.get(0).sendToClient(new TurnNotify());
        } catch (NotEnoughPlayers notEnoughPlayers) {
            notEnoughPlayers.printStackTrace();
        }

    }

    /**
     * check papalspace position and ad point
     */
    @Override
    public void checkPapalSpaceActivation(){
        //TODO checkPapalSpaceActivation
    }


}
