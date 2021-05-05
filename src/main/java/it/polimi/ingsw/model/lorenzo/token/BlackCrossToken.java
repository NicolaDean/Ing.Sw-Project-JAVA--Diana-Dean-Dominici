package it.polimi.ingsw.model.lorenzo.token;

import it.polimi.ingsw.enumeration.CardType;
import it.polimi.ingsw.model.lorenzo.LorenzoGame;

public class BlackCrossToken implements ActionToken{
    int bonus=2;

    @Override
    public void activateToken(LorenzoGame l) {
        l.getLorenzo().incrementPosition(bonus);
    }

    @Override
    public boolean isSpecial() {
        return false;
    }

    @Override
    public CardType getType() {
        return null;
    }
}
