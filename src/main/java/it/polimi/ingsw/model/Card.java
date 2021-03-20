package it.polimi.ingsw.model;
import java.util.ArrayList;

public class Card {

    private ArrayList<Resource> Cost = new ArrayList<Resource>();
    private int VictoryPoints;

    public Card(ArrayList<Resource> cost, int victoryPoints) {
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
     * @param db
     * @return
     */
    public boolean checkCost(Dashboard db) {boolean Outcome = false; return Outcome;}

}
