package it.polimi.ingsw;

import it.polimi.ingsw.enumeration.ResourceType;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.market.balls.BasicBall;
import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceList;
import static it.polimi.ingsw.enumeration.ResourceType.*;
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
                System.out.print(m.getResouces()[i][j]+"      ");
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
        List f= new ResourceList();
        for(int i=0;i<3;i++) {
            try {
                n = m.getResouces()[i].clone();
                dis = m.getDiscardedResouce();
                m.exstractRow(i + 1, p);
                f = m.getPendingResourceExtracted();
                assertEquals(dis, m.getResouces()[i][0]);
                assertEquals(n[0], m.getResouces()[i][1]);
                assertEquals(n[1], m.getResouces()[i][2]);
                assertEquals(n[2], m.getResouces()[i][3]);
                assertEquals(n[3], m.getDiscardedResouce());

                assertEquals(n[0].getType(), f.get(0));
                assertEquals(n[1].getType(), f.get(1));
                assertEquals(n[2], f.get(2));
                assertEquals(n[3], f.get(3));
            }catch (Exception e){
                if((i>2)||(i<0))
                    fail();
            }
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
        ResourceType n[] = new ResourceType[3],dis;
        List f=new ResourceList();
        for(int i=0;i<4;i++) {
            try {
                n[0]=m.getResouces()[0][i].getType();
                n[1]=m.getResouces()[1][i].getType();
                n[2]=m.getResouces()[2][i].getType();
                dis=m.getDiscardedResouce().getType();
                m.exstractColumn(i+1, p);
                f=m.getPendingResourceExtracted();
                assertEquals(dis, m.getResouces()[0][i].getColor());
                assertEquals(n[0], m.getResouces()[1][i].getColor());
                assertEquals(n[1], m.getResouces()[2][i].getColor());
                assertEquals(n[2], m.getDiscardedResouce().getColor());

                assertEquals(n[0], f.get(0));
                assertEquals(n[1], f.get(1));
                assertEquals(n[2], f.get(2));

            }catch (Exception e){
                if((i>3)||(i<0))
                    fail();
            }

        }
        checkMarketConsistency(m);
    }



}
