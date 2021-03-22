package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.CardType;

import java.util.ArrayList;
import java.util.List;

public class ProductionCard extends Card{

    private CardType Type;
    private int Level;
    private List<Resource> RawMaterials;
    private List<Resource> ObtainedMaterials;

    public ProductionCard(ArrayList<Resource> cost, int victoryPoints) {
        super(cost, victoryPoints);
    }

    public CardType getType() {
        return Type;
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

        boolean out = ResourceOperator.compare(resAvailable,this.RawMaterials);

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

    public boolean buy(Dashboard dash)
    {
        boolean out = this.checkCost(dash);

        //Pusha la carta nella dashboard
        return out;
    }

}
