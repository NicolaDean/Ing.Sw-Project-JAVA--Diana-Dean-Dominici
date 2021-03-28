
package it.polimi.ingsw.model;

import java.awt.*;

public class WhiteBall extends BasicBall{
    /**
     * active ability
     * @param P: player that get red ball
     */
    @Override
    public void active(Player P)
    {
        //attiva abilità leader

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