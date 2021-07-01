package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.model.cards.ProductionCard;
import it.polimi.ingsw.utils.DebugMessages;

/**
 * packet that informs about a lorenzo's action
 */
public class LorenzoDiscardedCard extends Packet<ClientController> implements PacketManager<ClientController> {

    int x;
    int y;
    ProductionCard newCard;

    public LorenzoDiscardedCard(int x, int y, ProductionCard newCard)
    {
        super("LorenzoDiscardedCard");
        this.x = x;
        this.y = y;
        this.newCard = newCard;
    }

    @Override
    public Packet analyze(ClientController controller)
    {
        controller.showMessage("Lorenzo discarded Card");
        controller.lorenzoCardDiscard(x,y,newCard);
        return null;
    }
}
