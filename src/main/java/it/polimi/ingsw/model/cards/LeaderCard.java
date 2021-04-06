package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.enumeration.ResourceType;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceOperator;

import java.util.List;

public class LeaderCard extends Card{

    private ResourceType type;
    private List<PrerequisiteCard> cardPrequisite;


    public LeaderCard(List<Resource> cost,List<PrerequisiteCard> cardPrequisite, int victoryPoints, ResourceType type) {
        super(cost, victoryPoints);
        this.type = type;
        this.cardPrequisite = cardPrequisite;
    }

    public ResourceType getType()
    {
        return this.type;
    }

    /**
     * check the prerequisite to activate a card and then activate it
     * @param p player who own the card
     * @return true if activation goes well
     */
    public boolean activate(Player p)
    {
        return true;
        /*boolean resourceRequisite = true;
        boolean cardRequisite = true;

        //Check resource prerequisite if necessary
        if(!this.getCost().isEmpty())
            resourceRequisite = ResourceOperator.compare(p.getDashboard().getAllAvailableResource(),this.getCost());
        //Check card prerequisite if necessary
        if(cardPrequisite !=null)
            cardRequisite     = p.getDashboard().checkCardPresence(cardPrequisite);

        return  resourceRequisite && cardRequisite;

         */
    }
}
