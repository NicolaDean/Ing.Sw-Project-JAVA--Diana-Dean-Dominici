package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.ResourceType;

import java.awt.*;

public class BlueBall extends ResourceBall{

    /**
     * Add Resource to the player
     * @param p: player that get ball
     */
    @Override
    public void active(Player p){
        //chiede al player dove aggiungere le risorse tramite View
        //p.getDashboard().getStorage().safeInsertion(new Resource(SHILD,1),posizioneChiestaPrima);
    }

    /**
     *
     * @return type color
     */
    @Override
    public Color getColor() {
        return Color.blue;
    }
}
