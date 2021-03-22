package it.polimi.ingsw;

import it.polimi.ingsw.enumeration.ResourceType;
import it.polimi.ingsw.model.Dashboard;
import it.polimi.ingsw.model.ProductionCard;
import it.polimi.ingsw.model.Resource;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.enumeration.ResourceType.COIN;
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

    @Test void TestProduction()
    {

    }
}
