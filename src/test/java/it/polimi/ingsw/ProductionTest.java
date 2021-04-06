package it.polimi.ingsw;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.cards.ProductionCard;
import it.polimi.ingsw.model.dashboard.Dashboard;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceList;
import it.polimi.ingsw.model.resources.ResourceOperator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static it.polimi.ingsw.enumeration.ResourceType.*;
import static org.junit.jupiter.api.Assertions.*;

public class ProductionTest {

    @Test
    public void CheckCost()
    {
        Player p = new Player();

        p.getDashboard().chestInsertion(new Resource(COIN,1));
        p.getDashboard().chestInsertion(new Resource(ROCK,1));

        List<Resource> tmp = new ResourceList();

        tmp.add(new Resource(COIN,1));
        tmp.add(new Resource(ROCK,1));
        tmp.add(new Resource(SHIELD,1));

        ProductionCard card = new ProductionCard(tmp,2,1);

        assertFalse(card.checkCost(p.getDashboard()));

        p.getDashboard().storageInsertion(new Resource(SHIELD,1),0);
        assertTrue(card.checkCost(p.getDashboard()));
    }

    @Test
    public void BuyTest()
    {
        Player p = new Player();


        p.getDashboard().chestInsertion(new Resource(COIN,2));
        p.getDashboard().chestInsertion(new Resource(ROCK,1));


        List<Resource> tmp = new ResourceList();

        tmp.add(new Resource(COIN,1));
        tmp.add(new Resource(ROCK,1));
        tmp.add(new Resource(SHIELD,1));

        ProductionCard card = new ProductionCard(tmp,2,1);

        assertFalse(card.buy(p,0));


        p.getDashboard().storageInsertion(new Resource(SHIELD,1),0);
        assertTrue(card.buy(p,0));
    }

    @Test
    public void ProductionTest()
    {
        Player p = new Player();

        p.getDashboard().chestInsertion(new Resource(COIN,1));
        p.getDashboard().chestInsertion(new Resource(ROCK,3));
        p.getDashboard().storageInsertion(new Resource(SHIELD,1),0);

        List<Resource> check = new ResourceList();
        check =  p.getDashboard().getAllAvailableResource();

        //Check RESOURCE INSERTION
        assertTrue(ResourceOperator.extractQuantityOf(ROCK,check) == 3);
        assertTrue(ResourceOperator.extractQuantityOf(COIN,check) == 1);
        assertTrue(ResourceOperator.extractQuantityOf(SHIELD,check) == 1);
        assertTrue(ResourceOperator.extractQuantityOf(SERVANT,check) == 0);

        //COST
        List<Resource> cost = new ResourceList();
        cost.add(new Resource(COIN,1));
        cost.add(new Resource(ROCK,1));
        cost.add(new Resource(SHIELD,1));

        //RAW MAT
        List<Resource> raw = new ResourceList();
        raw.add(new Resource(COIN,2));

        //OBTAINED
        List<Resource> obt = new ResourceList();
        obt.add(new Resource(ROCK,1));

        //BUY A CARD
        ProductionCard card = new ProductionCard(cost,raw,obt,2,1);
        assertTrue(card.buy( p,0));

        //Apllying costs
        p.getDashboard().applyChestCosts(new Resource(COIN,1));
        p.getDashboard().applyChestCosts(new Resource(ROCK,1));
        p.getDashboard().applyStorageCosts(new Resource(SHIELD,1),0);


        check  =  p.getDashboard().getAllAvailableResource();
        //Check Application of costs
        assertTrue(ResourceOperator.extractQuantityOf(ROCK,check) == 2);
        assertTrue(ResourceOperator.extractQuantityOf(COIN,check) == 0);
        assertTrue(ResourceOperator.extractQuantityOf(SHIELD,check) == 0);
        assertTrue(ResourceOperator.extractQuantityOf(SERVANT,check) == 0);

        //Sorage Refill

        p.getDashboard().storageInsertion(new Resource(COIN,2),1);

        //Production
        p.getDashboard().production(p,0);

        //Cost Application

        p.getDashboard().applyStorageCosts(new Resource(COIN,2),1);

        //Check Application of costs
        check  =  p.getDashboard().getAllAvailableResource();


        //Check Cost apply and resource adding
        assertTrue(ResourceOperator.extractQuantityOf(ROCK,check) == 3);
        assertTrue(ResourceOperator.extractQuantityOf(COIN,check) == 0);
        assertTrue(ResourceOperator.extractQuantityOf(SHIELD,check) == 0);
        assertTrue(ResourceOperator.extractQuantityOf(SERVANT,check) == 0);


    }

    @Test

    public void discountedCostTest()
    {

        //COST
        List<Resource> cost = new ResourceList();
        cost.add(new Resource(COIN,1));
        cost.add(new Resource(ROCK,1));
        cost.add(new Resource(SHIELD,1));

        //RAW MAT
        List<Resource> raw = new ResourceList();
        raw.add(new Resource(COIN,2));

        //OBTAINED
        List<Resource> obt = new ResourceList();
        obt.add(new Resource(ROCK,1));

        //BUY A CARD
        ProductionCard card = new ProductionCard(cost,raw,obt,2,1);

        Player p = new Player();

        p.addDiscount(new Resource(COIN,1));

        List<Resource> resultCost = card.getCost(p.getDashboard());

        assertTrue(ResourceOperator.extractQuantityOf(ROCK,resultCost) == 1);
        assertTrue(ResourceOperator.extractQuantityOf(COIN,resultCost) == 0);
        assertTrue(ResourceOperator.extractQuantityOf(SHIELD,resultCost) == 1);
        assertTrue(ResourceOperator.extractQuantityOf(SERVANT,resultCost) == 0);

    }
}
