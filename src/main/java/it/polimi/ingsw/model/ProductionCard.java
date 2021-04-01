package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.CardType;

import java.util.List;

public class ProductionCard extends Card{

    private CardType type;
    private int level;
    private List<Resource> rawMaterials;
    private List<Resource> obtainedMaterials;

    public ProductionCard(List<Resource> cost, int victoryPoints,int level) {
        super(cost, victoryPoints);
        this.level = level;
    }

    public ProductionCard(List<Resource> cost,List<Resource> raw,List<Resource>obt,int victoryPoints,int level)
    {
        super(cost,victoryPoints);
        this.level = level;
        this.rawMaterials = raw;
        this.obtainedMaterials = obt;

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


    @Override
    public boolean checkCost(Dashboard dash) {
        List<Resource> availableRes = ResourceOperator.merge(dash.getDiscount(),dash.getAllAvailableResource());

        boolean out = ResourceOperator.compare(availableRes,this.getCost());

        return out;
    }


    /**
     * Player select A production card trough the view and this model method will be called to activate
     * THIS FUNCTION DOSNT REMOVE COST RESOURCE (it will be done by controller)
     * @param dash Dashboard of the player
     * @return  true if the activation goes well
     */
    public boolean produce(Dashboard dash)
    {

        //Check if necesary resources are availabe
        List<Resource> resAvailable = dash.getAllAvailableResource();
        boolean out = ResourceOperator.compare(resAvailable,this.rawMaterials);

        //if true add obtained resources to the chest
        if(out)
        {
            dash.chestInsertion(this.obtainedMaterials);
        }

        return out;
    }


    /**
     *
     * @param dash the player dashboard
     * @param pos positioning of the card inside the dashboard
     * @return true if all goes in the right way
     */
    public boolean buy(Dashboard dash, int pos)
    {
        //First Buy the card then ask player where chose resource in the controller
        boolean out = this.checkCost(dash);

        if(out)
        {
            dash.setProcuctionCard(this,pos);
        }

        return out;
    }

}
