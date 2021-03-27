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
     * @param dash Dashboard of the player
     * @return  true id
     */
    public boolean activate(Dashboard dash)
    {
        //Il metodo activate controlla tramite getAvailableRes se ha le risorse e poi la attiva a prescindere
        //Nel mentre notifica il controller del magazzino che sono state
        // estratte X risorse e che chieda all'utente da dove estrarle

        List<Resource> resAvailable = dash.getAllAvailableResource();

        boolean out = ResourceOperator.compare(resAvailable,this.rawMaterials);

        if(out)
        {
            //NOTIFY SPENDED and OBTAINED RESOURCE TO DASHBOARD
            //(es: notifyProductionExecuted(
            // (la dash si occupera di chiedere dove estrare le spese
            // e sucessivamente aggiungere le ottenute al froziere
            //La dashboard avrà anche l'opzione annulla in caso l'utente ci ripensi

            //In questo modo il codice diventa asincrono e non interattivo
        }

        return out;
    }

    public boolean buy(Dashboard dash,int pos)
    {
        boolean out = this.checkCost(dash);

        //Notify Dashboard asking for position  (es notify(this)) instead of having pos parameter

        //
        dash.setProcuctionCard(this,pos);
        return out;
    }

}
