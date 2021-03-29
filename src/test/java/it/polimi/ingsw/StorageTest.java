package it.polimi.ingsw;

import it.polimi.ingsw.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static it.polimi.ingsw.enumeration.ResourceType.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StorageTest {

    /**
     * tests the correct operation of the deposit swap
     *
     * @throws Exception if the swap cannot be made because of size issues
     */
    @Test
    public void TestCorrectDepositSwap() throws Exception {
        Storage testStorage = new Storage();
        Resource a = new Resource(SHIELD, 1);
        Resource b = new Resource(COIN, 1);
        Resource c = new Resource(ROCK, 2);
        testStorage.safeInsertion(a, 0);
        testStorage.safeInsertion(b, 1);
        testStorage.safeInsertion(c, 2);
        testStorage.swapDeposit(0, 1);
        testStorage.swapDeposit(1, 2);
        assertTrue(ResourceOperator.Compare(testStorage.getStorage()[0].getResource(), b) &&
                ResourceOperator.Compare(testStorage.getStorage()[1].getResource(), c) &&
                ResourceOperator.Compare(testStorage.getStorage()[2].getResource(), a)
        );


    }

    /**
     * checks if exceptions are thrown correctly
     */
    @Test
    public void TestInvalidSwap() {
        Assertions.assertThrows(Exception.class, () -> {
            Storage testStorage = new Storage();
            Resource a = new Resource(SHIELD, 1);
            Resource b = new Resource(COIN, 2);
            Resource c = new Resource(ROCK, 3);
            testStorage.safeInsertion(a, 0);
            testStorage.safeInsertion(b, 1);
            testStorage.safeInsertion(c, 2);
            testStorage.swapDeposit(0, 1);
            testStorage.swapDeposit(1, 2);
        });

    }

    @Test
    public void TestStorageAsList() throws Exception {
        Storage testStorage = new Storage();
        Resource a = new Resource(SHIELD, 1);
        Resource b = new Resource(COIN, 1);
        Resource c = new Resource(ROCK, 2);
        Resource d = new Resource(ROCK, 4);
        testStorage.safeInsertion(a, 0);
        testStorage.safeInsertion(b, 1);
        testStorage.safeInsertion(c, 2);
        List<Resource> list = testStorage.getStorageAsList();
        DepositBonus bonus = new DepositBonus(list, 3, ROCK);
        bonus.activate(testStorage);
        testStorage.safeInsertion(c, 3);
        list = testStorage.getStorageAsList();
        assertTrue(ResourceOperator.Compare(list.get(0), a) &&
                ResourceOperator.Compare(list.get(1), b) &&
                ResourceOperator.Compare(list.get(2), d));
    }

    /**
     * test the safeinsertion of a wrong type
     */
    @Test
    public void TestWrongTypeInsertion() {
        Assertions.assertThrows(Exception.class, () -> {
            Storage testStorage = new Storage();
            Resource a = new Resource(SHIELD, 1);
            Resource b = new Resource(ROCK, 1);
            Resource c = new Resource(ROCK, 2);
            List<Resource> list = testStorage.getStorageAsList();
            DepositBonus bonus = new DepositBonus(list, 3, ROCK);
            testStorage.safeInsertion(a, 0);
            testStorage.safeInsertion(b, 1);
            testStorage.safeInsertion(c, 2);

        });
    }

    /**
     * test the safeinserction in bonus deposit
     * @throws Exception
     */
    @Test
    public void TestBonusStorage() throws Exception {

        Storage testStorage = new Storage();
        Resource a = new Resource(SHIELD, 1);
        Resource b = new Resource(ROCK, 1);
        Resource c = new Resource(COIN, 2);
        testStorage.safeInsertion(a, 0);
        testStorage.safeInsertion(b, 1);
        testStorage.safeInsertion(c, 2);
        List<Resource> list = testStorage.getStorageAsList();
        DepositBonus bonus = new DepositBonus(list, 3, ROCK);
        DepositBonus bonus2 = new DepositBonus(list, 3, SHIELD);
        bonus.activate(testStorage);
        testStorage.safeInsertion(b, 3);
        bonus2.activate(testStorage);
        testStorage.safeInsertion(a, 4);
        assertTrue(ResourceOperator.Compare(testStorage.getStorage()[3].getResource(), b) &&
                ResourceOperator.Compare(testStorage.getStorage()[4].getResource(), a));

    }

    /**
     * test the safesub in the bonus deposits
     * @throws Exception
     */
    @Test
    public void TestBonusStorageSub() throws Exception {

        Storage testStorage = new Storage();
        Resource a = new Resource(SHIELD, 1);
        Resource b = new Resource(ROCK, 1);
        Resource c = new Resource(COIN, 2);
        testStorage.safeInsertion(b, 1);
        testStorage.safeSubtraction(b, 1);
        testStorage.safeInsertion(a, 1);
        testStorage.safeInsertion(c, 2);
        List<Resource> list = testStorage.getStorageAsList();
        DepositBonus bonus = new DepositBonus(list, 3, ROCK);
        DepositBonus bonus2 = new DepositBonus(list, 3, SHIELD);
        bonus.activate(testStorage);
        testStorage.safeInsertion(b, 3);
        bonus2.activate(testStorage);
        testStorage.safeInsertion(a, 4);
        testStorage.safeSubtraction(a, 4);
        testStorage.safeInsertion(b, 4);
        testStorage.safeSubtraction(b, 3);
        testStorage.safeSubtraction(a, 1);
        testStorage.safeInsertion(b, 1);
        testStorage.safeInsertion(a, 3);
    }

    /**
     * test the swap between bonus deposit
     */
    @Test
    public void TestBonusSwap() {
        Assertions.assertThrows(Exception.class, () -> {
            Storage testStorage = new Storage();
            Resource a = new Resource(SHIELD, 1);
            Resource b = new Resource(ROCK, 1);
            Resource c = new Resource(COIN, 2);
            testStorage.safeInsertion(a, 0);
            testStorage.safeInsertion(b, 1);
            testStorage.safeInsertion(c, 2);
            List<Resource> list = testStorage.getStorageAsList();
            DepositBonus bonus = new DepositBonus(list, 3, ROCK);
            DepositBonus bonus2 = new DepositBonus(list, 3, SHIELD);
            bonus.activate(testStorage);
            testStorage.safeInsertion(b, 3);
            bonus2.activate(testStorage);
            testStorage.safeInsertion(a, 4);
            testStorage.swapDeposit(3, 1);
        });

    }
}



