package it.polimi.ingsw;

import it.polimi.ingsw.model.Deposit;
import it.polimi.ingsw.model.Storage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.ResourceOperator;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.enumeration.ResourceType.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StorageTest {

    /**
     * tests the correct operation of the deposit swap
     * @throws Exception if the swap cannot be made because of size issues
     */
    @Test
    public void TestCorrectDepositSwap() throws Exception
    {
        Storage testStorage = new Storage();
        Resource a = new Resource(SHILD, 1);
        Resource b = new Resource(COIN, 1);
        Resource c = new Resource(ROCK, 2);
        testStorage.safeInsertion(a, 0);
        testStorage.safeInsertion(b, 1);
        testStorage.safeInsertion(c, 2);
        testStorage.swapDeposit(0, 1);
        testStorage.swapDeposit(1, 2);
        assertTrue(ResourceOperator.Compare(testStorage.getStorage()[0].getResource(), b)&&
                        ResourceOperator.Compare(testStorage.getStorage()[1].getResource(), c)&&
                        ResourceOperator.Compare(testStorage.getStorage()[2].getResource(), a)
                );


    }

    /**
     * checks if exceptions are thrown correctly
     */
    @Test
    public void TestInvalidSwap() {
        Assertions.assertThrows(Exception.class,()->{
            Storage testStorage = new Storage();
            Resource a = new Resource(SHILD, 1);
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
    public void TestResourcesAsList() throws Exception {
        Storage testStorage = new Storage();
        Resource a = new Resource(SHILD, 1);
        Resource b = new Resource(COIN, 1);
        Resource c = new Resource(ROCK, 2);
        testStorage.safeInsertion(a, 0);
        testStorage.safeInsertion(b, 1);
        testStorage.safeInsertion(c, 2);

        List<Resource> list=testStorage.getStorageAsList();
        assertTrue(ResourceOperator.Compare(list.get(0), a)&&
                ResourceOperator.Compare(list.get(1), b)&&
                ResourceOperator.Compare(list.get(2), c));
        }

    @Test
    public void TestWrongTypeInsertion() {
        Assertions.assertThrows(Exception.class,()->{
            Storage testStorage = new Storage();
            Resource a = new Resource(SHILD, 1);
            Resource b = new Resource(ROCK, 1);
            Resource c = new Resource(ROCK, 2);
            testStorage.safeInsertion(a, 0);
            testStorage.safeInsertion(b, 1);
            testStorage.safeInsertion(c, 2);


        });
    }
    }



