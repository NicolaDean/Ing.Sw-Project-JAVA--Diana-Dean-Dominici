package it.polimi.ingsw.model.market.balls;

import it.polimi.ingsw.enumeration.ResourceType;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.view.utils.CliColors;

import java.awt.*;
import java.io.Serializable;

public class BasicBall implements Serializable {
    String colorGUI;
    String colorCLI;

    public void setColor(String  c,String s){
        colorGUI=c;
        colorCLI=s;
    }

    public String getColor(){
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