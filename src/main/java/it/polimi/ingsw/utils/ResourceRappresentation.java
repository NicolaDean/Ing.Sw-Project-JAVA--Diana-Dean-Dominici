package it.polimi.ingsw.utils;

import it.polimi.ingsw.enumeration.ResourceType;
import it.polimi.ingsw.view.utils.CliColors;

import java.util.HashMap;

import static it.polimi.ingsw.enumeration.ResourceType.*;

public class ResourceRappresentation {

    private final HashMap<ResourceType,String> colorRappresentation   = new HashMap<ResourceType,String>();
    private final HashMap<ResourceType,String> noColorRappresentation = new HashMap<ResourceType,String>();

    public ResourceRappresentation()
    {
        colorRappresentation.put(COIN   , CliColors.YELLOW_BACKGROUND);
        colorRappresentation.put(SERVANT, CliColors.MAGENTA_BACKGROUND);
        colorRappresentation.put(SHIELD , CliColors.BLUE_BACKGROUND);
        colorRappresentation.put(ROCK   , CliColors.WHITE_BACKGROUND);


        noColorRappresentation.put(COIN   , "-CO-");
        noColorRappresentation.put(SERVANT, "-SE-");
        noColorRappresentation.put(SHIELD , "-SH-");
        noColorRappresentation.put(ROCK   , "-RO-");
    }

    public String getColorRappresentation(ResourceType resourceType)
    {
        return this.colorRappresentation.get(resourceType);
    }

    public String getNonColorRappresentation(ResourceType resourceType)
    {
        return this.noColorRappresentation.get(resourceType);
    }
}
