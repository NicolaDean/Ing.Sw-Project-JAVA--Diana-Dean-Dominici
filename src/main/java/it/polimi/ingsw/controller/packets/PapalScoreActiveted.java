package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.model.PapalSpace;
import it.polimi.ingsw.model.factory.MapFactory;
import it.polimi.ingsw.utils.DebugMessages;
import java.util.List;

public class PapalScoreActiveted extends Packet<ClientController> implements PacketManager<ClientController>{

    int player;

    public PapalScoreActiveted(int playerIndex)
    {
        super("PapalScoreActiveted");
        this.player            = playerIndex;
    }

    @Override
    public Packet analyze(ClientController controller)
    {
        List<PapalSpace> papalSpaces= MapFactory.loadPapalSpacesFromJsonFile();

        controller.showMessage(controller.getMiniModel().getPlayers()[player].getNickname() + " Reach a papal space");
        for(PapalSpace ps: papalSpaces)
            if((controller.getMiniModel().getPlayers()[player].getPosition() >= ps.getInitialPosition()) && (controller.getMiniModel().getPlayers()[player].getPosition() <= ps.getFinalPosition()) )
                DebugMessages.printWarning("you got "+ps.getScore()+" VP for being in the papal space");

        return null;
    }
}