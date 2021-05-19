package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.market.balls.BasicBall;
import it.polimi.ingsw.model.minimodel.MiniModel;
import it.polimi.ingsw.model.minimodel.MiniPlayer;

public class GameStarted extends Packet<ClientController> implements PacketManager<ClientController>{

    MiniModel miniModel;
    BasicBall[][] miniBallsMarket;
    BasicBall miniBallDiscarted;

    public GameStarted(MiniModel miniModel,BasicBall[][] miniBallsMarket,BasicBall miniBallDiscarted) {
        super("GameStarted");
        this.miniModel=miniModel;
        this.miniBallsMarket=miniBallsMarket;
        this.miniBallDiscarted=miniBallDiscarted;
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
        controller.setInformation(miniModel,miniBallsMarket,miniBallDiscarted);
        controller.printGameStarted();
        //controller.abortHelp();
        return null;
    }
}
