package it.polimi.ingsw.model.lorenzo.token;

import it.polimi.ingsw.controller.packets.LorenzoPositionUpdate;
import it.polimi.ingsw.enumeration.CardType;
import it.polimi.ingsw.model.lorenzo.LorenzoGame;
import it.polimi.ingsw.utils.ConstantValues;
import it.polimi.ingsw.view.utils.CliColors;

public class BlackCrossToken extends BasicToken{
    int bonus=2;

    @Override
    public void activateToken(LorenzoGame l) {
        l.getLorenzo().incrementPosition(bonus);
        l.resetDeckToken();
        this.notifyObserver(c->c.broadcastMessage(-1,new LorenzoPositionUpdate(l.getLorenzo().getPosition())));
    }



    @Override
    public CardType getType() {
        return null;
    }

    @Override
    public String getColor() {
        return CliColors.WHITE_BACKGROUND+CliColors.BLACK_TEXT+"1";
    }
}
