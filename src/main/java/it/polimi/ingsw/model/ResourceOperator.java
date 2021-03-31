package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.ResourceType;

import java.util.ArrayList;
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
        if(a.getType() == b.getType())
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
        if(a.getType() == b.getType())
        {
            return new Resource(a.getType(), a.getQuantity()-b.getQuantity());
        }
        else
        {
            throw new Exception("Resource Type is different");
        }
    }

    /**
     * Return true if all Resources of a is >= of b
     * @param maxThen
     * @param minThen
     * @return
     *
     */
    public static boolean compare(List<Resource> maxThen,List<Resource> minThen)
    {
        boolean flag;//avoid to return true if 2 list have only different reource type

        for(Resource resA : maxThen)
        {
            flag = false;
            for(Resource resB : minThen)
            {
                if(resA.getType() == resB.getType())
                {
                    if( resA.getQuantity() >= resB.getQuantity()) flag = true;
                    else return false;
                }
            }
            if(flag == false) return false;
        }
        return true;
    }

    /**
     * Merge two Resource list Grouping quantity by Resource type
     * @param a list 1
     * @param b list 2
     * @return a new list with all element of a and b grouped by resourceType
     */
    public static List<Resource> merge(List<Resource>a , List<Resource> b)
    {
        List<Resource> tmp = new ResourceList();

        if(a == null) return b;
        if(b == null) return a;

        for(Resource res :a)
        {
            tmp.add(res);
        }
        for(Resource res:b)
        {
            tmp.add(res);
        }


        return tmp;
    }

    public static int extractQuantityOf(ResourceType type,List<Resource> list)
    {
        for(Resource res:list)
        {
            if(res.getType() == type) return res.getQuantity();
        }
        return 0;
    }


}
