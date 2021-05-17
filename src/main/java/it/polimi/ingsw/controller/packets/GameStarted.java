package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.model.MiniModel;
import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.market.balls.BasicBall;

import static it.polimi.ingsw.utils.ConstantValues.marketCol;
import static it.polimi.ingsw.utils.ConstantValues.marketRow;

public class GameStarted extends Packet<ClientController> implements PacketManager<ClientController>{

    MiniModel miniModel;

    public GameStarted(MiniModel miniModel) {
        super("GameStarted");
        this.miniModel=miniModel;
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
        controller.setMiniModel(miniModel); //da modificare quando servirà la modifica
        controller.printGameStarted();
        controller.abortHelp();
        return null;
    }
}
