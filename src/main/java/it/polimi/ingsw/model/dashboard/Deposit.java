package it.polimi.ingsw.model.dashboard;

import it.polimi.ingsw.enumeration.ResourceType;
import it.polimi.ingsw.exceptions.*;
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
    public void safeInsertion(Resource in) throws FullDepositException,WrongPosition
    {
        //System.out.println(resource);
        if(resource == null) {
            //System.out.println(in.getQuantity());
            if(in.getQuantity() > sizeMax) {
                throw new FullDepositException();
            }
            else
                setNewDeposit(in.getType(), in.getQuantity());
        }
        else {
            Resource result = resource;
            try {


                result = ResourceOperator.sum(in, resource);

                if (result.getQuantity() <= sizeMax)
                    resource = result;


            } catch (IncompatibleTypesComparison e) {
                throw new WrongPosition("");
            }
            if (result.getQuantity() > sizeMax) {

                throw new FullDepositException();
            }
        }


    }

    /**
     * this method is used to subtract resources from an already initialized deposit
     * @param in
     * @throws Exception is the amount of resources is not sufficient to apply the subtraction
     */
    public void safeSubtraction(Resource in) throws WrongPosition,EmptyDeposit
    {

        if(resource == null)
            throw new EmptyDeposit("");


        int a = resource.getQuantity();
            try {
                Resource result;
                result = ResourceOperator.sub(resource, in);
                if (resource.getQuantity() >= in.getQuantity())
                {
                        a = -1;
                        resource = result;
                }


            } catch (IncompatibleTypesComparison e) {
                a = -1;
                throw new WrongPosition("");
            }

            if(resource != null && a == resource.getQuantity())
                throw new EmptyDeposit(" , extraction operation failed");

    }

    /**
     * method used to insert a resource for the first time or if a deposit swap occurs
     * @param Rtype
     * @param Rquantity
     */
    public void setNewDeposit(ResourceType Rtype, int Rquantity )
    {
        resource = new Resource(Rtype, Rquantity);
        if (Rquantity == -1) {
            resource = null;
        }
    }
    public void setNewBonusDeposit(ResourceType Rtype, int Rquantity )
    {
        resource = new Resource(Rtype, Rquantity);

    }

    @Override
    public Deposit clone() {
        Deposit out = new Deposit(this.sizeMax);
        try {
            out.safeInsertion(this.getResource());
        }catch (Exception e){
            e.printStackTrace();
        }
        return out;
    }
}
