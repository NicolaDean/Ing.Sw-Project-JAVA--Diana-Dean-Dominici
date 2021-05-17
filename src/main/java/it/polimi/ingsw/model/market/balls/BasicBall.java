package it.polimi.ingsw.model.market.balls;

import it.polimi.ingsw.enumeration.ResourceType;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.market.Market;

import java.awt.*;
import java.beans.Transient;

public class BasicBall {

    Color colorGUI;
    String colorCLI;

    public void setColor(Color colorGUI,String colorCLI) {
        this.colorGUI = colorGUI;
        this.colorCLI = colorCLI;
    }

    public Color getColor(){return this.colorGUI;}

    public String getCliColor(){return this.colorCLI;}

    public void active(Market market, Player p){}

    public ResourceType getType(){return null;}
}