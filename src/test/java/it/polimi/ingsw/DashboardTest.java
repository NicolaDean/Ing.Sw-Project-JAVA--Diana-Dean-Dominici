package it.polimi.ingsw;

import it.polimi.ingsw.enumeration.ResourceType;
import it.polimi.ingsw.model.Dashboard;
import it.polimi.ingsw.model.ProductionCard;
import it.polimi.ingsw.model.Resource;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.enumeration.ResourceType.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class DashboardTest {


    @Test
    public void TestCardsInsertion()
    {
        List<Resource> res = new ArrayList<Resource>();
        Dashboard dash = new Dashboard();

        res.add(new Resource(COIN,2));

        ProductionCard tmp = new ProductionCard(res,3,1);
        ProductionCard tmp2 = new ProductionCard(res,2,2);
        ProductionCard tmp3 = new ProductionCard(res,2,3);

        //Lv 1 impiled on 1
        assertTrue(dash.setProcuctionCard(tmp,0));
        assertFalse(dash.setProcuctionCard(tmp,0));

        //Lv 2 impiled on 1
        assertTrue(dash.setProcuctionCard(tmp2,0));

        //Lv 2 on emply space
        assertFalse(dash.setProcuctionCard(tmp2,1));

        //Lv3 on lv 1
        assertTrue(dash.setProcuctionCard(tmp,2));
        assertFalse(dash.setProcuctionCard(tmp3,2));

        //Lv3 on lv 2
        assertTrue(dash.setProcuctionCard(tmp3,0));

        //Lv 1 on lv 2/3
        assertFalse(dash.setProcuctionCard(tmp,0));
    }

    @Test
    public void TestScoreCalculation()
    {
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
        dash.storageInsertion(new Resource(SERVANT,2),0);

        List<Resource> tmp = dash.getAllAvailableResource();

        for(Resource res:tmp)
        {
            if(res.getType() == COIN) assertTrue(res.getQuantity() == 3);
            if(res.getType() == SERVANT) assertTrue(res.getQuantity() == 2);
            if(res.getType() == ROCK) assertTrue(res.getQuantity() == 2);
        }
    }

    @Test void TestProduction()
    {
        Dashboard dash = new Dashboard();

        dash.storageInsertion(new Resource(COIN,1),0);
        dash.storageInsertion(new Resource(SERVANT,2),2);


        //production without raw material
        assertFalse(dash.basicProduction(COIN,SHILD,ROCK));

        //production with raw material
        assertTrue(dash.basicProduction(COIN,SERVANT,ROCK));
    }
}
