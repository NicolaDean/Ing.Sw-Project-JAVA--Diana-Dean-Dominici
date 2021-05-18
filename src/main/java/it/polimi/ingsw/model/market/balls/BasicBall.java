package it.polimi.ingsw.model.market.balls;

import it.polimi.ingsw.enumeration.ResourceType;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.view.utils.CliColors;

import java.awt.*;

public class BasicBall {
    transient Color colorGUI;
    String colorCLI;

    public void setColor(Color  c,String s){
        colorGUI=c;
        colorCLI=s;
    }

    public Color getColor(){
        return colorGUI;
    }

    public String getCliColor(){
        return colorCLI;
    }

    public void active(Market market, Player p){}

    public ResourceType getType(){
        return null;
    }

}