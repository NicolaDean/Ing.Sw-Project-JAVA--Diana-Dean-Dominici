package it.polimi.ingsw;

import it.polimi.ingsw.enumeration.ResourceType;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.ProductionCard;
import it.polimi.ingsw.model.cards.leaders.LeaderTradeCard;
import it.polimi.ingsw.model.dashboard.Dashboard;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceList;
import it.polimi.ingsw.model.resources.ResourceOperator;


import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.enumeration.ResourceType.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class LeadersTest {
    @Test
    public void discountBonusTest()
    {
        Dashboard dash = new Dashboard();

        dash.setDiscount(new Resource(COIN,1));
        dash.setDiscount(new Resource(ROCK,1));
        dash.setDiscount(new Resource(SERVANT,1));

        List<Resource> cost = new ResourceList();

        cost.add(new Resource(COIN,1));
        cost.add(new Resource(ROCK,2));

        ProductionCard card = new ProductionCard(cost,cost,cost,1,1);

        List<Resource> result = card.getCost(dash);//get cost with the discount if exist

        assertEquals(ResourceOperator.extractQuantityOf(COIN,result),0);
        assertEquals(ResourceOperator.extractQuantityOf(ROCK,result),1);
        assertEquals(ResourceOperator.extractQuantityOf(SERVANT,result),0);
        assertEquals(ResourceOperator.extractQuantityOf(SHIELD,result),0);

    }

    @Test
    public void tradeBonusTest()
    {
        List<Resource> cost = new ResourceList();

        cost.add(new Resource(COIN,1));

        LeaderCard l[] = {new LeaderTradeCard(cost,new ArrayList<>(),1,COIN), new LeaderCard(cost,new ArrayList<>(),1, ResourceType.COIN) };
        Player p=new Player("nick",l);

        p.chestInsertion(new Resource(COIN,1));
        //LEADER ACTIVATION
        assertTrue(p.activateLeader(0));

        //TRADE BONUS TEST
        assertTrue(p.bonusProduction(0,ROCK));

        //PAY ACTIVATION
        p.payChestResource(new Resource(COIN,1));

        //CHECK POS
        assertEquals(p.getPosition(),1);

        //CHECK ADDED/REMOVED RESOURCE
        assertEquals(ResourceOperator.extractQuantityOf(COIN,p.getDashboard().getAllAvailableResource()),0);
        assertEquals(ResourceOperator.extractQuantityOf(SERVANT,p.getDashboard().getAllAvailableResource()),0);
        assertEquals(ResourceOperator.extractQuantityOf(SHIELD,p.getDashboard().getAllAvailableResource()),0);
        assertEquals(ResourceOperator.extractQuantityOf(ROCK,p.getDashboard().getAllAvailableResource()),1);



    }


}
