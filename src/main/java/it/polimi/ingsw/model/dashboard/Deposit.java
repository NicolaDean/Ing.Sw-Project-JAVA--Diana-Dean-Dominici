package it.polimi.ingsw.model.dashboard;

import it.polimi.ingsw.enumeration.resourceType;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceOperator;

public class Deposit {
    final int sizeMax; //the capacity of the deposit (it can be 1, 2 or 3)
    private Resource resource; //the resource type that the deposit can contain (it can change during the game so it is not final)


    public Deposit(int sizeMax) {
        this.sizeMax = sizeMax;
    }

    public int getSizeMax() {
        return sizeMax;
    }

    public Resource getResource() {
        return resource;
    }

    /**
     * this method is used to insert resources into an already initialized deposit
     * @param in
     * @throws Exception if the insertion exceeds SizeMax
     */
    public void safeInsertion(Resource in) throws Exception
    {
        if(resource ==null)
            setNewDeposit(in.getType(), in.getQuantity());
        else {
            Resource result = resource;
            try {

                result = ResourceOperator.sum(in, resource);
                if (result.getQuantity() <= sizeMax)
                    resource = result;


            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            if (result.getQuantity() > sizeMax)
                throw new Exception("the capacity has been exceeded, operation failed");
        }

    }

    /**
     * this method is used to subtract resources from an already initialized deposit
     * @param in
     * @throws Exception is the amount of resources is not sufficient to apply the subtraction
     */
    public void safeSubtraction(Resource in) throws Exception
    {

        if(resource == null)
            throw new Exception("The deposit is empty");


        int a = resource.getQuantity();
            try {
                Resource result;
                result = ResourceOperator.sub(resource, in);
                if (resource.getQuantity() >= in.getQuantity())
                {   a = -1;
                        resource = result;
                }


            } catch (Exception e) {
                a = -1;
                System.out.println(e.getMessage());
            }

            if(resource != null && a == resource.getQuantity())
                throw new Exception("not enough resources, operation failed");

    }

    /**
     * method used to insert a resource for the first time or if a deposit swap occurs
     * @param Rtype
     * @param Rquantity
     */
    public void setNewDeposit(resourceType Rtype, int Rquantity )
    {
        resource = new Resource(Rtype, Rquantity);
        if (Rquantity == 0) {
            resource = null;
        }
    }

}
