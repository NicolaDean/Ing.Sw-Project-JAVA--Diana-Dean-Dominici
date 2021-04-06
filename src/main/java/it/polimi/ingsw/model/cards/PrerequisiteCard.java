package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.enumeration.CardType;

public class PrerequisiteCard {
    private int level;
    private CardType type;
    private int quantity;

    public PrerequisiteCard(CardType type,int level,int quantity)
    {
        this.level    = level;
        this.type     = type;
        this.quantity = quantity;
    }
    public int getLevel()
    {
        return this.level;
    }
    public int getQuantity()
    {
        return this.quantity;
    }
    public CardType getType()
    {
        return this.type;
    }
}
