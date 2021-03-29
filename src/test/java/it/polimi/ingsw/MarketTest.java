package it.polimi.ingsw;

import it.polimi.ingsw.enumeration.ResourceType;
import it.polimi.ingsw.model.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
        LeaderCard l[] = {new LeaderCard(r,1), new LeaderCard(r,1) };
        Player p=new Player("nick",l);
        r.add(new Resource(ResourceType.SHIELD,3));
        r.add(new Resource(ResourceType.COIN,10));

        int i;
        Market m=new Market();
        BasicBall n[],dis;
        for(int j=0;j<10000;j++) {
            try {
                i=(int)(Math.random()*10)%3;
                n=m.getResouces()[i].clone();
                dis=m.getDiscardedResouce();
                m.exstractRow(i+1, p);
                assertTrue(m.getDiscardedResouce().equals(n[3]));
                assertTrue(m.getResouces()[i][0].equals(dis));
                assertTrue(m.getResouces()[i][1].equals(n[0]));
                assertTrue(m.getResouces()[i][2].equals(n[1]));
                assertTrue(m.getResouces()[i][3].equals(n[2]));

            }catch (Exception e){
                assertTrue(false);
            }
        }
        checkMarketConsistency(m);
    }

    /**
     * check exstraction row with exeption
     */
    @Test
    public void checkExstractionRowWithExeption(){

        ArrayList<Resource> r = new ArrayList<>();
        LeaderCard l[] = {new LeaderCard(r,1), new LeaderCard(r,1) };
        Player p=new Player("nick",l);
        r.add(new Resource(ResourceType.SHIELD,3));
        r.add(new Resource(ResourceType.COIN,10));

        int i;
        Market m=new Market();
        BasicBall n[],dis;
        for(int j=0;j<10000;j++) {
            try {
                i=(int)(Math.random()*10);
                n=m.getResouces()[i].clone();
                dis=m.getDiscardedResouce();
                m.exstractRow(i+1, p);
                assertTrue(m.getDiscardedResouce().equals(n[3]));
                assertTrue(m.getResouces()[i][0].equals(dis));
                assertTrue(m.getResouces()[i][1].equals(n[0]));
                assertTrue(m.getResouces()[i][2].equals(n[1]));
                assertTrue(m.getResouces()[i][3].equals(n[2]));

            }catch (Exception e){
                assertTrue(true);
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
        LeaderCard l[] = {new LeaderCard(r,1), new LeaderCard(r,1) };
        Player p=new Player("nick",l);
        r.add(new Resource(ResourceType.SHIELD,3));
        r.add(new Resource(ResourceType.COIN,10));

        int i;
        Market m=new Market();
        Color n[] = new Color[3],dis;
        for(int j=0;j<10000;j++) {
            try {
                i=(int)(Math.random()*10)%3;
                n[0]=m.getResouces()[0][i].getColor();
                n[1]=m.getResouces()[1][i].getColor();
                n[2]=m.getResouces()[2][i].getColor();
                dis=m.getDiscardedResouce().getColor();
                m.exstractColumn(i+1, p);
                assertTrue(m.getDiscardedResouce().getColor().equals(n[2]));
                assertTrue(m.getResouces()[0][i].getColor().equals(dis));
                assertTrue(m.getResouces()[1][i].getColor().equals(n[0]));
                assertTrue(m.getResouces()[2][i].getColor().equals(n[1]));
            }catch (Exception e){
                assertTrue(false);
            }
        }
        checkMarketConsistency(m);
    }

    /**
     * check exstraction column with exeption
     */
    @Test
    public void checkExstractionColumnWithExeption(){

        ArrayList<Resource> r = new ArrayList<>();
        LeaderCard l[] = {new LeaderCard(r,1), new LeaderCard(r,1) };
        Player p=new Player("nick",l);
        r.add(new Resource(ResourceType.SHIELD,3));
        r.add(new Resource(ResourceType.COIN,10));

        int i;
        Market m=new Market();
        Color n[] = new Color[3],dis;
        for(int j=0;j<10000;j++) {
            try {
                i=(int)(Math.random()*10);
                n[0]=m.getResouces()[0][i].getColor();
                n[1]=m.getResouces()[1][i].getColor();
                n[2]=m.getResouces()[2][i].getColor();
                dis=m.getDiscardedResouce().getColor();
                m.exstractColumn(i+1, p);
                assertTrue(m.getDiscardedResouce().getColor().equals(n[2]));
                assertTrue(m.getResouces()[0][i].getColor().equals(dis));
                assertTrue(m.getResouces()[1][i].getColor().equals(n[0]));
                assertTrue(m.getResouces()[2][i].getColor().equals(n[1]));
            }catch (Exception e){
                assertTrue(true);
            }
        }
        checkMarketConsistency(m);
    }
}
