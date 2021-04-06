package it.polimi.ingsw;

import it.polimi.ingsw.model.dashboard.Deposit;
import it.polimi.ingsw.model.resources.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static it.polimi.ingsw.enumeration.ResourceType.*;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class DepositTest {

    /** test the safeinsertion
     *
     *
     * @throws Exception
     */
    @Test
    public void TestDepositInsertion() throws Exception
    {
        Deposit testdeposit = new Deposit(3);
        testdeposit.setNewDeposit(COIN, 2);
        Resource r = new Resource(COIN, 1);
        testdeposit.safeInsertion(r);
        assertTrue(testdeposit.getResource().getQuantity()==3 && testdeposit.getResource().getType()==COIN);

        testdeposit = new Deposit(3);
        testdeposit.safeInsertion(r);
        assertTrue(testdeposit.getResource().getQuantity()==1 && testdeposit.getResource().getType()==COIN);


    }

    /**
     * test the insert of an invalid resource
     * @throws Exception
     */
    @Test
    public void TestInvalidDepositInsertion() throws Exception
    {
        Deposit testdeposit = new Deposit(3);
        testdeposit.setNewDeposit(COIN, 2);
        Resource r = new Resource(SHIELD, 1);
        testdeposit.safeInsertion(r);
        assertTrue(testdeposit.getResource().getQuantity()==2 && testdeposit.getResource().getType()==COIN);

    }

    /**
     * test the safeSubtraction
     * @throws Exception
     */
   @Test
    public void TestDepositSubtraction() throws Exception
    {
        Deposit testdeposit = new Deposit(3);
        testdeposit.setNewDeposit(COIN, 3);
        Resource r = new Resource(COIN, 2);
        testdeposit.safeSubtraction(r);
        assertTrue(testdeposit.getResource().getQuantity()==1 && testdeposit.getResource().getType()==COIN);

    }

    /**
     * test the sub of an invalid resource
     * @throws Exception
     */
    @Test
    public void TestInvalidDepositSub() throws Exception
    {
        Deposit testdeposit = new Deposit(3);
        testdeposit.setNewDeposit(COIN, 2);
        Resource r = new Resource(SHIELD, 1);
        testdeposit.safeSubtraction(r);
        assertTrue(testdeposit.getResource().getQuantity()==2 && testdeposit.getResource().getType()==COIN);

    }


    /**
     * Test the exceptions of safeInsertion
     */
    @Test
    public void TestDepositExceptionInsertion() {
        Assertions.assertThrows(Exception.class,()->{
            Deposit testdeposit = new Deposit(2);
            testdeposit.setNewDeposit(COIN, 2);
            Resource r = new Resource(COIN, 1);
            testdeposit.safeInsertion(r);
        });

    }

    /**
     * Test the exceptions of SafeSubtraction
     */
    @Test
    public void TestDepositExceptionSub() {
        Assertions.assertThrows(Exception.class,()->{
            Deposit testdeposit = new Deposit(2);
            testdeposit.setNewDeposit(COIN, 2);
            Resource r = new Resource(COIN, 3);
            testdeposit.safeSubtraction(r);

        });
    }
}