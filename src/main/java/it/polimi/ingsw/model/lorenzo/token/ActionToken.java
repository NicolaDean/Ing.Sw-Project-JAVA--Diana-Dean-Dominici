package it.polimi.ingsw.model.lorenzo.token;

import it.polimi.ingsw.enumeration.CardType;
import it.polimi.ingsw.model.lorenzo.LorenzoGame;

public interface ActionToken {
    /**
     * Discard 2 Development Cards or Move the Black Cross token forward by 2 spaces
     * @param l lorenzo game
     */
    public void activateToken(LorenzoGame l);

    public CardType getType();

    public String getColor();

    public boolean isSpecial();

}
