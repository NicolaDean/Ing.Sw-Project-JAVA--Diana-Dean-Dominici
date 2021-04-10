package it.polimi.ingsw.model.cards;

import com.google.gson.JsonObject;
import it.polimi.ingsw.enumeration.CardType;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.dashboard.Dashboard;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceList;
import it.polimi.ingsw.model.resources.ResourceOperator;

import java.util.List;

public class ProductionCard extends Card {

    private CardType type;
    private int level;
    private int obtainedFaith;
    private List<Resource> rawMaterials;
    private List<Resource> obtainedMaterials;


    //Empty Constructor for GSON
    public ProductionCard(List<Resource> cost,List<Resource> raw,List<Resource>obt,int victoryPoints,int level,int obtainedFaith,CardType type)
    {
        super(cost,victoryPoints);
        this.level = level;
        this.rawMaterials =  raw;
        this.obtainedMaterials =  obt;
        this.type = type;
        this.obtainedFaith = obtainedFaith;
    }

    public ProductionCard(List<Resource> cost, int victoryPoints,int level) {
        super(cost, victoryPoints);
        this.level = level;
    }

    public ProductionCard(List<Resource> cost,List<Resource> raw,List<Resource>obt,int victoryPoints,int level)
    {
        super(cost,victoryPoints);
        this.level = level;
        this.rawMaterials =  raw;
        this.obtainedMaterials =  obt;
    }



    public int getLevel() {
        return level;
    }

    public CardType getType() {
        return type;
    }

    /**
     *
     * @param card card to compare
     * @return true if type is equals
     */
    public boolean compareType(ProductionCard card)
    {
        return this.getType() == card.getType();
    }

    /**
     *
     * @param card type to compare
     * @return true if type is equals
     */
    public boolean compareType(PrerequisiteCard card)
    {
        boolean out = false;


        //If level is not negative check level
        if(level != -1)
        {
            if(card.getLevel() != this.level) return false;
        }

        //If level is ok or not necessary check Type
        return this.getType() == card.getType();

    }

    @Override
    public boolean checkCost(Dashboard dash) {
        List<Resource> availableRes = ResourceOperator.merge(dash.getDiscount(),dash.getAllAvailableResource());

        boolean out = ResourceOperator.compare(availableRes,this.getCost());

        return out;
    }


    /**
     * Player select A production card trough the view and this model method will be called to activate
     * THIS FUNCTION DOSNT REMOVE COST RESOURCE (it will be done by controller)
     * @param p Dashboard of the player
     * @return  true if the activation goes well
     */
    public boolean produce(Player p) throws Exception {

        //Check if necesary resources are availabe
        List<Resource> resAvailable = p.getDashboard().getAllAvailableResource();
        boolean out = ResourceOperator.compare(resAvailable,this.rawMaterials);

        //if true add obtained resources to the chest
        if(out)
        {
            p.incrementPosition(this.obtainedFaith);
            p.chestInsertion(this.obtainedMaterials);
        }
        else
        {
            throw  new Exception("Not enough money to produce");
        }


        return out;
    }


    /**
     *
     * @param p the player that buys the card
     * @param pos positioning of the card inside the dashboard
     * @return true if all goes in the right way
     */
    public void buy(Player p, int pos) throws Exception {
        //First Buy the card then ask player where chose resource in the controller
        boolean out = this.checkCost(p.getDashboard());

        if(out)
        {
            try
            {
                out = p.getDashboard().setProcuctionCard(this,pos);
                if (out)
                    p.increaseScore(this.getVictoryPoints());

            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("Wrong card Position");
            }


        }else
        {
            throw new Exception("Not enouth money");
        }

    }

    /**
     * get the card price with the applied discount
     * @param dash the player dashboard
     * @return the cost of the card discounted(full price instead)
     */

    public List<Resource> getCost(Dashboard dash)
    {
        List<Resource> cost = super.getCost();

        if(dash.getDiscount() != null)
        {
            for(Resource scont : dash.getDiscount())
            {
                cost.remove(scont);
            }
        }
        return cost;
    }

}
