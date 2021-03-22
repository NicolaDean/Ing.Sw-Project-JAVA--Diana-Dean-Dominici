package it.polimi.ingsw.model;

public class Storage {
    private Deposit[] Storage = new Deposit[3];
    private boolean BonusActive;

    /**
     * constructor of Storage, it initializes the 3 storages with 1, 2, and 3 respectively as maxSize
     */
    public Storage() {
        Storage[0] = new Deposit(1);
        Storage[1] = new Deposit(2);
        Storage[2] = new Deposit(3);
        BonusActive = false;
    }

    /**
     * method that swaps the content of two selected deposits
     * @param pos1 position of the first deposit
     * @param pos2 position of the second deposit
     * @throws Exception if the swap is not possibile because of space limits of the deposits
     */
    public void swapDeposit(int pos1, int pos2) throws Exception {
        if (Storage[pos1].getResource().getQuantity() <= Storage[pos2].getSizeMax() && Storage[pos2].getResource().getQuantity() <= Storage[pos1].getSizeMax())
        {
            Resource tmp = Storage[pos1].getResource();
            Storage[pos1].setNewDeposit(Storage[pos2].getResource().getType(), Storage[pos2].getResource().getQuantity());
            Storage[pos2].setNewDeposit(tmp.getType(), tmp.getQuantity());

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
        Storage[pos].safeInsertion(in);
    }

    /**
     * removes resources from a deposit
     * @param in the resource to add
     * @param pos the deposit position
     * @throws Exception if the subraction can't be done because of space
     */
    public void safeSubtraction(Resource in, int pos) throws Exception
    {
        Storage[pos].safeSubtraction(in);
    }

    /**
     * sets the bonus deposit to true
     */
    public void setBonusActive() {
        BonusActive = true;
    }

    public Deposit[] getStorage() {
        return Storage;
    }

    public boolean isBonusActive() {
        return BonusActive;
    }
}
