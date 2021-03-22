package it.polimi.ingsw.model;
import java.util.ArrayList;
import java.util.List;

public class Card {

    private List<Resource> Cost = new ArrayList<Resource>();
    private int VictoryPoints;

    public Card(List<Resource> cost, int victoryPoints) {
        Cost = cost;
        VictoryPoints = victoryPoints;
    }

    /**
     * Method to get the card's VictoryPoints
     * @return returns the victory points
     */
    public int getScore() {int Score = this.VictoryPoints; return  Score;}

    /**
     * Methods that tells if you have enough resources to buy the card
     * @param dash Dashboard pointer
     * @return
     */
    public boolean checkCost(Dashboard dash) {
        List<Resource> resAvailable = dash.getAllAvailableResource();

        boolean out = ResourceOperator.compare(resAvailable,this.Cost);
        return out;
    }

}
