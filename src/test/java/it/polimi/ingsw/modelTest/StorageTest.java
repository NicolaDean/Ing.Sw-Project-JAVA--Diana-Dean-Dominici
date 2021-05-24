package it.polimi.ingsw.modelTest;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.cards.PrerequisiteCard;
import it.polimi.ingsw.model.cards.leaders.DepositBonus;
import it.polimi.ingsw.model.dashboard.Storage;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceOperator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
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
        testStorage = new Storage();
        testStorage.safeInsertion(b, 1);
        testStorage.safeInsertion(c, 2);
        testStorage.swapDeposit(0, 1);
        testStorage.swapDeposit(1, 2);

        assertTrue(ResourceOperator.Compare(testStorage.getStorage()[0].getResource(), b) &&
                ResourceOperator.Compare(testStorage.getStorage()[1].getResource(), c) &&
                testStorage.getStorage()[2].getResource()==null);

    }

    @Test
    public void TestCorrectBonusDepositSwap() throws Exception {
        Player player = new Player();
        Resource a = new Resource(SHIELD, 1);
        Resource b = new Resource(COIN, 1);
        Resource c = new Resource(ROCK, 2);
        Resource d = new Resource(ROCK, 1);
        player.getDashboard().getStorage().safeInsertion(a, 0);
        player.getDashboard().getStorage().safeInsertion(b, 1);
        player.getDashboard().getStorage().safeInsertion(c, 2);
        Storage testStorage = player.getDashboard().getStorage();

        List<Resource> list = player.getDashboard().getStorage().getStorageAsList();
        DepositBonus bonus = new DepositBonus(list,new ArrayList<PrerequisiteCard>(), 3, ROCK);
        bonus.activate(player);
        //System.out.println(player.getDashboard().getStorage().getStorage()[3].getResource().getType());
        player.getDashboard().getStorage().safeInsertion(d, 3);
        testStorage.swapDeposit(2,3);

        assertTrue(ResourceOperator.Compare(testStorage.getStorage()[2].getResource(), d) &&
                ResourceOperator.Compare(testStorage.getStorage()[3].getResource(), c) );


    }

    /**
     * tesrt the function to find which deposits contain a certain resourcetype
     * @throws Exception
     */
    @Test
    public void TestTypeFind() throws Exception {

        Player player = new Player();
        Resource a = new Resource(SHIELD, 1);
        Resource b = new Resource(COIN, 1);
        Resource c = new Resource(ROCK, 2);
        Resource d = new Resource(ROCK, 4);
        player.getDashboard().getStorage().safeInsertion(a, 0);
        player.getDashboard().getStorage().safeInsertion(b, 1);
        player.getDashboard().getStorage().safeInsertion(c, 2);
        List<Resource> list = player.getDashboard().getStorage().getStorageAsList();
        DepositBonus bonus = new DepositBonus(list,new ArrayList<PrerequisiteCard>(), 3, ROCK);
        bonus.activate(player);
        player.getDashboard().getStorage().safeInsertion(c, 3);
        List<Integer> positions = player.getDashboard().getStorage().findType(ROCK);
        List<Integer> positions2 = player.getDashboard().getStorage().findType(COIN);

        assertTrue(positions.get(0)== 2 && positions.get(1)== 3 && positions2.get(0)== 1);

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
        Player player = new Player();
        Resource a = new Resource(SHIELD, 1);
        Resource b = new Resource(COIN, 1);
        Resource c = new Resource(ROCK, 2);
        Resource d = new Resource(ROCK, 4);
        player.getDashboard().getStorage().safeInsertion(a, 0);
        player.getDashboard().getStorage().safeInsertion(b, 1);
        player.getDashboard().getStorage().safeInsertion(c, 2);
        List<Resource> list = player.getDashboard().getStorage().getStorageAsList();
        DepositBonus bonus = new DepositBonus(list,new ArrayList<PrerequisiteCard>(), 3, ROCK);
        bonus.activate(player);
        player.getDashboard().getStorage().safeInsertion(c, 3);
        list = player.getDashboard().getStorage().getStorageAsList();

        assertTrue(ResourceOperator.extractQuantityOf(a.getType(),list) == a.getQuantity());
        assertTrue(ResourceOperator.extractQuantityOf(b.getType(),list) == b.getQuantity());
        assertTrue(ResourceOperator.extractQuantityOf(c.getType(),list) == 2*c.getQuantity());


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
            DepositBonus bonus = new DepositBonus(list,new ArrayList<PrerequisiteCard>(), 3, ROCK);
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

        Player player = new Player();
        Resource a = new Resource(SHIELD, 1);
        Resource b = new Resource(ROCK, 1);
        Resource c = new Resource(COIN, 2);
        player.getDashboard().getStorage().safeInsertion(a, 0);
        player.getDashboard().getStorage().safeInsertion(b, 1);
        player.getDashboard().getStorage().safeInsertion(c, 2);
        List<Resource> list = player.getDashboard().getStorage().getStorageAsList();
        DepositBonus bonus = new DepositBonus(list, new ArrayList<PrerequisiteCard>(),3, ROCK);
        DepositBonus bonus2 = new DepositBonus(list,new ArrayList<PrerequisiteCard>(), 3, SHIELD);
        bonus.activate(player);
        player.getDashboard().getStorage().safeInsertion(b, 3);
        bonus2.activate(player);
        player.getDashboard().getStorage().safeInsertion(a, 4);
        assertTrue(ResourceOperator.Compare(player.getDashboard().getStorage().getStorage()[3].getResource(), b) &&
                ResourceOperator.Compare(player.getDashboard().getStorage().getStorage()[4].getResource(), a));

    }

    /**
     * test the safesub in the bonus deposits
     * @throws Exception
     */
    @Test
    public void TestBonusStorageSub() throws FullDepositException, NoBonusDepositOwned, WrongPosition, EmptyDeposit, NotSoddisfedPrerequisite, LeaderActivated {

        Player player = new Player();
        Resource a = new Resource(SHIELD, 1);
        Resource b = new Resource(ROCK, 1);
        Resource c = new Resource(COIN, 2);
        player.getDashboard().getStorage().safeInsertion(b, 1);
        player.getDashboard().getStorage().safeSubtraction(b, 1);
        player.getDashboard().getStorage().safeInsertion(a, 1);
        player.getDashboard().getStorage().safeInsertion(c, 2);
        List<Resource> list = player.getDashboard().getStorage().getStorageAsList();
        DepositBonus bonus = new DepositBonus(list,new ArrayList<PrerequisiteCard>(), 3, ROCK);
        DepositBonus bonus2 = new DepositBonus(list,new ArrayList<PrerequisiteCard>(), 3, SHIELD);
        bonus.activate(player);
        player.getDashboard().getStorage().safeInsertion(b, 3);
        bonus2.activate(player);
        player.getDashboard().getStorage().safeInsertion(a, 4);
        player.getDashboard().getStorage().safeSubtraction(a, 4);
        //player.getDashboard().getStorage().safeInsertion(b, 4);
        player.getDashboard().getStorage().safeSubtraction(b, 3);
        player.getDashboard().getStorage().safeSubtraction(a, 1);
        player.getDashboard().getStorage().safeInsertion(b, 1);
        //player.getDashboard().getStorage().safeInsertion(a, 3);
    }

    /**
     * test the swap between bonus deposit
     */


}



