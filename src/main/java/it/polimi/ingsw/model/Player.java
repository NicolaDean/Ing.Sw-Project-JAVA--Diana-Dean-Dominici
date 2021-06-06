package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.controller.packets.Packet;
import it.polimi.ingsw.controller.packets.UpdateCardBuyed;
import it.polimi.ingsw.enumeration.ResourceType;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.ProductionCard;
import it.polimi.ingsw.model.cards.leaders.BonusProductionInterface;
import it.polimi.ingsw.model.cards.leaders.LeaderTradeCard;
import it.polimi.ingsw.model.dashboard.Dashboard;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.utils.ConstantValues;
import it.polimi.ingsw.view.observer.Observable;

import java.util.ArrayList;
import java.util.List;

public class Player extends Observable<ServerController> {

    private String nickname;
    private boolean connectionState;
    private LeaderCard[] leaders;
    private Dashboard dashboard;
    private int position;
    private int score = 0;
    private List<LeaderTradeCard> bonusProductions;
    private boolean inkwell;
    private ArrayList<ResourceType> bonusball;
    private int positionLeaderActive;
    private boolean[] surpassedcells;
    private int lastadded = 0;
    private int controllerIndex=0;

    private UpdateCardBuyed pendingCard = null;



    public Player(String nickname, int nofcells)
    {

        surpassedcells = new boolean[nofcells];
        for (boolean a:surpassedcells) {
            a=false;
        }
        this.dashboard = new Dashboard();
        this.dashboard = new Dashboard();
        this.nickname = nickname;
        this.bonusProductions =null;
        bonusball = new ArrayList<>();
        connectionState = true;
    }

    public void resetPendingBuy()
    {
        this.pendingCard = null;
    }

    public LeaderCard[] getLeaders()
    {
        return this.leaders;
    }
    public void setPendingBuy(ProductionCard newCard,int x,int y,int dashPos,int playerIndex)
    {
        this.pendingCard = new UpdateCardBuyed(newCard,x,y,dashPos,playerIndex);
    }

    public void setConnectionState(boolean state)
    {
        this.connectionState = state;
    }
    public Packet getPendingCard()
    {
        return this.pendingCard;
    }
    //PENDING BUYED CARD

    public int getPositionLeaderActive() {
        return positionLeaderActive;
    }

    public void setLastadded(int lastadded) {
        this.lastadded = lastadded;
    }

    public int getLastadded() {
        return lastadded;
    }

    public boolean[] getSurpassedcells() {
        return surpassedcells;
    }

    public int getScore() {
        return score;
    }

    public Player(String nickname)
    {
        this.dashboard = new Dashboard();
        this.nickname = nickname;
        this.bonusProductions =null;
        bonusball = new ArrayList<>();
    }

    public Player(){
        this.dashboard = new Dashboard();
        nickname = "Test";
    }

    public ArrayList<ResourceType> getBonusball() {
        return bonusball;
    }

    public void setInkwell() {
        this.inkwell = true;
    }

    public void setLeaders(LeaderCard[] leaders) {
        this.leaders = leaders;
    }

    public void setLeaders(int pos1,int pos2)
    {
        LeaderCard[] cards = new LeaderCard[2];

        cards[0] = this.leaders[pos1];
        cards[1] = this.leaders[pos2];

        this.setLeaders(cards);
    }

    public String getNickname()
    {
        return this.nickname;
    }

    public Dashboard getDashboard() {
        return this.dashboard;
    }

    public int getPosition() {
        return this.position;
    }

    /**
     * Add Basic ball
     * @param b ball to adding
     */
    public void addBonusball(ResourceType b){
        bonusball.add(b);
    }


    /**
     * Increment player position by 1
     */
    public void incrementPosition()
    {
        this.position++;
        this.notifyObserver(controller -> {controller.sendPositionUpdate(this.position,controllerIndex);});
        try { this.notifyObserver(ServerController::checkPapalSpaceActivation); }catch(Exception e){ }
    }

    /**
     * Increment position by the number in input
     * @param numberOfCell cell to increse
     */
    public void incrementPosition(int numberOfCell)
    {
        this.position+=numberOfCell;
        this.notifyObserver(controller -> {controller.sendPositionUpdate(this.position,controllerIndex);});

        try { this.notifyObserver(ServerController::checkPapalSpaceActivation); }catch(Exception e){ }

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
    public void discardLeader(int position) throws LeaderActivated {
        if(this.leaders[position].isActive()|| this.leaders[position] == null)
        {
            throw new LeaderActivated("");
        }
        else
        {
            this.leaders[position] = null;
        }

    }


    /**
     * Activate leader
     * @param position position leader
     * @return true if it's active
     */
    public void activateLeader(int position) throws NotSoddisfedPrerequisite, LeaderActivated {
        positionLeaderActive = position;
        if(this.leaders[position] == null) throw new LeaderActivated("");
        this.leaders[position].activate(this);
    }

    public Packet getLeaderCardUpdate(int position,int playerIndex)
    {
        LeaderCard update =  this.leaders[position];
        return update.updateMiniModel(this,playerIndex);
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
     *  Add resource to chest
     * @param resource resource to add
     */
    public void chestInsertion(List<Resource> resource)
    {
        this.dashboard.chestInsertion(resource);
    }

    /**
     * Insert resources in storage
     * @param resource resource to put in storage
     * @param position deposit to select
     */
    public void storageInsertion(Resource resource,int position) throws FullDepositException, NoBonusDepositOwned, WrongPosition {
        this.dashboard.storageInsertion(resource,position);

    }

    /**Remove the resourced payed from the pendingCost and remove resources from storage
     * Apply a cost to the storage
     * @param resource resource to pay
     * @param position deposit to select
     */
    public void payStorageResource(Resource resource,int position) throws EmptyDeposit, WrongPosition {
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

    /**
     * Choose leader from 4 card
     * @param l leader card
     * @param discard1 first leader to discard
     * @param discard2 second leader to discard
     */
    public void chooseLeader(LeaderCard l[],int discard1,int discard2){
        int pos=0;
        for(int i = 0; i< ConstantValues.leaderCardsToDraw; i++)
            if((i!=discard1)&&(i!=discard2)){
                leaders[pos]=l[i];
                pos++;
            }

    }



    /**
     * Add the bonusProduction interface to the player so that he can activate bonus production
     * @param bonus the activated  Leader Card
     */
    public void addTradeBonus(LeaderTradeCard bonus)
    {
        if(this.bonusProductions == null) this.bonusProductions = new ArrayList<>();

        this.bonusProductions.add(bonus);
    }

    public void increaseScore(int n)
    {
        score = score + n;
    }
    public void decreaseScore(int n)
    {
        score = score - n;
    }

    /**
     * produce something through the bonus production interface
     * @param pos the leader card to use (if thers more then 1)
     * @param resWanted the resource i want to obtain
     * @return true if the production is done false is something dosnt gone well
     */
    public void bonusProduction(int pos, ResourceType resWanted) throws WrongPosition, NotEnoughResource {
        if(pos < this.bonusProductions.size())
        {
            BonusProductionInterface card = this.bonusProductions.get(pos);
            card.produce(this,resWanted);

            this.dashboard.setPendingCost(card.getProdCost()); //if goes well add the cost to the pending cost
        }
        else
        {
            throw  new WrongPosition("Not existing bonus card");
        }
    }

    public List<LeaderTradeCard> getBonusProduductions()
    {
        return this.bonusProductions;
    }
    public void setControllerIndex(int controllerIndex) {
        this.controllerIndex = controllerIndex;
    }

    public int getControllerIndex()
    {
        return this.controllerIndex;
    }
}
