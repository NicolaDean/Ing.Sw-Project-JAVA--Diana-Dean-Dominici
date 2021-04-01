package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.ResourceType;

public interface Production {

    public boolean produce(Dashboard dashboard);

    public boolean produce(Dashboard dashboard, ResourceType obtain);
}
