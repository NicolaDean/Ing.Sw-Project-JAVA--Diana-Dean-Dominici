package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.market.balls.BasicBall;

import static it.polimi.ingsw.utils.ConstantValues.marketCol;
import static it.polimi.ingsw.utils.ConstantValues.marketRow;

public class GameStarted extends Packet<ClientController> implements PacketManager<ClientController>{

    private BasicBall[][] miniMarketBalls;
    private BasicBall miniDiscardedResouce;

    public GameStarted(Market m) {
        super("GameStarted");
        miniMarketBalls=exactMiniMarket(m);
        miniDiscardedResouce=m.getDiscardedResouce();
    }

    public BasicBall[][] getMiniMarketBalls() {
        return miniMarketBalls;
    }

    public BasicBall getMiniDiscardedResouce() {
        return miniDiscardedResouce;
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
        controller.setMiniModel(miniMarketBalls,miniDiscardedResouce);
        controller.printGameStarted();
        controller.abortHelp();
        return null;
    }
}
