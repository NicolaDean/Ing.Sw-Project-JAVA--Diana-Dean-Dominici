package it.polimi.ingsw.model.market.balls;

import it.polimi.ingsw.enumeration.ResourceType;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.view.utils.CliColors;

import java.awt.*;

import static it.polimi.ingsw.enumeration.ResourceType.COIN;
import static it.polimi.ingsw.enumeration.ResourceType.ROCK;

public class ResourceBall  extends BasicBall {
    ResourceType type;
    String cliColors;
    Color color;

    public ResourceBall(Color color, ResourceType type, String cliColors) {
        this.type = type;
        this.cliColors = cliColors;
        this.color=color;
    }

    /**
     * Add Resource to the player
     * @param m market
     * @param p player
     */
    public void active(Market m, Player p){
        m.addResourceExtracted(type);
    }

    public Color getColor(){
        return color;
    }

    public ResourceType getType(){return type;}

    @Override
    public String getCliColor() {
        return cliColors;
    }

}
