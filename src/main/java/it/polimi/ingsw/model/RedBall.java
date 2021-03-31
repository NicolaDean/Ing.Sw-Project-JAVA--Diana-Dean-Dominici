package it.polimi.ingsw.model;

import java.awt.*;

public class RedBall extends BasicBall{
    @Override
    public void active(Player p, int pos) { }

    /**
     * increment papal position
     * @param P: player that get red ball
     */
    @Override
    public void active(Player P){
        P.incrementPosition();
    }

    /**
     *
     * @return type color
     */
    @Override
    public Color getColor() {
        return Color.red;
    }
}
