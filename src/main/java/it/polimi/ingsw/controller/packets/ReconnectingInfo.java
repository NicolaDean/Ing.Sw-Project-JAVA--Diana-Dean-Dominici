package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.model.cards.ProductionCard;
import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.market.balls.BasicBall;
import it.polimi.ingsw.model.minimodel.MiniModel;
import it.polimi.ingsw.model.minimodel.MiniPlayer;
import it.polimi.ingsw.utils.DebugMessages;

import java.util.Stack;

public class ReconnectingInfo extends Packet<ClientController> implements PacketManager<ClientController>{

    MiniModel model;
    BasicBall[][] miniBallsMarket;
    BasicBall miniBallDiscarted;
    int index;
    long gameId;

    public ReconnectingInfo(long gameId,MiniModel model, BasicBall[][] miniBallsMarket, BasicBall miniBallDiscarted) {
        super("ReconnectingInfo");
        this.model=model;
        this.miniBallsMarket=miniBallsMarket;
        this.miniBallDiscarted=miniBallDiscarted;
        this.gameId = gameId;
    }

    /**
     * exaxt mini market for client
     * @param m market
     * @return matrix of ball
     */
    public BasicBall[][] exactMiniMarket(Market m){
        return m.getResouces();
    }

    @Override
    public Packet analyze(ClientController controller)
    {
        DebugMessages.printError("INFORMATION SETTED WHEN RECONNECT");
        controller.setInformation(model,miniBallsMarket,miniBallDiscarted);
        //controller.abortHelp();
        return null;
    }
}
