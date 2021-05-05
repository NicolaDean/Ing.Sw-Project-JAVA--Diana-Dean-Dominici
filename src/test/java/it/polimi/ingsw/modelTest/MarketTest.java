package it.polimi.ingsw.modelTest;

import it.polimi.ingsw.enumeration.ResourceType;
import it.polimi.ingsw.exceptions.WrongPosition;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.PrerequisiteCard;
import it.polimi.ingsw.model.cards.leaders.LeaderDiscountCard;
import it.polimi.ingsw.model.cards.leaders.LeaderWhiteCard;
import it.polimi.ingsw.model.market.balls.BasicBall;
import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceList;
import static it.polimi.ingsw.enumeration.ResourceType.*;

import it.polimi.ingsw.model.resources.ResourceOperator;
import org.junit.jupiter.api.*;
import java.awt.*;
import java.util.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MarketTest {

    @Disabled
    public void writeMarket(Market m){
        System.out.println("Market: ");
        for (int i = 0; i < 3; i++) {
            System.out.println("");
            for(int j = 0; j < 4; j++){
                try {
                    System.out.print(m.getResouces()[i][j].getType()+"      ");
                }catch (Exception e){
                    System.out.print("RED/WHITE      ");
                }
            }
        }
        System.out.println("\ndiscarded ball: "+m.getDiscardedResouce());
    }

    @Disabled
    public void checkMarketConsistency(Market m){
        int b=0,y=0,w=0,r=0,g=0,v=0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if(m.getResouces()[i][j].getColor()== Color.blue){
                    b++;
                }
                if(m.getResouces()[i][j].getColor()== Color.yellow){
                    y++;
                }
                if(m.getResouces()[i][j].getColor()== Color.white){
                    w++;
                }
                if(m.getResouces()[i][j].getColor()== Color.red){
                    r++;
                }
                if(m.getResouces()[i][j].getColor()== Color.gray){
                    g++;
                }
                if(m.getResouces()[i][j].getColor()== Color.magenta){
                    v++;
                }
            }
        }
        if(m.getDiscardedResouce().getColor()== Color.blue){
            b++;
        }
        if(m.getDiscardedResouce().getColor()== Color.yellow){
            y++;
        }
        if(m.getDiscardedResouce().getColor()== Color.white){
            w++;
        }
        if(m.getDiscardedResouce().getColor()== Color.red){
            r++;
        }
        if(m.getDiscardedResouce().getColor()== Color.gray){
            g++;
        }
        if(m.getDiscardedResouce().getColor()== Color.magenta){
            v++;
        }
        assertTrue(b==2);
        assertTrue(y==2);
        assertTrue(w==4);
        assertTrue(r==1);
        assertTrue(g==2);
        assertTrue(v==2);
    }

    /**
     * check building market
     */
    @Test
    public void checkMarketShuffle(){
        checkMarketConsistency(new Market());
    }

    /**
     * check exstraction row
     */
    @Test
    public void checkExstractionRow(){

        ArrayList<Resource> r = new ArrayList<>();
        LeaderCard l[] = {new LeaderCard(r,new ArrayList<>(),1, COIN), new LeaderCard(r,new ArrayList<>(),1, COIN) };
        Player p=new Player("nick");
        r.add(new Resource(SHIELD,3));
        r.add(new Resource(COIN,10));
        Market m=new Market();
        BasicBall n[],dis;
        for(int i=0;i<3;i++) {

                n = m.getResouces()[i].clone();
                dis = m.getDiscardedResouce();
                try {
                    m.exstractRow(i + 1, p);
                }catch (Exception e){ fail(); }
                assertEquals(dis, m.getResouces()[i][0]);
                assertEquals(n[0], m.getResouces()[i][1]);
                assertEquals(n[1], m.getResouces()[i][2]);
                assertEquals(n[2], m.getResouces()[i][3]);
                assertEquals(n[3], m.getDiscardedResouce());

        }
        checkMarketConsistency(m);
    }

    /**
     * check exstraction column
     */
    @Test
    public void checkExstractionColumn(){

        ArrayList<Resource> r = new ArrayList<>();
        LeaderCard l[] = {new LeaderCard(r,new ArrayList<>(),1, COIN), new LeaderCard(r,new ArrayList<>(),1, COIN) };
        Player p=new Player("nick");
        r.add(new Resource(SHIELD,3));
        r.add(new Resource(COIN,10));

        Market m=new Market();
        Color n[] = new Color[3],dis;

        List f=new ResourceList();
        for(int i=0;i<4;i++) {
            try {
                n[0]=m.getResouces()[0][i].getColor();
                n[1]=m.getResouces()[1][i].getColor();
                n[2]=m.getResouces()[2][i].getColor();
                dis=m.getDiscardedResouce().getColor();
                m.exstractColumn(i+1, p);
                f=m.getPendingResourceExtracted();
                assertEquals(dis, m.getResouces()[0][i].getColor());
                assertEquals(n[0], m.getResouces()[1][i].getColor());
                assertEquals(n[1], m.getResouces()[2][i].getColor());
                assertEquals(n[2], m.getDiscardedResouce().getColor());

            }catch (Exception e){
                if((i>3)||(i<0))
                    fail();
            }

        }
        checkMarketConsistency(m);
    }

    /**
     * check activation ball (no white ball)
     */
    @Test
    public void activityTestNoWhite() throws WrongPosition {
        ResourceList r = new ResourceList();
        LeaderCard l[] = {new LeaderCard(r,new ArrayList<>(),1, COIN), new LeaderCard(r,new ArrayList<>(),1, SERVANT) };
        Player p=new Player("nick");
        List b;
        int tmpShield=0,tmpCoin=0,tmpRock=0,tmpSeverant=0,tmpfaith=0;
        Market m=new Market();

        tmpfaith=p.getPosition();
        int colonna=0;

            for (int i = 0; i < 3; i++) {
                try {
                    tmpCoin += (m.getResouces()[i][colonna].getType() == COIN) ? 1 : 0;
                }catch (Exception e){ }
                try {
                    tmpRock += (m.getResouces()[i][colonna].getType() == ROCK) ? 1 : 0;
                }catch (Exception e){ }
                try {
                    tmpShield += (m.getResouces()[i][colonna].getType() == SHIELD) ? 1 : 0;
                }catch (Exception e){ }
                try {
                    tmpSeverant += (m.getResouces()[i][colonna].getType() == SERVANT) ? 1 : 0;
                }catch (Exception e){ }
                tmpfaith += (m.getResouces()[i][colonna].getColor() == Color.red) ? 1 : 0;
            }

        m.exstractColumn(colonna+1 , p );
        b=m.getPendingResourceExtracted();

        assertEquals(ResourceOperator.extractQuantityOf(SERVANT,b), tmpSeverant);
        assertEquals(ResourceOperator.extractQuantityOf(COIN,b), tmpCoin);
        assertEquals(ResourceOperator.extractQuantityOf(ROCK,b), tmpRock);
        assertEquals(ResourceOperator.extractQuantityOf(SHIELD,b), tmpShield);
        assertEquals(tmpfaith, p.getPosition());


        for(colonna=0;colonna<4;colonna++){
            tmpfaith=p.getPosition();
            tmpCoin=0;
            tmpRock=0;
            tmpSeverant=0;
            tmpShield=0;

            for (int i = 0; i < 3; i++) {
                try {
                    tmpCoin += (m.getResouces()[i][colonna].getType() == COIN) ? 1 : 0;
                }catch (Exception e){ }
                try {
                    tmpRock += (m.getResouces()[i][colonna].getType() == ROCK) ? 1 : 0;
                }catch (Exception e){ }
                try {
                    tmpShield += (m.getResouces()[i][colonna].getType() == SHIELD) ? 1 : 0;
                }catch (Exception e){ }
                try {
                    tmpSeverant += (m.getResouces()[i][colonna].getType() == SERVANT) ? 1 : 0;
                }catch (Exception e){ }
                tmpfaith += (m.getResouces()[i][colonna].getColor() == Color.red) ? 1 : 0;
            }

            m.exstractColumn(colonna+1 , p );
            b=m.getPendingResourceExtracted();

            assertEquals(ResourceOperator.extractQuantityOf(SERVANT,b), tmpSeverant);
            assertEquals(ResourceOperator.extractQuantityOf(COIN,b), tmpCoin);
            assertEquals(ResourceOperator.extractQuantityOf(ROCK,b), tmpRock);
            assertEquals(ResourceOperator.extractQuantityOf(SHIELD,b), tmpShield);
            assertEquals(tmpfaith, p.getPosition());

        }


    }

    /**
     * check activation white ball
     */
    @Test
    public void activityTestOneWhite() throws WrongPosition {
        List b;
        Market m= new Market();
        Player p=new Player("nick");
        LeaderCard l[]= new LeaderCard[]{
                new LeaderWhiteCard(new ResourceList(), new ArrayList<PrerequisiteCard>() , 2, COIN),
                new LeaderWhiteCard(new ResourceList(), new ArrayList<PrerequisiteCard>() , 2, SHIELD)};
        p.setLeaders(l);
        try { p.activateLeader(0); } catch (Exception e) { }
        int tmpCoin=0,tmpWhite=0;

        int colonna=0;

        for (int i = 0; i < 3; i++) {
                tmpCoin += (m.getResouces()[i][colonna].getColor() == Color.yellow) ? 1 : 0;
                tmpWhite += (m.getResouces()[i][colonna].getColor()==Color.white) ? 1 : 0;
        }

        m.exstractColumn(colonna+1 , p );
        b=m.getPendingResourceExtracted();

        assertEquals(ResourceOperator.extractQuantityOf(COIN,b), tmpCoin + tmpWhite);
    }

    @Test
    public void activityTestTwoWhite() throws WrongPosition {
        List b;
        Market m= new Market();
        Player p=new Player("nick");
        LeaderCard l[]= new LeaderCard[]{
                new LeaderWhiteCard(new ResourceList(), new ArrayList<PrerequisiteCard>() , 2, COIN),
                new LeaderWhiteCard(new ResourceList(), new ArrayList<PrerequisiteCard>() , 2, SHIELD)};
        p.setLeaders(l);
        try { p.activateLeader(0); } catch (Exception e) { }
        try { p.activateLeader(1); } catch (Exception e) { }
        int tmpWhite=0;

        int colonna=0;

        for (int i = 0; i < 3; i++) {
            tmpWhite += (m.getResouces()[i][colonna].getColor()==Color.white) ? 1 : 0;
        }

        m.exstractColumn(colonna+1 , p );
        b=m.getPendingResourceExtracted();

        assertEquals(m.getWhiteCount(), tmpWhite);
    }



}
