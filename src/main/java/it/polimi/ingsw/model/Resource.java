package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.*;

public class Resource {
    private ResourceType Type;
    private int Quantity;

    public Resource(ResourceType type,int quantity)
    {
        this.Type = type;
        this.Quantity = quantity;
    }
    /**
     *
     * get Method for quantity
     */
    public int getQuantity()
    {
        return this.Quantity;
    }

    /**
     *
     * get Method for type
     */
    public ResourceType getType()
    {
        return this.Type;
    }

    /**
     * set Quantity of the resource
     * @param quantity
     */
    public void setQuantity(int quantity)
    {
        this.Quantity = quantity;
    }
}
