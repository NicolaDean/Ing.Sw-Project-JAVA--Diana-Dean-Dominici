package it.polimi.ingsw.model;

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
     * @param a
     * @param b
     * @return
     */
    public static boolean compare(List<Resource> a,List<Resource> b)
    {
        boolean flag;//avoid to return true if 2 list have only different reource type

        for(Resource resA : a)
        {
            flag = false;
            for(Resource resB : b)
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
     *
     * @param  list a list
     * @param res a resource
     * @return a new list with the new Resource inserted as quantiy in existing resource of that type
     */
    public static List<Resource> compactedInsertion(List<Resource> list, Resource res)
    {
        int i=0;
        for(Resource alreadyIn: list)
        {
            if(alreadyIn.getType() == res.getType())
            {
                try{
                    list.set(i,ResourceOperator.sum(alreadyIn,res));
                    return list;
                }catch (Exception e)
                {
                    System.out.println(e.getMessage());
                    return null;
                }

            }
            i++;
        }
        list.add(res);
        return list;
    }
    /**
     * Merge two Resource list Grouping quantity by Resource type
     * @param a list 1
     * @param b list 2
     * @return a new list with all element of a and b grouped by resourceType
     */
    public static List<Resource> merge(List<Resource>a , List<Resource> b)
    {
        List<Resource>tmp = new ArrayList<Resource>();

        if (a == null) return b;
        if (b == null) return a;

        for(Resource res: a)
        {
            tmp.add(res);
        }

        boolean flag = false;
        //if this type already exist sum the quantity
        for(Resource res:b)
        {
            tmp = compactedInsertion(tmp,res);
        }
        return tmp;
    }


}
