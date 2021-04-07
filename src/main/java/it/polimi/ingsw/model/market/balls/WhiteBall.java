
package it.polimi.ingsw.model.market.balls;

import it.polimi.ingsw.model.Player;

import java.awt.*;

public class WhiteBall extends BasicBall{

    /**
     * active ability
     * @param p: player that get red ball
     */
    @Override
    public void active(Player p,int pos) {
        int size= p.getBonusball().size();
        if(size<=1)
            p.getBonusball().get(0).active(p,pos);
        if(size==2)
            p.incrementPendingWhiteBall();
    }

    /**
     *
     * @return type color
     */
    @Override
    public Color getColor() {
        return Color.white;
    }

}


/*
    int num= Controller.Model.getPlayerWhiteBonus();


 */