package it.polimi.ingsw.model.market.balls;

import it.polimi.ingsw.enumeration.ResourceType;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.view.utils.CliColors;

import java.awt.*;

public class RedBall extends BasicBall{

    /**
     *  se true extracted red ball
     * @param m market
     */
    @Override
    public void active(Market m,Player p){
        p.incrementPosition();
    }

    /**
     *
     * @return type color
     */
    @Override
    public Color getColor() {
        return Color.red;
    }

    /**
     *  throw exeption
     * @return no one
     */
    @Override
    public ResourceType getType() {
        throw new RuntimeException("White and red ball dont have resource type");
    }

    @Override
    public String getCliColor() {
        return CliColors.RED_TEXT;
    }
}
