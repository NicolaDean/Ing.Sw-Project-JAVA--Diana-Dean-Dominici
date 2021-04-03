package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.ResourceType;

import java.awt.*;

import static it.polimi.ingsw.enumeration.ResourceType.SHIELD;

public class BlueBall extends ResourceBall{

    /**
     * Add Resource to the player
     * @param p player
     * @param pos position (between 1 and 3)
     */
    @Override
    public void active(Player p, int pos){
        p.addResource(new Resource(SHIELD,1),pos);
    }

    /**
     *
     * @return type color
     */
    @Override
    public Color getColor() {
        return Color.blue;
    }
}
