package it.polimi.ingsw.model.market.balls;

import it.polimi.ingsw.enumeration.ResourceType;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.market.Market;

import java.awt.*;

public abstract class BasicBall {

    public abstract Color getColor();

    public abstract void active(Market market, Player p);

    public abstract ResourceType getType();
}