package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.market.balls.*;
import it.polimi.ingsw.model.market.balls.resourceballs.BlueBall;
import it.polimi.ingsw.model.market.balls.resourceballs.GrayBall;
import it.polimi.ingsw.model.market.balls.resourceballs.VioletBall;
import it.polimi.ingsw.model.market.balls.resourceballs.YellowBall;

public class Market {
    private BasicBall discardedResouce;
    private BasicBall resouces[][] = { { new WhiteBall(), new WhiteBall() , new WhiteBall() ,new WhiteBall() } ,
                               { new BlueBall(), new BlueBall() , new GrayBall() ,new GrayBall() } ,
                               { new YellowBall(), new YellowBall() , new VioletBall() ,new VioletBall() } };

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
    public BasicBall[] exstractRow(int pos, Player p) {
        BasicBall tmp;
        BasicBall out[] = new BasicBall[4];
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

        }
        return out;
    }
    /**
     *
     * @param pos column position, it must be between 1 and 4
     * @param p player
     */
    public BasicBall[] exstractColumn(int pos,Player p) {
        BasicBall tmp;
        BasicBall out[] = new BasicBall[3];
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
        }
        return out;
    }
}
