package it.polimi.ingsw;
import it.polimi.ingsw.model.Dashboard;
import it.polimi.ingsw.model.ProductionCard;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.ResourceList;
import org.junit.jupiter.api.Test;

import java.util.List;

import static it.polimi.ingsw.enumeration.ResourceType.*;
import static org.junit.jupiter.api.Assertions.*;

public class ProductionTest {

    @Test
    public void CheckCost()
    {
        Dashboard dash = new Dashboard();

        dash.chestInsertion(new Resource(COIN,1));
        dash.chestInsertion(new Resource(ROCK,1));

        List<Resource> tmp = new ResourceList();

        tmp.add(new Resource(COIN,1));
        tmp.add(new Resource(ROCK,1));
        tmp.add(new Resource(SHIELD,1));

        ProductionCard card = new ProductionCard(tmp,2,1);

        assertFalse(card.checkCost(dash));

        dash.storageInsertion(new Resource(SHIELD,1),0);
        assertTrue(card.checkCost(dash));
    }

    @Test
    public void BuyTest()
    {
        Dashboard dash = new Dashboard();

        dash.chestInsertion(new Resource(COIN,1));
        dash.chestInsertion(new Resource(ROCK,1));


        List<Resource> tmp = new ResourceList();

        tmp.add(new Resource(COIN,1));
        tmp.add(new Resource(ROCK,1));
        tmp.add(new Resource(SHIELD,1));

        ProductionCard card = new ProductionCard(tmp,2,1);

        assertFalse(card.buy(dash,0));


        dash.storageInsertion(new Resource(SHIELD,1),0);
        assertTrue(card.buy(dash,0));
    }

    @Test
    public void ActivateTest()
    {
        Dashboard dash = new Dashboard();

        dash.chestInsertion(new Resource(COIN,1));
        dash.chestInsertion(new Resource(ROCK,3));
        dash.storageInsertion(new Resource(SHIELD,1),0);

        //COST
        List<Resource> cost = new ResourceList();
        cost.add(new Resource(COIN,1));
        cost.add(new Resource(ROCK,1));
        cost.add(new Resource(SHIELD,1));

        //RAW MAT
        List<Resource> raw = new ResourceList();
        cost.add(new Resource(COIN,2));

        //OBTAINED
        List<Resource> obt = new ResourceList();
        cost.add(new Resource(ROCK,1));

        //BUY A CARD
        ProductionCard card = new ProductionCard(cost,raw,obt,2,1);
        assertTrue(card.buy(dash,0));

        //Apllying costs
        dash.applyChestCosts(new Resource(COIN,1));
        dash.applyChestCosts(new Resource(ROCK,1));
        dash.applyStorageCosts(new Resource(SHIELD,1),0);

        //Check Application of costs
        cost  = dash.getAllAvailableResource();
        for(Resource res: cost)
        {
            if(res.getType() == ROCK) assertTrue(res.getQuantity() == 2);
            if(res.getType() == COIN) assertTrue(res.getQuantity() == 0);
            if(res.getType() == SHIELD) assertTrue(res.getQuantity() == 0);
        }

        //Sorage Refill

        dash.storageInsertion(new Resource(COIN,2),1);

        //Production
        dash.production(0);

        //Cost Application

        dash.applyStorageCosts(new Resource(COIN,2),1);

        //Check Application of costs
        cost  = dash.getAllAvailableResource();

        for(Resource res: cost)
        {
            if(res.getType() == ROCK) assertTrue(res.getQuantity() == 1);
            if(res.getType() == COIN) assertTrue(res.getQuantity() == 0);
            if(res.getType() == SHIELD) assertTrue(res.getQuantity() == 0);
        }


    }
}
