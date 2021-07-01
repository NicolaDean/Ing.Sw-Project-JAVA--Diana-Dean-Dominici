package it.polimi.ingsw.model.resources;

import it.polimi.ingsw.enumeration.ResourceType;
import it.polimi.ingsw.exceptions.IncompatibleTypesComparison;

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
    public static Resource sum(Resource a,Resource b) throws IncompatibleTypesComparison
    {
        if(a.getType() == b.getType())
        {
            return new Resource(a.getType(), a.getQuantity()+b.getQuantity());
        }
        else
        {
             throw new IncompatibleTypesComparison();
        }
    }

    /**
     * Sub 2 resource of the same type, if the type is different throw exeption
     * @param a
     * @param b
     * @return
     * @throws Exception
     */
    public static Resource sub(Resource a,Resource b) throws IncompatibleTypesComparison
    {
        if(a.getType() == b.getType())
        {
            return new Resource(a.getType(), a.getQuantity()-b.getQuantity());
        }
        else
        {
            throw new IncompatibleTypesComparison();
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

    /**
     *
     * @param type type to count
     * @param list list to check
     * @return quantity of a specific resource type
     */
    public static int extractQuantityOf(ResourceType type, List<Resource> list)
    {
        for(Resource res:list)
        {
            if(res.getType() == type) return res.getQuantity();
        }
        return 0;
    }


    /**
     * list A - list B (UNSAFE!!, dosnt check if A hase more resource then B) if that happen quantity is setted to 0 to avoid infinite loop
     * used only in cli resource insertion/extraction to check if he completed the payment
     * @param a op1
     * @param b op2
     * @return a list with subtracted qwuantity
     */
    public static List<Resource> listSubtraction(List<Resource> a,List<Resource>b)
    {
        List<Resource> out = new ResourceList();

        if(b == null) return a;

        for(Resource res1 : a)
        {
            int qty = extractQuantityOf(res1.getType(),b);
                qty = res1.getQuantity() - qty;

                //if(qty<0) qty = 0;//Resolve eventual "negative" quantity resources bug

            out.add(new Resource(res1.getType(),qty));
        }
        return out;
    }

    /**
     * check if a list is empty
     * @param resources  list to check
     * @return true if empty
     */
    public static boolean isEmpty(List<Resource> resources)
    {
        for(Resource res1 : resources) {
            if(res1.getQuantity()>0)return false; //Resolve eventual "negative" quantity resources bug
        }
        return true;

    }

    /**
     *
     * @param resources  list to elaborate
     * @param res   resource to remove
     * @return new list with res removed
     */
    public static List<Resource> remove(List<Resource> resources,Resource res)
    {
        int pos=0;

        if (res == null) return resources;
        if(resources.isEmpty())
        {
            return resources;
        }
        else
        {
            for(Resource r :resources)
            {
                if(r!= null)
                {
                    if(res.getType().equals(res.getType()))
                    {
                        try {
                            if(r.getQuantity() >= res.getQuantity())
                            {
                                resources.set(pos,ResourceOperator.sub(r,res));
                                return resources;
                            }
                            else{
                                return resources;
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                            return resources;
                        }
                    }
                }

                pos++;

            }
            return resources;
        }
    }
    /**
     * return the number of non empty resource type inside this resource list
      * @param resources list to check
     * @return number of not null resource type
     */
    public static int getTypeCounter(List<Resource> resources)
    {
        int out =0;
        for(Resource res1 : resources) {
            if(res1.getQuantity()!=0)out ++;
        }
        return out;
    }

}
