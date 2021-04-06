package it.polimi.ingsw.model.cards;
import it.polimi.ingsw.model.dashboard.Dashboard;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceList;
import it.polimi.ingsw.model.resources.ResourceOperator;

import java.util.List;

public class Card {

    private List<Resource> cost = new ResourceList();
    private int victoryPoints;

    public Card()
    {
        this.cost = new ResourceList();
    }
    public Card(List<Resource> cost, int victoryPoints) {
        this.cost = cost;
        this.victoryPoints = victoryPoints;
    }

    /**
     * Method to get the card's VictoryPoints
     * @return returns the victory points
     */
    public int getScore() {int Score = this.victoryPoints; return  Score;}

    public List<Resource> getCost()
    {
        return this.cost;
    }
    /**
     * Methods that tells if you have enough resources to buy the card
     * @param dash Dashboard pointer
     * @return
     */
    public boolean checkCost(Dashboard dash) {
        List<Resource> resAvailable = dash.getAllAvailableResource();

        boolean out = ResourceOperator.compare(resAvailable,this.cost);
        return out;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }
}

//if(resourceRequisite && cardRequisite)
//            p.increaseScore(this.getVictoryPoints());