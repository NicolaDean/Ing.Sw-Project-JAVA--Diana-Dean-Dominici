package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.ResourceType;

import java.awt.*;

public abstract class ResourceBall  extends BasicBall{

    /**
     * Add Resource to the player
     * @param p: player that get ball
     */
    @Override
    public abstract void active(Player p);

    /**
     *
     * @return type color
     */
    @Override
    public abstract Color getColor();
}
