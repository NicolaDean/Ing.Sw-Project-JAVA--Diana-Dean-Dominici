package it.polimi.ingsw.model;

import java.awt.*;

import static it.polimi.ingsw.enumeration.ResourceType.SERVANT;
import static it.polimi.ingsw.enumeration.ResourceType.SHILD;

public class VioletBall extends ResourceBall{
    /**
     * Add Resource to the player
     * @param p player
     * @param pos position (between 1 and 3)
     */
    @Override
    public void active(Player p, int pos){
        p.getDashboard().storageInsertion(new Resource(SERVANT,1),pos);
    }


    /**
     *
     * @return type color
     */
    @Override
    public Color getColor() {
        return Color.magenta;
    }
}
