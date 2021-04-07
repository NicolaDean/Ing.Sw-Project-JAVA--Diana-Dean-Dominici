package it.polimi.ingsw.model.market.balls;

import it.polimi.ingsw.enumeration.ResourceType;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.resources.Resource;

import java.awt.*;

import static it.polimi.ingsw.enumeration.ResourceType.COIN;

public class ResourceBall  extends BasicBall {

    ResourceType type;

    public void active(Player p) {}

    public Color getColor() {
        return Color.BLACK;
    }

    public void active(Player p, int pos) {
        p.addResource(new Resource(type,1),pos);
    }

    public void setType(ResourceType type){
        this.type=type;
    }
}
