package it.polimi.ingsw.model.resources;

import it.polimi.ingsw.enumeration.*;

public class Resource {
    private ResourceType type;
    private int quantity;

    public Resource(ResourceType type,int quantity)
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

    /**
     * set Quantity of the resource
     * @param quantity
     */
    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }
}
