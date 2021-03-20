package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.ResourceType;

public class ResourceBall  extends BasicBall{
    ResourceType Type;

    public ResourceBall(ResourceType type) {
        Type = type;
    }

    public void active(Player p){
        //chiede al player dove aggiungere le risorse
        //p.getDashboard().getStorage().safeInsertion(new Resource(Type,1),posizioneChiestaPrima);
    }
}
