package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.enumeration.CardType;

public class PrerequisiteCard {
    private int level;
    private CardType type;

    public PrerequisiteCard(CardType type,int level)
    {
        this.level = level;
        this.type = type;
    }
    public int getLevel()
    {
        return this.level;
    }
    public CardType getType()
    {
        return this.type;
    }
}
