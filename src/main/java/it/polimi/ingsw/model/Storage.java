package it.polimi.ingsw.model;

import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private Deposit[] storage = new Deposit[3];


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
        if (storage[pos1].getResource().getQuantity() <= storage[pos2].getSizeMax() && storage[pos2].getResource().getQuantity() <= storage[pos1].getSizeMax())
        {
            Resource tmp = storage[pos1].getResource();
            storage[pos1].setNewDeposit(storage[pos2].getResource().getType(), storage[pos2].getResource().getQuantity());
            storage[pos2].setNewDeposit(tmp.getType(), tmp.getQuantity());

        }
        else
            throw new Exception("the swap is not possible");
    }

    /**
     * inserts new resources in a deposit
     * @param in the resource to add
     * @param pos the deposit position
     * @throws Exception if the insertion can't be done because of space
     */
    public void safeInsertion(Resource in, int pos) throws Exception
    {
        boolean tmp = false;
        if(storage[pos].getResource() == null){
            for(int i=0; i<3; i++) {
                if (i!= pos && storage[i].getResource()!= null) {
                    if (in.getType() == storage[i].getResource().getType()) {
                        tmp = true;
                    }

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
     * removes resources from a deposit
     * @param in the resource to add
     * @param pos the deposit position
     * @throws Exception if the subraction can't be done because of space
     */
    public void safeSubtraction(Resource in, int pos) throws Exception
    {
        storage[pos].safeSubtraction(in);
    }

    /**
     * sets the bonus deposit to true
     */


    public Deposit[] getStorage() {
        return storage;
    }

    public List<Resource> getStorageAsList() {

        List resourcelist = new ArrayList();
        resourcelist.add(storage[0].getResource());
        resourcelist.add(storage[1].getResource());
        resourcelist.add(storage[2].getResource());
        return resourcelist;

    }


}
