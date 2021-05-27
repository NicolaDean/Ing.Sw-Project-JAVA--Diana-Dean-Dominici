package it.polimi.ingsw.model.market.balls;

import it.polimi.ingsw.enumeration.ResourceType;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.market.Market;

import java.awt.*;

public class ResourceBall  extends BasicBall {
    ResourceType type;
    String GUI;
    String CLI;

    public ResourceBall(String colorGUI, ResourceType type, String cliColors) {
        super();
        this.type = type;
        this.CLI=cliColors;
        this.GUI =colorGUI;
        this.setColor(colorGUI,CLI);
    }

    /**
     * Add Resource to the player
     * @param m market
     * @param p player
     */
    public void active(Market m, Player p){
        m.addResourceExtracted(type);
    }

    public ResourceType getType(){return type;}

}
