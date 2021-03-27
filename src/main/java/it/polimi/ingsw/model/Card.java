package it.polimi.ingsw.model;
import java.util.ArrayList;
import java.util.List;

public class Card {

    private List<Resource> cost = new ArrayList<Resource>();
    private int victoryPoints;

    public Card(List<Resource> cost, int victoryPoints) {
        this.cost = cost;
        this.victoryPoints = victoryPoints;
    }

    /**
     * Method to get the card's VictoryPoints
     * @return returns the victory points
     */
    public int getScore() {int Score = this.victoryPoints; return  Score;}

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

}
