package it.polimi.ingsw.modelTest;

import it.polimi.ingsw.exceptions.FullDepositException;
import it.polimi.ingsw.exceptions.NoBonusDepositOwned;
import it.polimi.ingsw.exceptions.WrongPosition;
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


    /**
     * check if dashboard card insertion work correctrly
     * try to do diffferent combination of insertions (levels, positions..)
     * @throws Exception custom exceptions used inside model
     */
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
            dash.setProductionCard(tmp,0);
        });
        Assertions.assertThrows(Exception.class,()-> {
            dash.setProductionCard(tmp,0);
        });

        //Lv 2 on null
        Assertions.assertThrows(Exception.class,()-> {
            dash.setProductionCard(tmp2,1);
        });
        //Lv 3 on null
        Assertions.assertThrows(Exception.class,()-> {
            dash.setProductionCard(tmp3,1);
        });

        //Lv 2 impiled on 1
        Assertions.assertDoesNotThrow(()-> {
            dash.setProductionCard(tmp2,0);
        });


        //Lv3 on lv 1
        Assertions.assertDoesNotThrow(()-> {
            dash.setProductionCard(tmp,2);
        });
        Assertions.assertThrows(Exception.class,()-> {
            dash.setProductionCard(tmp3,2);
        });

        //Lv3 on lv 2
        Assertions.assertDoesNotThrow(()-> {
            dash.setProductionCard(tmp3,0);
        });

        //Lv3 on lv 3
        Assertions.assertThrows(Exception.class,()-> {
            dash.setProductionCard(tmp3,0);
        });
        //Lv2 on lv 3
        Assertions.assertThrows(Exception.class,()-> {
            dash.setProductionCard(tmp2,0);
        });


        //Lv 1 on lv 2/3
        Assertions.assertThrows(Exception.class,()-> {
            dash.setProductionCard(tmp,0);
        });


    }

    /**
     * Check if real time score calculus is right
     * @throws Exception model exceptions
     */
    @Test
    public void TestScoreCalculation() throws Exception {
        List<Resource> res = new ArrayList<Resource>();
        Dashboard dash = new Dashboard();

        res.add(new Resource(COIN,2));

        ProductionCard tmp = new ProductionCard(res,3,1);
        ProductionCard tmp2 = new ProductionCard(res,2,2);
        ProductionCard tmp3 = new ProductionCard(res,2,3);

        dash.setProductionCard(tmp,0);
        dash.setProductionCard(tmp2,0);
        dash.setProductionCard(tmp3,0);
        dash.setProductionCard(tmp,1);

        assertTrue(dash.getCardScores() == 10);

    }

    /**
     * check if "all available resources" function work well and retrive all res from chest and storage
     * @throws FullDepositException  Deposit full (cant insert any more res)
     * @throws NoBonusDepositOwned   (wrond deposit index)
     * @throws WrongPosition         (wrong depoisti index)
     */
    @Test
    void TestGetAllResources() throws FullDepositException, NoBonusDepositOwned, WrongPosition {
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

    /**
     * check if dashboard production execution work well(eg pricing, activation,..)
     * @throws Exception model exception
     */
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

    /**
     * check if pending cost of buy or production work well
     * @throws Exception model exception
     */
    @Test
    void TestPendingCost() throws Exception {
        List<Resource> res = new ArrayList<Resource>();
        Dashboard dash = new Dashboard();

        res.add(new Resource(COIN,2));

        ProductionCard tmp = new ProductionCard(res,3,1);

        dash.setProductionCard(tmp,1);

        assertTrue(ResourceOperator.extractQuantityOf(COIN,dash.getPendingCost())==2);
        assertTrue(ResourceOperator.extractQuantityOf(ROCK,dash.getPendingCost())==0);
        assertTrue(ResourceOperator.extractQuantityOf(SERVANT,dash.getPendingCost())==0);
        assertTrue(ResourceOperator.extractQuantityOf(SHIELD,dash.getPendingCost())==0);

    }
}
