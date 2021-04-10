package it.polimi.ingsw;

import it.polimi.ingsw.model.dashboard.Dashboard;
import it.polimi.ingsw.model.cards.ProductionCard;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceOperator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.enumeration.ResourceType.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class DashboardTest {


    @Test
    public void TestCardsInsertion() throws Exception {
        List<Resource> res = new ArrayList<Resource>();
        Dashboard dash = new Dashboard();

        res.add(new Resource(COIN,2));

        ProductionCard tmp = new ProductionCard(res,3,1);
        ProductionCard tmp2 = new ProductionCard(res,2,2);
        ProductionCard tmp3 = new ProductionCard(res,2,3);

        //Lv 1 impiled on 1
        Assertions.assertDoesNotThrow(()-> {
            dash.setProcuctionCard(tmp,0);
        });
        Assertions.assertThrows(Exception.class,()-> {
            dash.setProcuctionCard(tmp,0);
        });

        //Lv 2 impiled on 1
        Assertions.assertDoesNotThrow(()-> {
            dash.setProcuctionCard(tmp2,0);
        });

        //Lv 2 on emply space
        Assertions.assertThrows(Exception.class,()-> {
            dash.setProcuctionCard(tmp2,1);
        });

        //Lv3 on lv 1
        Assertions.assertDoesNotThrow(()-> {
            dash.setProcuctionCard(tmp,2);
        });
        Assertions.assertThrows(Exception.class,()-> {
            dash.setProcuctionCard(tmp3,2);
        });

        //Lv3 on lv 2
        Assertions.assertDoesNotThrow(()-> {
            dash.setProcuctionCard(tmp3,0);
        });

        //Lv 1 on lv 2/3
        Assertions.assertThrows(Exception.class,()-> {
            dash.setProcuctionCard(tmp,0);
        });
    }

    @Test
    public void TestScoreCalculation() throws Exception {
        List<Resource> res = new ArrayList<Resource>();
        Dashboard dash = new Dashboard();

        res.add(new Resource(COIN,2));

        ProductionCard tmp = new ProductionCard(res,3,1);
        ProductionCard tmp2 = new ProductionCard(res,2,2);
        ProductionCard tmp3 = new ProductionCard(res,2,3);

        dash.setProcuctionCard(tmp,0);
        dash.setProcuctionCard(tmp2,0);
        dash.setProcuctionCard(tmp3,0);
        dash.setProcuctionCard(tmp,1);

        assertTrue(dash.getCardScores() == 10);

    }

    @Test
    void TestGetAllResources()
    {
        Dashboard dash = new Dashboard();

        dash.chestInsertion(new Resource(COIN,1));
        dash.chestInsertion(new Resource(ROCK,2));
        dash.chestInsertion(new Resource(COIN,1));

        dash.storageInsertion(new Resource(COIN,1),0);
        dash.storageInsertion(new Resource(SERVANT,2),1);


        List<Resource> tmp = dash.getAllAvailableResource();

        assertTrue(ResourceOperator.extractQuantityOf(COIN,tmp) == 3);
        assertTrue(ResourceOperator.extractQuantityOf(SERVANT,tmp) == 2);
        assertTrue(ResourceOperator.extractQuantityOf(ROCK,tmp) == 2);
    }

    @Test void TestProduction() throws Exception {
        Dashboard dash = new Dashboard();

        dash.storageInsertion(new Resource(COIN,1),0);
        dash.storageInsertion(new Resource(SERVANT,2),2);


        List<Resource> tmp = dash.getAllAvailableResource();

        //production without raw material
        Assertions.assertThrows(Exception.class,()-> {
            dash.basicProduction(COIN, SHIELD,ROCK);
        });

        assertDoesNotThrow(()-> {
            dash.basicProduction(COIN, SERVANT,ROCK);
        });
    }

    @Test
    void TestPendingCost() throws Exception {
        List<Resource> res = new ArrayList<Resource>();
        Dashboard dash = new Dashboard();

        res.add(new Resource(COIN,2));

        ProductionCard tmp = new ProductionCard(res,3,1);

        dash.setProcuctionCard(tmp,1);

        assertTrue(ResourceOperator.extractQuantityOf(COIN,dash.getPendingCost())==2);
        assertTrue(ResourceOperator.extractQuantityOf(ROCK,dash.getPendingCost())==0);
        assertTrue(ResourceOperator.extractQuantityOf(SERVANT,dash.getPendingCost())==0);
        assertTrue(ResourceOperator.extractQuantityOf(SHIELD,dash.getPendingCost())==0);

    }
}
