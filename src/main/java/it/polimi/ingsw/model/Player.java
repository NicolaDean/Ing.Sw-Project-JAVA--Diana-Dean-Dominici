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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Player extends Observable<ServerController> implements Serializable {

    private String nickname;
    private boolean connectionState;
    private LeaderCard[] leaders;
    private Dashboard dashboard;
    private int position;
    private int score = 0;
    private List<LeaderTradeCard> bonusProductions;
    private boolean inkwell;
    private ArrayList<ResourceType> bonusball;
    private boolean[] surpassedcells;
    private int lastadded = 0;
    private int controllerIndex=0;

    transient private UpdateCardBuyed pendingCard = null;



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


    /**
     *
     * @return the list of owned leaders of this player
     */
    public LeaderCard[] getLeaders()
    {
        return this.leaders;
    }

    /**
     * when user buy a card (and hasnt already payed yet) befor send cardUpdate wait for him to pay
     * @param newCard     new card in shop
     * @param x           shop x coordinate
     * @param y           shop y coordinate
     * @param dashPos     dash positioning of this player
     * @param playerIndex index player who buy card (this player)
     */
    public void setPendingBuy(ProductionCard newCard,int x,int y,int dashPos,int playerIndex)
    {
        this.pendingCard = new UpdateCardBuyed(newCard,x,y,dashPos,playerIndex);
    }

    /**
     * change connection state of this player
     * @param state true if connected
     */
    public void setConnectionState(boolean state)
    {
        this.connectionState = state;
    }

    /**
     *  this function is called when a card is buyed and finaly payer
     * @return a packet with card buyed but waiting to be payd
     */
    public Packet getPendingCard()
    {
        return this.pendingCard;
    }
    //PENDING BUYED CARD


    /**
     * set new surpassed cell
     * @param lastadded added
     */
    public void setLastadded(int lastadded) {
        this.lastadded = lastadded;
    }

    /**
     *
     * @return last cell surpassed
     */
    public int getLastadded() {
        return lastadded;
    }

    /**
     *
     * @return all cell of faith track surpassed
     */
    public boolean[] getSurpassedcells() {
        return surpassedcells;
    }

    /**
     *
     * @return final score
     */
    public int getScore() {
        return score;
    }
    /**
     *
     * @return possible reources choice user can do when own 2 white ball leader
     */
    public ArrayList<ResourceType> getBonusball() {
        return bonusball;
    }

    /**
     * first player
     */
    public void setInkwell() {
        this.inkwell = true;
    }

    /**
     * set leader chosen by user
     * @param leaders leader chosen
     */
    public void setLeaders(LeaderCard[] leaders) {
        this.leaders = leaders;
    }

    /**
     *  select the 2 leader chosen from user during first turn
     * @param pos1 first leader selected
     * @param pos2 second leader selected
     */
    public void setLeaders(int pos1,int pos2)
    {
        LeaderCard[] cards = new LeaderCard[2];

        cards[0] = this.leaders[pos1];
        cards[1] = this.leaders[pos2];

        this.setLeaders(cards);
    }

    /**
     *
     * @return player nickname
     */
    public String getNickname()
    {
        return this.nickname;
    }

    /**
     *
     * @return the dashboard of this player containing all his cards
     */
    public Dashboard getDashboard() {
        return this.dashboard;
    }

    /**
     *
     * @return the current position of player
     */
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
            this.incrementPosition();
            this.leaders[position] = null;
        }

    }


    /**
     * Activate leader
     * @param position position leader
     * @return true if it's active
     */
    public void activateLeader(int position) throws NotSoddisfedPrerequisite, LeaderActivated {
        if(this.leaders[position] == null) throw new LeaderActivated("");
        this.leaders[position].activate(this);
    }

    /**
     *
     * @param position    position of card inside dash
     * @param playerIndex index to update dashboard minimodel
     * @return a packet that will nonify client that a card is buyed (to update minimodel with new card)
     */
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

    /**
     * increse final score
     * @param n increment value
     */
    public void increaseScore(int n)
    {
        score = score + n;
    }

    /**
     * decrese final score
     * @param n increment value
     */
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
    public void bonusProduction(int pos, ResourceType resWanted) throws WrongPosition, NotEnoughResource, AlreadyUsed {
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

    /**
     *
     * @return the list of bonus productions owned by this player
     */
    public List<LeaderTradeCard> getBonusProduductions()
    {
        return this.bonusProductions;
    }

    /**
     *
     * @param controllerIndex index of clientHandler associated with this player
     */
    public void setControllerIndex(int controllerIndex) {
        this.controllerIndex = controllerIndex;
    }

    /**
     *
     * @return index of clientHandler inside serverController
     */
    public int getControllerIndex()
    {
        return this.controllerIndex;
    }

    /**
     * Reset usage of productions card, trade card, basic production
     */
    public void resetTurn() {

        this.dashboard.resetGain();
        if(bonusProductions==null)return;
        for(BonusProductionInterface prod:bonusProductions)
        {
            prod.reset();
        }
    }
}
