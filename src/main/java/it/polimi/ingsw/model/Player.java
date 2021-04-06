package it.polimi.ingsw.model;


import it.polimi.ingsw.enumeration.ResourceType;
import org.jetbrains.annotations.NotNull;

public class Player {

    private String nickname;
    private boolean connectionState;
    private LeaderCard[] leaders;
    private Dashboard dashboard;
    private ResourceList pendingCost;
    private int position;
    private int score;



    public Player(String nickname,LeaderCard[] drawedCards)
    {
        this.dashboard = new Dashboard();
        this.nickname = nickname;
    }
    public Player(){
        dashboard = new Dashboard();
        nickname = "Test";
    }

    public String getNickname()
    {
        return this.nickname;
    }

    /**
     * Increment player position by 1
     */
    public void incrementPosition()
    {
        this.position++;
    }

    /**
     * True if Online
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
     *  Adding resourcing
     * @param r resource
     * @param p position
     */
    public void addResource(@NotNull Resource r, int p){
        dashboard.storageInsertion(new Resource(r.getType(),r.getQuantity()),p);
    }

    public Dashboard getDashboard() {
        return this.dashboard;
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
    private int getCardsScore() {
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

    /**
     *  Add resource to chest
     * @param resource resource to add
     */
    public void chestInsertion(Resource resource)
    {
        this.dashboard.chestInsertion(resource);
    }

    /**
     * Insert resources in storage
     * @param resource resource to put in storage
     * @param position deposit to select
     */
    public void storageInsertion(Resource resource,int position)
    {
        this.dashboard.storageInsertion(resource,position);
    }

    /**
     * Apply a cost to the storage
     * @param resource resource to pay
     * @param position deposit to select
     */
    public void payStorageResource(Resource resource,int position) {
        this.dashboard.applyStorageCosts(resource,position);
    }

    /**
     *
     * @param resource resource to pay
     */
    public void payChestResource(Resource resource)
    {
        this.dashboard.applyChestCosts(resource);
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

    /**
     * Choose leader from 4 card
     * @param l leader card
     * @param discard1 first leader to discard
     * @param discard2 second leader to discard
     */
    public void chooseLeader(LeaderCard l[],int discard1,int discard2){
        int pos=0;
        for(int i=0; i<4; i++)
            if((i!=discard1)&&(i!=discard2)){
                leaders[pos]=l[i];
                pos++;
            }

    }


}
