package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.model.resources.Resource;

import java.util.List;

public class TurnNotifySpecialPending extends TurnNotify{


    List<Resource> lastAction;
    public TurnNotifySpecialPending(List<Resource> lastAction)
    {
        this.setType("TurnNotifySpecialPending");
        this.lastAction = lastAction;


    }

    @Override
    public Packet analyze(ClientController controller)
    {
        System.out.println("It's your turn!");
        controller.abortHelp();
        controller.setMyTurn(true);
        //Ask to pay resources remaining before reconnect
        controller.askResourceExtraction(lastAction);
        controller.excecuteTurn();
        return null;
    }
}
