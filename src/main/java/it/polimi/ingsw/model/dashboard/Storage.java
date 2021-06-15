package it.polimi.ingsw.model.dashboard;

import it.polimi.ingsw.enumeration.ResourceType;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceList;
import it.polimi.ingsw.utils.ConstantValues;

import static it.polimi.ingsw.enumeration.ResourceType.*;

import java.util.ArrayList;
import java.util.List;

public class Storage {
    private Deposit[] storage = new Deposit[ConstantValues.maxDepositsNumber];


    /**
     * constructor of Storage, it initializes the 3 storages with 1, 2, and 3 respectively as maxSize
     */
    public Storage() {

        int size = 1;
        for(int i=0;i<ConstantValues.normalDepositNumber;i++)
        {
            //Create a "linear" piramid with ConstantValues.normalDepositNumber height
            storage[i] = new Deposit(size);
            size++;
        }

    }

    /**
     * method that swaps the content of two selected deposits
     * @param pos1 position of the first deposit
     * @param pos2 position of the second deposit
     * @throws Exception if the swap is not possibile because of space limits of the deposits
     */
    public void swapDeposit(int pos1, int pos2) throws IllegalSwap {
        /*if((pos1>2 || pos2>2) &&
                ((storage[pos1].getResource() != null || storage[pos2].getResource() != null) ||
                (storage[pos1].getResource().getType() != storage[pos2].getResource().getType() ||
                storage[pos1].getResource().getQuantity()>storage[pos2].getSizeMax() ||
                        storage[pos2].getResource().getQuantity()>storage[pos1].getSizeMax())))*/

        System.out.println("pos1 subito  è "+pos1);
        System.out.println("pos2 subito è "+pos2);

        if (pos1>5 || pos2 > 5)
            throw new IllegalSwap("");

        if(pos1>2 || pos2>2) {
            this.swapbonusDeposit(pos1, pos2);
            return;
        }

        if (storage[pos1].getResource() == null && storage[pos2].getResource() == null)
            throw new IllegalSwap("");

        if (storage[pos1].getResource() == null)
        {
            if (storage[pos2].getResource().getQuantity() <= storage[pos1].getSizeMax()) {
                Resource tmp = storage[pos2].getResource();
                storage[pos1].setNewDeposit(storage[pos2].getResource().getType(), storage[pos2].getResource().getQuantity());
                storage[pos2].setNewDeposit(SHIELD, -1);

            } else
                throw new IllegalSwap("");
        }

        else
        {
            if (storage[pos2].getResource() == null)
            {
                if (storage[pos1].getResource().getQuantity() <= storage[pos2].getSizeMax()) {
                    Resource tmp = storage[pos1].getResource();
                    storage[pos1].setNewDeposit(SHIELD, -1);
                    storage[pos2].setNewDeposit(tmp.getType(), tmp.getQuantity());

                } else
                    throw new IllegalSwap("");
            }
            else
            {
                //System.out.println("pos1 è "+pos1);
                //System.out.println("pos2 è "+pos2);
                if (storage[pos1].getResource().getQuantity() <= storage[pos2].getSizeMax() && storage[pos2].getResource().getQuantity() <= storage[pos1].getSizeMax()) {
                    Resource tmp = storage[pos1].getResource();
                    storage[pos1].setNewDeposit(storage[pos2].getResource().getType(), storage[pos2].getResource().getQuantity());
                    storage[pos2].setNewDeposit(tmp.getType(), tmp.getQuantity());

                } else
                    throw new IllegalSwap("");
            }
        }

    }

    public void swapbonusDeposit(int pos1, int pos2) throws IllegalSwap
    {
        int tmp;

        if(pos1>pos2)
        {
            tmp = pos1;
            pos1 = pos2;
            pos2 = tmp;
        }

        //caso in cui entrambi sono bonus
        if(pos1 >= ConstantValues.normalDepositNumber)
        {

            if(storage[pos1].getResource().getType() == storage[pos2].getResource().getType()) {
                tmp = storage[pos1].getResource().getQuantity();
                storage[pos1].getResource().setQuantity(storage[pos2].getResource().getQuantity());
                storage[pos1].getResource().setQuantity(tmp);
            }
            else
            {
                throw new IllegalSwap("");
            }
        }


        //caso in cui uno è normale ed è vuoto
        if (storage[pos1].getResource() == null)
        {
            if (storage[pos2].getResource().getQuantity() <= storage[pos1].getSizeMax()) {
                if(storage[pos2].getResource().getQuantity() == 0)
                {
                    storage[pos1].setNewDeposit(SHIELD,-1);
                }
                else {
                    Resource tmp2 = storage[pos1].getResource();
                    if(storage[pos2].getResource().getQuantity() >0)
                        storage[pos1].setNewDeposit(storage[pos2].getResource().getType(), storage[pos2].getResource().getQuantity());
                    storage[pos2].getResource().setQuantity(0);
                }

            } else
                throw new IllegalSwap("");
        }
        //caso in cui uno è normale e non è vuoto
        else {

            if (storage[pos2].getResource().getQuantity() <= storage[pos1].getSizeMax() && storage[pos2].getResource().getQuantity() <= storage[pos1].getSizeMax()) {
                if(storage[pos2].getResource().getType() == storage[pos1].getResource().getType())
                {

                    Resource tmp2 = storage[pos1].getResource();

                    storage[pos1].setNewDeposit(storage[pos2].getResource().getType(), storage[pos2].getResource().getQuantity());

                    storage[pos2].setNewDeposit(tmp2.getType(), tmp2.getQuantity());
                }else
                    throw new IllegalSwap("");

            } else
                throw new IllegalSwap("");
        }

    }


    /*
    method to move resources between deposits (at least 1 needs to be bonus)
     */
    public void moveResource(int pos1, int pos2, int q) throws IllegalResourceMove, FullDepositException, EmptyDeposit, NoBonusDepositOwned, WrongPosition {
        //System.out.println("\nla risorsa !!!!! ora ha quantità: "+storage[pos1].getResource().getQuantity()+"\n");

        //System.out.println("\n\n q vale: "+q+"\n\n");
        if(pos1 <3 && pos2 <3)
            throw new IllegalResourceMove("this operations is for bonus deposits only. use the swapdeposit function instead.");
        if(this.getStorage()[pos1]== null || this.getStorage()[pos2]== null)
            throw new IllegalResourceMove("you don't have the selected bonus deposit.");
        Resource resource;
        if(pos1 <3 && pos2 >=3)
        {
            //da pos1 normale a pos2 bonus
            System.out.println("\nda pos1 normale a pos2 bonus\n");
            if(storage[pos1].getResource()==null)
                throw new IllegalResourceMove("The starting deposit is empty.");
            if(storage[pos1].getResource().getQuantity()<q)
                throw new IllegalResourceMove("You don't have enough resources in the first deposit.");
            if(storage[pos1].getResource().getType() != storage[pos2].getResource().getType())
                throw new IllegalResourceMove("You can't insert this type of resource in this bonus deposit.");
            resource = new Resource(storage[pos1].getResource().getType(), q);

                //System.out.println("\nla risorsa ora ha quantità: "+storage[pos1].getResource().getQuantity()+"\n");
            try {
                safeSubtraction(resource,pos1);
                safeInsertion(resource,pos2);
            }
             catch (FullDepositException e) {
                safeInsertion(resource,pos1);
                throw e;
            }

            //System.out.println("\nla risorsa ora ha quantità: "+storage[pos1].getResource().getQuantity()+"\n");



            return;
        }
        if(pos2 <3 && pos1 >=3)
        {
            //da pos1 bonus a pos2 normale
            System.out.println("\nda pos1 bonus a pos2 normale\n");
            if(storage[pos1].getResource().getQuantity()==0)
                throw new IllegalResourceMove("The starting deposit is empty.");
            if(storage[pos1].getResource().getQuantity()<q)
                throw new IllegalResourceMove("You don't have enough resources in the first deposit.");

            if(storage[pos2].getResource()!=null)
            {
                if(storage[pos2].sizeMax-storage[pos2].getResource().getQuantity() < q)
                    throw new IllegalResourceMove("the second deposit dosn't have enough space!");
                if(storage[pos2].getResource().getType()!=storage[pos1].getResource().getType())
                    throw new IllegalResourceMove("You can't insert this type of resource in this deposit.");
            }
            else
            {
                if(storage[pos2].sizeMax < q)
                    throw new IllegalResourceMove("the second deposit dosn't have enough space!");
            }
            resource = new Resource(storage[pos1].getResource().getType(), q);
            try {
                safeSubtraction(resource,pos1);
                safeInsertion(resource,pos2);
            }
            catch (FullDepositException e) {
                safeInsertion(resource,pos1);
                throw e;
            }

            return;
        }
        if(pos2 >=3 && pos1 >=3)
        {
                throw new IllegalResourceMove("The two bonus deposits are of different resource types!");
        }

    }




    /**
     * inserts new resources in a deposit
     * @param in the resource to add
     * @param pos the deposit position
     * @throws Exception if the insertion can't be done because of space
     */
    public void safeInsertion(Resource in, int pos) throws NoBonusDepositOwned, WrongPosition, FullDepositException {
        if(pos==3 && storage[3] == null)
            throw new NoBonusDepositOwned();
        if(pos==4 && storage[4] == null)
            throw new NoBonusDepositOwned();

        boolean tmp = false;
        if(storage[pos].getResource() == null && pos <3){
            for(int i=0; i<ConstantValues.normalDepositNumber; i++) {
                if (i != pos && storage[i].getResource() != null && in.getType() == storage[i].getResource().getType()) {
                    tmp = true;
                    break;
                }
            }
            if(!tmp)
                storage[pos].safeInsertion(in);
            else {
                throw new WrongPosition("there is already a slot with this type of resource");
            }
        }
        else
            storage[pos].safeInsertion(in);
    }

    /**
     * it looks for deposits that contain a certain type
     * @param type
     * @return
     * @throws Exception
     */
    public List<Integer> findType(ResourceType type) throws Exception
    {
        List<Integer> indexes = new ArrayList<Integer>();
        boolean a = false;
        for(int i = 0; i<ConstantValues.maxDepositsNumber; i++)
        {
            if (storage[i] != null && storage[i].getResource().getType() == type) {
                indexes.add(i);
                a = true;
            }
        }

        if (!a)
            throw new Exception("there are no deposits with this resourcetype");
    return indexes;
    }

    /**
     * returns the free spaces of a deposit
     * @param pos
     * @return
     */
    public int getFreeSpace(int pos)
    {
        if (storage[pos] != null)
            return (storage[pos].getSizeMax() - storage[pos].getResource().getQuantity());
        else
            return storage[pos].getSizeMax();

    }


    /**
     * removes resources from a deposit
     * @param in the resource to add
     * @param pos the deposit position
     * @throws Exception if the subraction can't be done because of space
     */
    public void safeSubtraction(Resource in, int pos) throws EmptyDeposit, WrongPosition {
        storage[pos].safeSubtraction(in);
        System.out.println("\n "+storage[pos].getResource().getQuantity()+"\n");
        if (storage[pos].getResource().getQuantity()==0 && pos<ConstantValues.normalDepositNumber)

            storage[pos].setNewDeposit(ROCK, -1);

    }

    /**
     * sets the bonus deposit to true
     */


    public Deposit[] getStorage() {
        return storage;
    }

    /**
     * return the storage as a list of resources
     * @return
     */
    public ResourceList getStorageAsList() {
        ResourceList resourcelist = new ResourceList();
        for (int i = 0; i < ConstantValues.maxDepositsNumber; i++) {
            if (storage[i] != null)
                resourcelist.add(storage[i].getResource());
        }
        return resourcelist;
    }

        /**
         * initialize a bonus deposit (leader)
         * @param type
         */
        public int initializeBonusDeposit (ResourceType type)
        {
            int i;
            if (storage[3] == null)
                i = 3;
            else
                i = 4;

            storage[i] = new Deposit(ConstantValues.bonusDepositSize);
            storage[i].setNewBonusDeposit(type, 0);

            //Indicate the activation order
            if(i==3) return 0;
            else return 1;

        }

    public Deposit[] getDeposits()
    {
        return this.storage;
    }

}


