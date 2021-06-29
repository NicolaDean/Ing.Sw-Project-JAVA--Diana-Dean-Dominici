package it.polimi.ingsw.model.lorenzo.token;

import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.controller.packets.LorenzoDiscardedCard;
import it.polimi.ingsw.enumeration.CardType;
import it.polimi.ingsw.model.cards.ProductionCard;
import it.polimi.ingsw.model.lorenzo.LorenzoGame;
import it.polimi.ingsw.model.lorenzo.token.ActionToken;
import it.polimi.ingsw.utils.ConstantValues;
import it.polimi.ingsw.view.observer.Observable;
import it.polimi.ingsw.view.utils.CliColors;

public class ColoredActionToken  extends BasicToken {
    CardType type;
    int bonus=2;

    public ColoredActionToken(CardType type,int bonus) {
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
        int remaining = bonus;
        int cicle;

        for (int y = 0; y < ConstantValues.colDeck; y++){
            for (int x = 0; x < ConstantValues.rowDeck; x++) {

                if( (!l.getProductionDecks()[x][y].empty()) && (l.getProductionDecks()[x][y].peek().getType()==type)) {

                    cicle = bonus - (bonus-remaining);

                    for (int i = 0; i < cicle; i++) {

                        //If stack is not empty try discard "bonus" cards
                        if( (!l.getProductionDecks()[x][y].empty()))
                        {
                            l.discardProductionDeck(x, y);
                            remaining--;

                            ProductionCard newCard = null;
                            if(!l.getProductionDecks()[x][y].empty()) newCard = l.getDeck()[x][y].peek();

                            int finalX = x;
                            int finalY = y;
                            ProductionCard finalNewCard = newCard;
                            this.notifyObserver(c->c.broadcastMessage(-1,new LorenzoDiscardedCard(finalX, finalY, finalNewCard)));
                        }
                    }

                    if(remaining ==0)
                        return;
                }
            }
        }
        if(remaining != 0)
        {
            //TODO notyfiObserver gioco finito
            return;
        }
    }



    @Override
    public CardType getType() {
        return type;
    }

    @Override
    public String getColor() {
        return ConstantValues.resourceRappresentation.getCardTypeColorRappresentation(type) + CliColors.BLACK_TEXT +  "Card";
    }
}
