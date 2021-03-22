package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.ResourceType;

import java.security.PublicKey;

public class Market {
    private BasicBall DiscardedResouce;
    private BasicBall Resouces[][] = { { new WhiteBall(), new WhiteBall() , new WhiteBall() ,new WhiteBall() } ,
                               { new BlueBall(), new BlueBall() , new GrayBall() ,new GrayBall() } ,
                               { new YellowBall(), new YellowBall() , new VioletBall() ,new VioletBall() } };

    /**
     * build e shuffle balls
     */
    public Market() {
        DiscardedResouce = new RedBall();
        Randomized();
    }

    /**
     * get resource
     * @return balls
     */
    public BasicBall[][] getResouces() {
        return Resouces;
    }

    /**
     * get discarded ball
     * @return discarded ball
     */
    public BasicBall getDiscardedResouce() {
        return DiscardedResouce;
    }

    /**
     * shuffle balls 300 times
     */
    private void Randomized(){
        BasicBall Tmp;
        int r,c,n=2;

        for(int p=0;p<n;p++) {
            for (int i = 0; i < 3; i++) {
                for(int j = 0; j < 4; j++){
                    r=(int)(Math.random()*10)%3;
                    c=(int)(Math.random()*10)%4;
                    Tmp = Resouces[i][j];
                    Resouces[i][j] = Resouces[r][c];
                    Resouces[r][c] = Tmp;

                    if((int)(Math.random()*10)<4){
                        r=(int)(Math.random()*10)%3;
                        c=(int)(Math.random()*10)%4;
                        Tmp = Resouces[r][c];
                        Resouces[r][c] = DiscardedResouce;
                        DiscardedResouce = Tmp;
                    }
                }
            }

        }
    }

    /**
     *
     * @param Pos row position, it must be between 1 and 3
     * @param P player
     */
    public void exstractRow(int Pos,Player P) {
        BasicBall Tmp;
        Pos--;
        if ((Pos > 2) || (Pos < 0)) {
            throw new IllegalArgumentException("invalid position");
        } else {
            for (int i = 1; i < 4; i++) {
                Resouces[Pos][i].active(P);
                Tmp = Resouces[Pos][i];
                Resouces[Pos][i] = Resouces[Pos][0];
                Resouces[Pos][0] = Tmp;
            }
            Tmp = DiscardedResouce;
            DiscardedResouce = Resouces[Pos][0];
            Resouces[Pos][0] = Tmp;
        }
    }
    /**
     *
     * @param Pos column position, it must be between 1 and 4
     * @param P player
     */
    public void exstractColumn(int Pos,Player P) {
        BasicBall Tmp;
        Pos--;
        if (Pos > 3 || Pos < 0) {
            throw new IllegalArgumentException("invalid position");
        } else {
            for (int i = 1; i < 3; i++) {
                Resouces[i][Pos].active(P);
                Tmp = Resouces[i][Pos];
                Resouces[i][Pos] = Resouces[0][Pos];
                Resouces[0][Pos] = Tmp;
            }
            Tmp = DiscardedResouce;
            DiscardedResouce = Resouces[0][Pos];
            Resouces[0][Pos] = Tmp;
        }
    }



}
