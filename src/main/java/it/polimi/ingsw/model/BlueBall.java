package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.ResourceType;

import java.awt.*;

import static it.polimi.ingsw.enumeration.ResourceType.SHILD;

public class BlueBall extends ResourceBall{

    /**
     * Add Resource to the player
     * @param p player
     * @param pos position (between 1 and 3)
     */
    @Override
    public void active(Player p, int pos){
        p.getDashboard().storageInsertion(new Resource(SHILD,1),pos);
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
