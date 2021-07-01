package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.model.cards.ProductionCard;
import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.market.balls.BasicBall;
import it.polimi.ingsw.model.minimodel.MiniModel;
import it.polimi.ingsw.model.minimodel.MiniPlayer;

import java.util.Stack;

/**
 * packet that notifies the clients that the game started
 */
public class GameStarted extends Packet<ClientController> implements PacketManager<ClientController>{

    MiniPlayer[] players;
    Stack<ProductionCard>[][] productionDecks;
    BasicBall[][] miniBallsMarket;
    BasicBall miniBallDiscarted;
    int index;
    long gameId;

    public GameStarted(long gameId,int index,MiniPlayer[] players,Stack<ProductionCard>[][] productionDecks, BasicBall[][] miniBallsMarket, BasicBall miniBallDiscarted) {
        super("GameStarted");
        this.players=players;
        this.productionDecks=productionDecks;
        this.miniBallsMarket=miniBallsMarket;
        this.miniBallDiscarted=miniBallDiscarted;
        this.index = index;
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
        controller.setInformation(index,players,productionDecks,miniBallsMarket,miniBallDiscarted);

        controller.saveReconnectInfo(this.gameId);
        controller.printGameStarted();

        //controller.abortHelp();
        return null;
    }
}
