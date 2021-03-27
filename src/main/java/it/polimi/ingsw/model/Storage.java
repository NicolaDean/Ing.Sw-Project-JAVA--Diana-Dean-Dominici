package it.polimi.ingsw.model;

public class Storage {
    private Deposit[] storage = new Deposit[3];
    private boolean bonusActive;

    /**
     * constructor of Storage, it initializes the 3 storages with 1, 2, and 3 respectively as maxSize
     */
    public Storage() {
        storage[0] = new Deposit(1);
        storage[1] = new Deposit(2);
        storage[2] = new Deposit(3);
        bonusActive = false;
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
    public void setBonusActive() {
        bonusActive = true;
    }

    public Deposit[] getStorage() {
        return storage;
    }

    public boolean isBonusActive() {
        return bonusActive;
    }
}
