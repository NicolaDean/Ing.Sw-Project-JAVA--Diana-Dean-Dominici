package it.polimi.ingsw.model.dashboard;

import it.polimi.ingsw.enumeration.resourceType;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceList;

import static it.polimi.ingsw.enumeration.resourceType.*;

import java.util.ArrayList;
import java.util.List;

public class Storage {
    private Deposit[] storage = new Deposit[5];


    /**
     * constructor of Storage, it initializes the 3 storages with 1, 2, and 3 respectively as maxSize
     */
    public Storage() {
        storage[0] = new Deposit(1);
        storage[1] = new Deposit(2);
        storage[2] = new Deposit(3);

    }

    /**
     * method that swaps the content of two selected deposits
     * @param pos1 position of the first deposit
     * @param pos2 position of the second deposit
     * @throws Exception if the swap is not possibile because of space limits of the deposits
     */
    public void swapDeposit(int pos1, int pos2) throws Exception {
        if(pos1>2 || pos2>2)
            throw new Exception("you can't swap bonus deposits");
        else {
            if (storage[pos1].getResource().getQuantity() <= storage[pos2].getSizeMax() && storage[pos2].getResource().getQuantity() <= storage[pos1].getSizeMax()) {
                Resource tmp = storage[pos1].getResource();
                storage[pos1].setNewDeposit(storage[pos2].getResource().getType(), storage[pos2].getResource().getQuantity());
                storage[pos2].setNewDeposit(tmp.getType(), tmp.getQuantity());

            } else
                throw new Exception("the swap is not possible");
        }
    }

    /**
     * inserts new resources in a deposit
     * @param in the resource to add
     * @param pos the deposit position
     * @throws Exception if the insertion can't be done because of space
     */
    public void safeInsertion(Resource in, int pos) throws Exception
    {
        if(pos==4 && storage[4] == null)
            throw new Exception("you don't have a bonus deposit!");
        if(pos==5 && storage[5] == null)
            throw new Exception("you don't have a bonus deposit!");

        boolean tmp = false;
        if(storage[pos].getResource() == null && pos <3){
            for(int i=0; i<3; i++) {
                if (i != pos && storage[i].getResource() != null && in.getType() == storage[i].getResource().getType()) {
                    tmp = true;
                    break;
                }
            }
            if(!tmp)
                storage[pos].setNewDeposit(in.getType(), in.getQuantity());
            else {
                throw new Exception("there is already a slot with this type of resource");
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
    public List<Integer> findType(resourceType type) throws Exception
    {
        List<Integer> indexes = new ArrayList<Integer>();
        boolean a = false;
        for(int i = 0; i<5; i++)
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
    public void safeSubtraction(Resource in, int pos) throws Exception
    {
        storage[pos].safeSubtraction(in);
        if (storage[pos].getResource().getQuantity()==0 && pos<3)

            storage[pos].setNewDeposit(ROCK, 0);

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
        for (int i = 0; i < 5; i++) {
            if (storage[i] != null)
                resourcelist.add(storage[i].getResource());
        }
        return resourcelist;
    }

        /**
         * initialize a bonus deposit (leader)
         * @param type
         */
        public void initializeBonusDeposit (resourceType type)
        {
            int i;
            if (storage[3] == null)
                i = 3;
            else
                i = 4;

            storage[i] = new Deposit(2);
            storage[i].setNewDeposit(type, 0);

        }
    }

