package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.ResourceType;

public class Deposit {
    final int SizeMax; //the capacity of the deposit (it can be 1, 2 or 3)
    private Resource Resource; //the resource type that the deposit can contain (it can change during the game so it is not final)


    public Deposit(int sizeMax) {
        SizeMax = sizeMax;
    }

    public int getSizeMax() {
        return SizeMax;
    }

    public it.polimi.ingsw.model.Resource getResource() {
        return Resource;
    }

    /**
     * this method is used to insert resources into an already initialized deposit
     * @param in
     * @throws Exception if the insertion exceeds SizeMax
     */
    public void safeInsertion(Resource in) throws Exception
    {
        if(Resource==null)
            setNewDeposit(in.getType(), in.getQuantity());
        else {
            Resource result = Resource;
            try {

                result = ResourceOperator.sum(in, Resource);
                if (result.getQuantity() <= SizeMax)
                    Resource = result;


            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            if (result.getQuantity() > SizeMax)
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

        if(Resource==null)
            throw new Exception("The deposit is empty");


        int a = Resource.getQuantity();
            try {
                Resource result = ResourceOperator.sub(Resource, in);
                if (Resource.getQuantity() >= in.getQuantity())
                {   a = -1;
                    if(result.getQuantity()==0)
                        Resource = null;
                    else
                        Resource = result;
                }


            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            if(a == Resource.getQuantity())
                throw new Exception("not enough resources, operation failed");

    }

    /**
     * method used to insert a resource for the first time or if a deposit swap occurs
     * @param Rtype
     * @param Rquantity
     */
    public void setNewDeposit(ResourceType Rtype, int Rquantity )
    {
        Resource = new Resource(Rtype, Rquantity);
    }

}
