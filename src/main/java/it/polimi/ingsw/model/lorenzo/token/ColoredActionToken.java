package it.polimi.ingsw.model.lorenzo.token;

import it.polimi.ingsw.enumeration.CardType;
import it.polimi.ingsw.model.lorenzo.LorenzoGame;
import it.polimi.ingsw.model.lorenzo.token.ActionToken;

public class ColoredActionToken implements ActionToken {
    CardType type;
    int bonus=2;

    public ColoredActionToken(CardType type) {
        this.type = type;
    }

    /*
    deck dispoction:
    Yl1 Vl1 Bl1 Gl1
    Yl2 Vl2 Bl2 Gl2
    Yl3 Vl3 Bl3 Gl3
     */

    @Override
    public void activateToken(LorenzoGame l) {

        for (int y = 0; y < 4; y++){
            for (int x = 0; x < 3; x++) {

                if( (!l.getProductionDecks()[x][y].empty()) && (l.getProductionDecks()[x][y].peek().getType()==type)) {

                    for (int i = 0; i < bonus; i++) {
                        l.discardProductionDeck(x, y);
                    }

                    return;
                }
            }
        }
    }

    @Override
    public boolean isSpecial() {
        return false;
    }

    @Override
    public CardType getType() {
        return type;
    }
}
