package it.polimi.ingsw.model;
import it.polimi.ingsw.enumeration.ResourceType;

import java.util.List;

public class ResourceOperator {
    /**
     * Compare the type and quantity of two resources
     * @param a
     * @param b
     * @return
     */
    public static boolean Compare(Resource a, Resource b)
    {
        return (a.getType() == b.getType()) && (a.getQuantity() == b.getQuantity());
    }

    /**
     * Sum 2 resource of the same type, if the type is different throw exeption
     * @param a
     * @param b
     * @return
     * @throws Exception
     */
    public static Resource sum(Resource a,Resource b) throws Exception
    {
        if(Compare(a,b))
        {
            return new Resource(a.getType(), a.getQuantity()+b.getQuantity());
        }
        else
        {
             throw new Exception("Resource Type is different");
        }
    }

    /**
     * Sub 2 resource of the same type, if the type is different throw exeption
     * @param a
     * @param b
     * @return
     * @throws Exception
     */
    public static Resource sub(Resource a,Resource b) throws Exception
    {
        if(Compare(a,b))
        {
            return new Resource(a.getType(), a.getQuantity()-b.getQuantity());
        }
        else
        {
            throw new Exception("Resource Type is different");
        }
    }

    /**
     *
     */


}
