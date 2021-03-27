package it.polimi.ingsw.model;

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
        Randomized();
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
    private void Randomized(){
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
                resouces[Pos][i].active(P);
                Tmp = resouces[Pos][i];
                resouces[Pos][i] = resouces[Pos][0];
                resouces[Pos][0] = Tmp;
            }
            Tmp = discardedResouce;
            discardedResouce = resouces[Pos][0];
            resouces[Pos][0] = Tmp;
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
                resouces[i][Pos].active(P);
                Tmp = resouces[i][Pos];
                resouces[i][Pos] = resouces[0][Pos];
                resouces[0][Pos] = Tmp;
            }
            Tmp = discardedResouce;
            discardedResouce = resouces[0][Pos];
            resouces[0][Pos] = Tmp;
        }
    }



}
