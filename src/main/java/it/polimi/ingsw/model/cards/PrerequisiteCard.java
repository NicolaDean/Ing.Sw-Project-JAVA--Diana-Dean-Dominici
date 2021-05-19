package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.enumeration.CardType;
import it.polimi.ingsw.utils.ConstantValues;
import it.polimi.ingsw.utils.CurrentOS;

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

    /**
     * If user are windows return a textual rappresentation else a colored write rappresenting this prerequisite
     * @return
     */
    public String getCliRappresentation()
    {
        if(!CurrentOS.IsWindows())
        {
            return ConstantValues.resourceRappresentation.getCardTypeColorRappresentation(type) +
                    " L:" + this.level + " Q:"+this.quantity;
        }
        else
        {
            return " L:" + this.level + " Q:"+this.quantity + " T:" + "";//T:tipo
        }
    }
}
