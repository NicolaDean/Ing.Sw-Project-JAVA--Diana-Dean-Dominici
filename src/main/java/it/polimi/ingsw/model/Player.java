package it.polimi.ingsw.model;


import it.polimi.ingsw.enumeration.ResourceType;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.dashboard.Dashboard;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceList;

import java.util.List;

public class Player {

    private String nickname;
    private boolean connectionState;
    private LeaderCard[] leaders;
    private Dashboard dashboard;
    private int position;
    private int score;



    public Player(String nickname,LeaderCard[] drawedCards)
    {
        this.dashboard = new Dashboard();
        this.nickname = nickname;
    }

    public Player() {
        this.dashboard = new Dashboard();
    }

    public String getNickname()
    {
        return this.nickname;
    }
    /**
     * increment player position by 1
     */
    public void incrementPosition()
    {
        this.position++;
    }

    /**
     * true if Online
     * false if Offline
     * @return
     */
    public boolean checkConnection()
    {
        return this.connectionState;
    }

    public int getPosition() {
        return this.position;
    }

    /**
     *  adding resourcing
     * @param r resource
     * @param p position
     */
    public void addResource(Resource r, int p){
        dashboard.storageInsertion(new Resource(r.getType(),r.getQuantity()),p);
    }

    public Dashboard getDashboard() {
        return this.dashboard;
    }

    /**
     * Cominica la fine del turno alla classe game
     */
    public void endTurn(){
        //Notify Game with event
    }

    /**
     * Calculate the score from the Production Cards, Player position and ...
     * @return
     */
    private int getDashboardScore()
    {
        return this.dashboard.getScore();
    }

    /**
     * Calculate the score from the Leader Victory Points
     * @return
     */
    private int getCardsScore()
    {
        int vp=0;
        for(LeaderCard Leader : leaders)
        {
            vp+=Leader.getScore();
        }
        return vp;
    }

    /**
     * Return the total score of the player by summing score from the map and cards
     * @return
     */
    public int getPlayerScore()
    {
        return this.getCardsScore() + getCardsScore();
    }

    /**
     * Delete Leader from the Leader List of the player
     * @param position
     */
    public void discardLeader(int position)
    {
        this.leaders[position] = null;
    }

    public void activateLeader(int position){ }

    /**
     *  Add resource to chest
     * @param resource resource to add
     */
    public void chestInsertion(Resource resource)
    {
        this.dashboard.chestInsertion(resource);
    }

    /**
     *  insert resources in storage
     * @param resource resource to put in storage
     * @param position deposit to select
     */
    public void storageInsertion(Resource resource,int position)
    {
        this.dashboard.storageInsertion(resource,position);
    }

    /**Remove the resourced payed from the pendingCost and remove resources from storage
     * apply a cost to the storage
     * @param resource resource to pay
     * @param position deposit to select
     */
    public void payStorageResource(Resource resource,int position)
    {

        this.dashboard.applyStorageCosts(resource,position);
    }

    /**
     * Remove the resourced payed from the pendingCost and remove resources from chest
     * @param resource resource to pay
     */
    public void payChestResource(Resource resource)
    {
        this.dashboard.applyChestCosts(resource);
    }

    /**
     * Remove a list of resources from the chest ( and remove them also from pendingCost)
     * @param resource
     */
    public void payChestResource(List<Resource> resource)
    {
        this.dashboard.applyChestCosts(resource);
    }

    /**
     * get the debits of this player
     * @return the list of resources this player need to pay (for a production or a buyed card)
     */
    public List<Resource> getPendingCost()
    {
        return this.dashboard.getPendingCost();
    }
    /**
     *
     * @param res Add a permanent discount to the player Dashboard
     */
    public void addDiscount(Resource res)
    {
        this.dashboard.setDiscount( res);
    }

    public void addDepositBonus(ResourceType typeBonus)
    {
        this.dashboard.addDepositBonus(typeBonus);
    }
}
