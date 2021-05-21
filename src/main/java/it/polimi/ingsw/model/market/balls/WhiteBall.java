
package it.polimi.ingsw.model.market.balls;

import it.polimi.ingsw.enumeration.ResourceType;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.view.utils.CliColors;

import java.awt.*;
import java.beans.Transient;

public class WhiteBall extends BasicBall{

    public WhiteBall() {
        this.setColor(Color.white,CliColors.R_WHITE_TEXT);
    }

    /**
     * if player had 2 leader activated increment white cout, if had just 1 leader add resource
     * @param m market
     * @param p player
     */
    @Override
    public void active(Market m,Player p) {
        if(p.getBonusball().size()==1)
            m.addResourceExtracted(p.getBonusball().get(0));
        if(p.getBonusball().size()==2)
            m.incrementWhiteCount();
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
