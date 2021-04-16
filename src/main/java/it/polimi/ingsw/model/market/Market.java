package it.polimi.ingsw.model.market;

import it.polimi.ingsw.enumeration.ResourceType;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.market.balls.*;
import it.polimi.ingsw.model.resources.ResourceList;

import java.awt.*;
import java.util.List;

import static it.polimi.ingsw.enumeration.ResourceType.*;

public class Market {
    private int whiteCount=0;
    private List pendingResourceExtracted = new ResourceList();
    private BasicBall discardedResouce;
    private BasicBall resouces[][] = { { new WhiteBall(), new WhiteBall() , new WhiteBall() ,new WhiteBall() } ,
                               { new ResourceBall(Color.blue,SHIELD), new ResourceBall(Color.blue,SHIELD) , new ResourceBall(Color.gray,ROCK) ,new ResourceBall(Color.gray,ROCK) } ,
                               { new ResourceBall(Color.yellow,COIN), new ResourceBall(Color.yellow,COIN) , new ResourceBall(Color.magenta,SERVANT) ,new ResourceBall(Color.magenta,SERVANT) } };

    /**
     * build e shuffle balls
     */
    public Market() {
        discardedResouce = new RedBall();
        randomized();
    }

    /**
     * get resource
     * @return balls
     */
    public BasicBall[][] getResouces() {
        return resouces;
    }

    /**
     * get discarded ball
     * @return discarded ball
     */
    public BasicBall getDiscardedResouce() {
        return discardedResouce;
    }

    /**
     * shuffle balls 300 times
     */
    private void randomized(){
        BasicBall Tmp;
        int r,c,n=2;

        for(int p=0;p<n;p++) {
            for (int i = 0; i < 3; i++) {
                for(int j = 0; j < 4; j++){
                    r=(int)(Math.random()*10)%3;
                    c=(int)(Math.random()*10)%4;
                    Tmp = resouces[i][j];
                    resouces[i][j] = resouces[r][c];
                    resouces[r][c] = Tmp;

                    if((int)(Math.random()*10)<4){
                        r=(int)(Math.random()*10)%3;
                        c=(int)(Math.random()*10)%4;
                        Tmp = resouces[r][c];
                        resouces[r][c] = discardedResouce;
                        discardedResouce = Tmp;
                    }
                }
            }

        }
    }

    /**
     *extraction row
     * @param pos row position, it must be between 1 and 3
     * @param p player
     */
    public void exstractRow(int pos, Player p) {
        BasicBall tmp;
        BasicBall out[] = new BasicBall[4];
        whiteCount=0;
        pendingResourceExtracted = new ResourceList();
        if ((pos > 3) || (pos < 1)) {
            throw new IllegalArgumentException("invalid position");
        } else {
            pos--;

            for (int i = 0; i < 4; i++)
                out[i] = resouces[pos][i];

            for (int i = 1; i < 4; i++) {
                tmp = resouces[pos][i];
                resouces[pos][i] = resouces[pos][0];
                resouces[pos][0] = tmp;
            }
            tmp = discardedResouce;
            discardedResouce = resouces[pos][0];
            resouces[pos][0] = tmp;

            for(BasicBall i:out){
                i.active(this,p);
            }

        }
    }
    /**
     *
     * @param pos column position, it must be between 1 and 4
     * @param p player
     */
    public void exstractColumn(int pos,Player p) {
        BasicBall tmp;
        BasicBall out[] = new BasicBall[3];
        whiteCount=0;
        pendingResourceExtracted = new ResourceList();
        if ((pos > 4) || (pos < 1)) {
            throw new IllegalArgumentException("invalid position");
        } else {
            pos--;
            for (int i = 0; i < 3; i++)
                    out[i] = resouces[i][pos];

            for (int i = 1; i < 3; i++) {
                tmp = resouces[i][pos];
                resouces[i][pos] = resouces[0][pos];
                resouces[0][pos] = tmp;
            }
            tmp = discardedResouce;
            discardedResouce = resouces[0][pos];
            resouces[0][pos] = tmp;

            for(BasicBall i:out){
                i.active(this,p);
            }

        }
    }

    /**
     * increment whiteCount
     */
    public void incrementWhiteCount(){
        whiteCount++;
    }

    /**
     *  add resourse in pendingResourceExtracted
     * @param type
     */
    public void addResourceExtracted(ResourceType type){
        pendingResourceExtracted.add(type);
    }

    /**
     * get pendingResourceExtracted
     * @return pendingResourceExtracted
     */
    public List getPendingResourceExtracted() {
        return pendingResourceExtracted;
    }
}
