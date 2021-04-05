package it.polimi.ingsw.model.resources;

import it.polimi.ingsw.enumeration.*;

public class Resource {
    private resourceType type;
    private int quantity;

    public Resource(resourceType type, int quantity)
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
    public resourceType getType()
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
