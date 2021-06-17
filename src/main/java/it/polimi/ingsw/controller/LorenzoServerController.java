package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.packets.*;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.lorenzo.Lorenzo;
import it.polimi.ingsw.model.lorenzo.LorenzoGame;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.utils.DebugMessages;

import javax.swing.plaf.metal.MetalBorders;
import java.util.concurrent.TimeUnit;

import static it.polimi.ingsw.enumeration.ResourceType.*;

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
    public Packet nextTurn(){
        saveGameState();
        if(!this.game.getPlayer(0).checkConnection())
        {
            //IF PLAYER IS OFFLINE PAUSE THE MATCH (is single player)
            paused = true;
            return null;
        }

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
    public void startGame() throws FullDepositException, NoBonusDepositOwned, WrongPosition {
        try {
            this.game.startGame();
            ((LorenzoGame)this.game).initializeTokens(this);

            //Generate Minimodel,Initialize cheats and send user the "gameStarted" packet
            this.initializeMinimodel();
            //this.clients.get(0).sendToClient(this.generateGameStartedPacket(this.generateMiniPlayer(),0));
            this.isStarted = true;
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
