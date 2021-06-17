package it.polimi.ingsw.model.resources;

import it.polimi.ingsw.enumeration.*;
import it.polimi.ingsw.utils.ConstantValues;
import it.polimi.ingsw.utils.CurrentOS;
import it.polimi.ingsw.view.utils.CliColors;

import java.io.Serializable;

public class Resource implements Serializable {
    private              ResourceType  type;
    private              int           quantity;

    public Resource(ResourceType type, int quantity)
    {
        this.type = type;
        this.quantity = quantity;
    }


    /**
     *
     * get Method for quantity
     */
    public int getQuantity()
    {
        return this.quantity;
    }

    /**
     *
     * get Method for type
     */
    public ResourceType getType()
    {
        return this.type;
    }


    public int getNumericType()
    {
        switch (this.type) {
            case SHIELD:
                return 1;

            case ROCK:
                return 2;

            case COIN:
                return 3;

            case SERVANT:
                return 4;

            default:
                return 1;


        }


    }

    /**
     * set Quantity of the resource
     * @param quantity
     */
    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    public String getCliRappresentation(boolean quantity)
    {
        if(quantity)
        {
            if(!CurrentOS.IsWindows())
            {
                return ConstantValues.resourceRappresentation.getColorRappresentation(this.getType()) + CliColors.BLACK_TEXT + CliColors.BOLD +
                        " " + this.getQuantity() + " ";
            }
            else
            {
                return ConstantValues.resourceRappresentation.getNonColorRappresentation(this.getType()) + this.getQuantity();
            }
        }
        else
        {
            if(!CurrentOS.IsWindows())
            {
                return ConstantValues.resourceRappresentation.getColorRappresentation(this.getType()) + "    ";
            }
            else
            {
                return ConstantValues.resourceRappresentation.getNonColorRappresentation(this.getType()) +"-";
            }
        }

    }
}
