package it.polimi.ingsw.model.market.balls;

import it.polimi.ingsw.enumeration.ResourceType;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.view.utils.CliColors;

import java.awt.*;
import java.beans.Transient;

public class RedBall extends BasicBall{

    public RedBall() {
        setColor("red",CliColors.RED_TEXT);
    }

    /**
     *  se true extracted red ball
     * @param m market
     */
    @Override
    public void active(Market m,Player p){
        m.setRedBallExtracted(true);
        p.incrementPosition();
    }


    /**
     *  throw exeption
     * @return no one
     */
    @Override
    public ResourceType getType() {
        throw new RuntimeException("White and red ball dont have resource type");
    }


}
