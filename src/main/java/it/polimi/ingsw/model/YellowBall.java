package it.polimi.ingsw.model;

import java.awt.*;

public class YellowBall extends ResourceBall{
    /**
     * Add Resource to the player
     * @param p: player that get ball
     */
    @Override
    public void active(Player p){
        //chiede al player dove aggiungere le risorse tramite View
        //p.getDashboard().getStorage().safeInsertion(new Resource(COIN,1),posizioneChiestaPrima);
    }

    /**
     *
     * @return type color
     */
    @Override
    public Color getColor() {
        return Color.yellow;
    }
}
