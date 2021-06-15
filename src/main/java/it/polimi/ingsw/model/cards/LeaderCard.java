package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.controller.packets.ACK;
import it.polimi.ingsw.controller.packets.Packet;
import it.polimi.ingsw.enumeration.ResourceType;
import it.polimi.ingsw.exceptions.LeaderActivated;
import it.polimi.ingsw.exceptions.NotSoddisfedPrerequisite;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceOperator;
import it.polimi.ingsw.utils.ConstantValues;
import it.polimi.ingsw.utils.DebugMessages;
import it.polimi.ingsw.view.utils.CliColors;

import java.util.List;

public class LeaderCard extends Card {

    private boolean      active;
    private ResourceType type;
    private List<PrerequisiteCard> cardPrequisite;
    private String cliRappresentation = "Leader";
    private int id;
    private int activationOrder=-1;

    transient Packet updateMinimodel;


    public LeaderCard(List<Resource> cost,List<PrerequisiteCard> cardPrequisite, int victoryPoints, ResourceType type) {
        super(cost, victoryPoints);
        this.type = type;
        this.cardPrequisite = cardPrequisite;
    }
    public void setId(int id)
    {
        this.id = id;
    }

    public int getId()
    {
        return id;
    }
    public ResourceType getType()
    {
        return this.type;
    }

    public void setActivationOrder(int order)
    {
        this.activationOrder = order;
    }

    public void setCardPrequisite(List<PrerequisiteCard> cardPrequisite) {
        this.cardPrequisite = cardPrequisite;
    }

    /**
     * check the prerequisite to activate a card and then activate it
     * @param p player who own the card
     * @return true if activation goes well
     */
    public void activate(Player p) throws NotSoddisfedPrerequisite, LeaderActivated {


        if(active) throw new LeaderActivated("");

        boolean resourceRequisite = true;
        boolean cardRequisite = true;

        //Check resource prerequisite if necessary

        if( this.getCost() != null ) {
            if(!this.getCost().isEmpty()) {

                resourceRequisite = ResourceOperator.compare(p.getDashboard().getAllAvailableResource(), this.getCost());
            }
        }
        //Check card prerequisite if necessary
        if(this.cardPrequisite !=null && !this.cardPrequisite.isEmpty())
            cardRequisite     = p.getDashboard().checkCardPresence(cardPrequisite);


        boolean out = resourceRequisite && cardRequisite;
        if(out || DebugMessages.leaderFree)
        {
            p.increaseScore(this.getVictoryPoints());
            this.active = true;
        }
        else
        {
            throw  new NotSoddisfedPrerequisite("");
        }
    }

    public String getHeader()
    {
        return ConstantValues.resourceRappresentation.getColorRappresentation(this.type) + CliColors.BLACK_TEXT + this.cliRappresentation;
    }

    public List<PrerequisiteCard> getCardPrequisite()
    {
        return this.cardPrequisite;
    }

    public void setCliRappresentation(String cliRappresentation)
    {
        this.cliRappresentation = cliRappresentation;
    }

    public int getPadding()
    {
        return this.cliRappresentation.length();
    }

    public boolean isActive()
    {
        return this.active;
    }

    public Packet updateMiniModel(Player p,int index)
    {
        return new ACK(0);
    }

    public String getCliRappresentation() {
        return cliRappresentation;
    }
}
