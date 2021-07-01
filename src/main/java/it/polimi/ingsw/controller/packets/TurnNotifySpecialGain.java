package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.model.resources.Resource;

import java.util.List;

/**
 * a custom turnnotify for gains
 */
public class TurnNotifySpecialGain extends TurnNotify{


    List<Resource> lastAction;
    public TurnNotifySpecialGain(List<Resource> lastAction)
    {
        this.setType("TurnNotifySpecialGain");
        lastAction = lastAction;
    }

    @Override
    public Packet analyze(ClientController controller)
    {
        System.out.println("It's your turn!");
        controller.abortHelp();
        controller.setMyTurn(true);
        //Ask to pay resources remaining before reconnect
        controller.showMarketResult(lastAction,0);
        controller.excecuteTurn();
        return null;
    }
}
