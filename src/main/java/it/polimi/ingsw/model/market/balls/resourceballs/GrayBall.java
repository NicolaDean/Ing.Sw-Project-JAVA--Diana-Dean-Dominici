package it.polimi.ingsw.model.market.balls.resourceballs;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.market.balls.ResourceBall;
import it.polimi.ingsw.model.resources.Resource;

import java.awt.*;

import static it.polimi.ingsw.enumeration.resourceType.ROCK;

public class GrayBall extends ResourceBall {
    /**
     * Add Resource to the player
     * @param p player
     * @param pos position (between 1 and 3)
     */
    @Override
    public void active(Player p, int pos){
        p.addResource(new Resource(ROCK,1),pos);
    }


    /**
     *
     * @return type color
     */
    @Override
    public Color getColor() {
        return Color.gray;
    }
}
