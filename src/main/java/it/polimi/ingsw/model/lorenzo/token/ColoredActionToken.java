package it.polimi.ingsw.model.lorenzo.token;

import it.polimi.ingsw.enumeration.CardType;
import it.polimi.ingsw.model.lorenzo.LorenzoGame;
import it.polimi.ingsw.model.lorenzo.token.ActionToken;

public class ColoredActionToken implements ActionToken {
    CardType type;
    int bonus=-2;

    @Override
    public void activateToken(LorenzoGame l) {
        //TODO
    }

    @Override
    public boolean isSpecial() {
        return false;
    }
}
