package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.model.cards.ProductionCard;
import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.market.balls.BasicBall;
import it.polimi.ingsw.model.minimodel.MiniModel;
import it.polimi.ingsw.model.minimodel.MiniPlayer;

import java.util.Stack;

public class GameStarted extends Packet<ClientController> implements PacketManager<ClientController>{

    MiniPlayer[] players;
    Stack<ProductionCard>[][] productionDecks;
    BasicBall[][] miniBallsMarket;
    BasicBall miniBallDiscarted;
    int index;

    public GameStarted(int index,MiniPlayer[] players,Stack<ProductionCard>[][] productionDecks, BasicBall[][] miniBallsMarket, BasicBall miniBallDiscarted) {
        super("GameStarted");
        this.players=players;
        this.productionDecks=productionDecks;
        this.miniBallsMarket=miniBallsMarket;
        this.miniBallDiscarted=miniBallDiscarted;
        this.index = index;
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
        controller.printGameStarted();
        //controller.abortHelp();
        return null;
    }
}
