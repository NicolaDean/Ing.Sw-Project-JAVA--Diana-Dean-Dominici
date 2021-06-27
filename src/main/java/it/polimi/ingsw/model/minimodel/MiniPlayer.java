package it.polimi.ingsw.model.minimodel;

import it.polimi.ingsw.enumeration.ResourceType;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.ProductionCard;
import it.polimi.ingsw.model.cards.leaders.BonusProductionInterface;
import it.polimi.ingsw.model.cards.leaders.LeaderTradeCard;
import it.polimi.ingsw.model.dashboard.Deposit;
import it.polimi.ingsw.model.dashboard.Storage;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceList;
import it.polimi.ingsw.view.observer.Observable;
import it.polimi.ingsw.view.scenes.BasicSceneUpdater;
import javafx.application.Platform;

import java.util.List;

public class MiniPlayer extends Observable<BasicSceneUpdater> {

    private int index;
    private List<Resource>              chest;
    private LeaderCard[]                leaderCards;
    private ProductionCard[]            decks;
    private int                         position;
    private String                      nickname;
    private Deposit[]                   storage;
    private List<Resource>              discount;
    private ResourceType []             whiteBalls;
    private boolean []                  papalToken;
    private LeaderTradeCard[]  trade;

    public MiniPlayer(String nickname) {
        this.position = 0;
        this.nickname = nickname;
        this.chest = new ResourceList();
        decks = new ProductionCard[3];
        this.papalToken=new boolean[3];
    }

    /**
     * Indicate which player inside game rappresent
     * @param index player index
     */
    public void setIndex(int index)
    {
        this.index = index;
    }

    /**
     * Increment minimodel position of this miniplayer
     * @param position new pos
     */
    public void incrementPosition(int position) {
        this.position = position;
        this.notifyObserver(scene -> scene.updatePlayerPosition(index,this.position));
    }

    /**
     * Update dashboard cards of this miniplayer
     * @param pos   dash position
     * @param card  new card
     */
    public void setNewCard(int pos,ProductionCard card)
    {
        this.decks[pos] = card;
        this.notifyObserver(scene -> scene.updateDashCard(card,pos,index));
    }


    public void initializeDeck(ProductionCard[] decks)
    {
        this.decks = decks;
    }

    /**
     *
     * @return dash deck
     */
    public ProductionCard[] getDecks() {
        return decks;
    }

    /**
     *
     * @return this player chest
     */
    public List<Resource> getChest() {
        return chest;
    }

    /**
     *
     * @return this player position
     */
    public int getPosition() {
        return position;
    }

    /**
     *
     * @return this player nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     *
     * @return this player storage
     */
    public Deposit[] getStorage() {
        return storage;
    }


    /**
     * set storage to this miniplayer, used during miniplayer generation
     * @param storage new storage
     */
    public void setStorage(Deposit[] storage) {
        this.storage = storage;
        //Platform.runLater(()-> this.notifyObserver(scene -> scene.updateStorage(index,storage)));

        this.notifyObserver(scene -> scene.updateStorage(index,storage));
        this.notifyObserver(scene -> scene.updateLeaders(index,leaderCards, getBonusStorage()));
    }


    /**
     *
     * @return 2 deposit bonus of this player
     */
    public Deposit[] getBonusStorage()
    {
        Deposit[] bos = new Deposit[2];
        bos[0] = storage[3];
        bos[1] = storage[4];
        return bos;
    }


    /**
     * set miniplayer leaders card
     * @param leaderCards new leader cards
     */
    public void setLeaderCards(LeaderCard[] leaderCards) {
        this.leaderCards = leaderCards;

        this.notifyObserver(scene-> scene.updateLeaders(index,leaderCards,getBonusStorage()));
    }

    /**
     *
     * @return this miniplayer leaders
     */
    public LeaderCard[] getLeaderCards()
    {
        return this.leaderCards;
    }

    /**
     * update this miniplayer chest with server data
     * @param chest new chest
     */
    public void updateChest(List<Resource> chest)
    {
        this.chest = chest;
        this.notifyObserver(scene -> scene.updateChest(index,chest));
    }

    /**
     * if a discount leader card is activated this function is called (when server say it)
     * @param discount discount owned by this player
     */
    public void setDiscount(List<Resource> discount)
    {
        this.discount = discount;
    }

    /**
     *
     * @param bonusProductionInterfaces
     */
    public void setTrade(LeaderTradeCard[] bonusProductionInterfaces)
    {
        this.trade = bonusProductionInterfaces;
    }

    /**
     * function called on server request when a white leader is activated
     * @param resourceTypes resource associated with white ball
     */
    public void setWhiteBalls(ResourceType[] resourceTypes)
    {
        this.whiteBalls = resourceTypes;
    }

    /**
     *
     * @return the owned discounts list
     */
    public List<Resource> getDiscount()
    {
        return this.discount;
    }

    /**
     *
     * @return all posssible choices this user can do with white balls
     */
    public ResourceType[] getWhiteBalls()
    {
        return this.whiteBalls;
    }

    /**
     *
     * @return array with all trade card owned by this player
     */
    public BonusProductionInterface[] getTrade()
    {
        return this.trade;
    }

    /**
     *
     * @param papalToken player papal spaces
     */
    public void setPapalSpace(boolean[] papalToken) {
        this.papalToken = papalToken;
    }

    /**
     *
     * @return an array of papal space of this player
     */
    public boolean[] getPapalSpaces() {
        return this.papalToken;
    }
}
