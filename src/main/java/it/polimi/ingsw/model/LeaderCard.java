package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.ResourceType;

import java.util.ArrayList;
import java.util.List;

public class LeaderCard extends Card{

    private ResourceType type;

    public LeaderCard(List<Resource> cost, int victoryPoints,ResourceType type) {
        super(cost, victoryPoints);
        this.type = type;
    }

    public ResourceType getType()
    {
        return this.type;
    }
    public void activate(Player p)
    {

    }
}
