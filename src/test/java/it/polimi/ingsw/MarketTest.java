package it.polimi.ingsw;

import it.polimi.ingsw.enumeration.ResourceType;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.market.balls.BasicBall;
import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceList;
import it.polimi.ingsw.model.resources.ResourceOperator;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;

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
        for(int i=0;i<10000;i++) {
            checkMarketConsistency(new Market());
        }
    }

    /**
     * check exstraction row
     */
    @Test
    public void checkExstractionRow(){

        ArrayList<Resource> r = new ArrayList<>();
        LeaderCard l[] = {new LeaderCard(r,new ArrayList<>(),1, ResourceType.COIN), new LeaderCard(r,new ArrayList<>(),1, ResourceType.COIN) };
        Player p=new Player("nick",l);
        r.add(new Resource(ResourceType.SHIELD,3));
        r.add(new Resource(ResourceType.COIN,10));
        Market m=new Market();
        BasicBall n[],f[],dis;
        for(int i=0;i<3;i++) {
            try {
                n=m.getResouces()[i].clone();
                dis=m.getDiscardedResouce();
                f=m.exstractRow(i+1, p);
                assertEquals(dis, m.getResouces()[i][0]);
                assertEquals(n[0], m.getResouces()[i][1]);
                assertEquals(n[1], m.getResouces()[i][2]);
                assertEquals(n[2], m.getResouces()[i][3]);
                assertEquals(n[3], m.getDiscardedResouce());

                assertEquals(n[0], f[0]);
                assertEquals(n[1], f[1]);
                assertEquals(n[2], f[2]);
                assertEquals(n[3], f[3]);

            }catch (Exception e){
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
        LeaderCard l[] = {new LeaderCard(r,new ArrayList<>(),1, ResourceType.COIN), new LeaderCard(r,new ArrayList<>(),1, ResourceType.COIN) };
        Player p=new Player("nick",l);
        r.add(new Resource(ResourceType.SHIELD,3));
        r.add(new Resource(ResourceType.COIN,10));

        Market m=new Market();
        Color n[] = new Color[3],dis;
        BasicBall f[];
        for(int i=0;i<4;i++) {
            try {
                n[0]=m.getResouces()[0][i].getColor();
                n[1]=m.getResouces()[1][i].getColor();
                n[2]=m.getResouces()[2][i].getColor();
                dis=m.getDiscardedResouce().getColor();
                f=m.exstractColumn(i+1, p);

                assertEquals(dis, m.getResouces()[0][i].getColor());
                assertEquals(n[0], m.getResouces()[1][i].getColor());
                assertEquals(n[1], m.getResouces()[2][i].getColor());
                assertEquals(n[2], m.getDiscardedResouce().getColor());

                assertEquals(n[0], f[0].getColor());
                assertEquals(n[1], f[1].getColor());
                assertEquals(n[2], f[2].getColor());

            }catch (IllegalArgumentException e){
                fail();
            }

        }
        checkMarketConsistency(m);
    }
    @Test
    public void activityTestNoWhite(){
        ResourceList r = new ResourceList();
        LeaderCard l[] = {new LeaderCard(r,new ArrayList<>(),1, ResourceType.COIN), new LeaderCard(r,new ArrayList<>(),1, ResourceType.SERVANT) };
        Player p=new Player("nick",l);
        BasicBall[] b;
        int tmpShield=0,tmpCoin=0,tmpRock=0,tmpSeverant=0,tmpfaith=0;
        Market m=new Market();



        b=m.exstractColumn(1 , p );

        for(BasicBall i:b){

            if (i.getColor() == Color.yellow) {
                tmpCoin++;
            }

            if (i.getColor() == Color.magenta) {
                tmpSeverant++;
            }

            if (i.getColor() == Color.gray) {
                tmpRock++;
            }

            if (i.getColor() == Color.blue) {
                tmpShield++;
            }

            if (i.getColor() == Color.red){
                tmpfaith++;
            }

        }

        for(BasicBall i:b) {
            if (i.getColor() == Color.yellow) {
                try {
                    if(p.getDashboard().getStorage().getFreeSpace( p.getDashboard().getStorage().findType(ResourceType.COIN).get(0) ) > tmpCoin)
                        i.active(p,p.getDashboard().getStorage().findType(ResourceType.COIN).get(0));
                } catch (Exception e) {
                    tmpCoin=0;
                }
            }

            if (i.getColor() == Color.magenta) {
                try {
                    if(p.getDashboard().getStorage().getFreeSpace( p.getDashboard().getStorage().findType(ResourceType.SERVANT).get(0) ) > tmpSeverant)
                        i.active(p,p.getDashboard().getStorage().findType(ResourceType.SERVANT).get(0));
                } catch (Exception e) {
                    tmpSeverant=0;
                }
            }
            if (i.getColor() == Color.gray) {
                try {
                    if(p.getDashboard().getStorage().getFreeSpace( p.getDashboard().getStorage().findType(ResourceType.ROCK).get(0) ) > tmpRock)
                        i.active(p,p.getDashboard().getStorage().findType(ResourceType.ROCK).get(0));
                } catch (Exception e) {
                    tmpRock=0;
                }
            }
            if (i.getColor() == Color.blue) {
                try {
                    if(p.getDashboard().getStorage().getFreeSpace( p.getDashboard().getStorage().findType(ResourceType.SHIELD).get(0) ) > tmpShield)
                        i.active(p,p.getDashboard().getStorage().findType(ResourceType.SHIELD).get(0));
                } catch (Exception e) {
                    tmpShield=0;
                }
            }
            if (i.getColor() == Color.red){
                i.active(p);
            }
        }

        assertEquals(tmpSeverant, ResourceOperator.extractQuantityOf(ResourceType.SERVANT, p.getDashboard().getAllAvailableResource()));
        assertEquals(tmpCoin, ResourceOperator.extractQuantityOf(ResourceType.COIN, p.getDashboard().getAllAvailableResource()));
        assertEquals(tmpRock, ResourceOperator.extractQuantityOf(ResourceType.ROCK, p.getDashboard().getAllAvailableResource()));
        assertEquals(tmpShield, ResourceOperator.extractQuantityOf(ResourceType.SHIELD, p.getDashboard().getAllAvailableResource()));
        assertEquals(tmpfaith, p.getPosition());


        for(int cont=1;cont<5;cont++){
            b=m.exstractColumn(cont , p );

            for(BasicBall i:b){

                if (i.getColor() == Color.yellow) {
                    tmpCoin++;
                }

                if (i.getColor() == Color.magenta) {
                    tmpSeverant++;
                }

                if (i.getColor() == Color.gray) {
                    tmpRock++;
                }

                if (i.getColor() == Color.blue) {
                    tmpShield++;
                }

                if (i.getColor() == Color.red){
                    tmpfaith++;
                }

            }
            for(BasicBall i:b) {
                if (i.getColor() == Color.yellow) {
                    try {
                        if(p.getDashboard().getStorage().getFreeSpace( p.getDashboard().getStorage().findType(ResourceType.COIN).get(0) ) > tmpCoin)
                            i.active(p,p.getDashboard().getStorage().findType(ResourceType.COIN).get(0));
                    } catch (Exception e) {
                        tmpCoin=0;
                    }
                }

                if (i.getColor() == Color.magenta) {
                    try {
                        if(p.getDashboard().getStorage().getFreeSpace( p.getDashboard().getStorage().findType(ResourceType.SERVANT).get(0) ) > tmpSeverant)
                            i.active(p,p.getDashboard().getStorage().findType(ResourceType.SERVANT).get(0));
                    } catch (Exception e) {
                        tmpSeverant=0;
                    }
                }
                if (i.getColor() == Color.gray) {
                    try {
                        if(p.getDashboard().getStorage().getFreeSpace( p.getDashboard().getStorage().findType(ResourceType.ROCK).get(0) ) > tmpRock)
                            i.active(p,p.getDashboard().getStorage().findType(ResourceType.ROCK).get(0));
                    } catch (Exception e) {
                        tmpRock=0;
                    }
                }
                if (i.getColor() == Color.blue) {
                    try {
                        if(p.getDashboard().getStorage().getFreeSpace( p.getDashboard().getStorage().findType(ResourceType.SHIELD).get(0) ) > tmpShield)
                            i.active(p,p.getDashboard().getStorage().findType(ResourceType.SHIELD).get(0));
                    } catch (Exception e) {
                        tmpShield=0;
                    }
                }
                if (i.getColor() == Color.red){
                    i.active(p);
                }
            }

            assertEquals(tmpSeverant, ResourceOperator.extractQuantityOf(ResourceType.SERVANT, p.getDashboard().getAllAvailableResource()));
            assertEquals(tmpCoin, ResourceOperator.extractQuantityOf(ResourceType.COIN, p.getDashboard().getAllAvailableResource()));
            assertEquals(tmpRock, ResourceOperator.extractQuantityOf(ResourceType.ROCK, p.getDashboard().getAllAvailableResource()));
            assertEquals(tmpShield, ResourceOperator.extractQuantityOf(ResourceType.SHIELD, p.getDashboard().getAllAvailableResource()));
            assertEquals(tmpfaith, p.getPosition());

        }
    }


}
