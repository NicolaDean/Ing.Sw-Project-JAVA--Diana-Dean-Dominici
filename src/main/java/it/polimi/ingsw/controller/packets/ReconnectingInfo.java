package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.model.cards.ProductionCard;
import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.market.balls.BasicBall;
import it.polimi.ingsw.model.minimodel.MiniModel;
import it.polimi.ingsw.model.minimodel.MiniPlayer;
import it.polimi.ingsw.utils.DebugMessages;

import java.util.Stack;

/**
 * packet for sharing the reconnection informations needed
 */
public class ReconnectingInfo extends Packet<ClientController> implements PacketManager<ClientController>{

    MiniModel model;
    BasicBall[][] miniBallsMarket;
    BasicBall miniBallDiscarted;
    int index;
    long gameId;
    boolean firstTurn;

    public ReconnectingInfo(long gameId, MiniModel model, BasicBall[][] miniBallsMarket, BasicBall miniBallDiscarted, boolean firstTurn) {
        super("ReconnectingInfo");
        this.model=model;
        this.miniBallsMarket=miniBallsMarket;
        this.miniBallDiscarted=miniBallDiscarted;
        this.gameId = gameId;
        this.firstTurn =firstTurn;
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
        controller.showMessage("INFORMATION SETTED WHEN RECONNECT");
        controller.setFirstTurnView(firstTurn);
        controller.setInformation(model,miniBallsMarket,miniBallDiscarted,firstTurn);
        //controller.abortHelp();
        return null;
    }
}
