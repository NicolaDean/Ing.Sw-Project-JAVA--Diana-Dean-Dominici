package it.polimi.ingsw.model.lorenzo.token;

import it.polimi.ingsw.model.lorenzo.LorenzoGame;

public class SpecialBlackCrossToken extends BlackCrossToken{
    int bonus=1;
    @Override
    public void activateToken(LorenzoGame l) {
        //TODO
    }

    @Override
    public boolean isSpecial() {
        return true;
    }
}
