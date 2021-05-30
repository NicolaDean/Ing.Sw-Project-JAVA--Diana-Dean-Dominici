package it.polimi.ingsw.model.minimodel;

import it.polimi.ingsw.enumeration.ResourceType;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.ProductionCard;
import it.polimi.ingsw.model.cards.leaders.BonusProductionInterface;
import it.polimi.ingsw.model.dashboard.Deposit;
import it.polimi.ingsw.model.dashboard.Storage;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceList;
import it.polimi.ingsw.view.observer.Observable;
import it.polimi.ingsw.view.scenes.BasicSceneUpdater;

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
    private BonusProductionInterface[]  trade;

    public MiniPlayer(String nickname) {
        this.position = 0;
        this.nickname = nickname;
        this.chest = new ResourceList();
        decks = new ProductionCard[3];
    }
    public void setIndex(int index)
    {
        this.index = index;
    }

    public void incrementPosition(int position) {
        this.position += position;
        this.notifyObserver(scene -> scene.updatePlayerPosition(index,position));
    }

    public void setDecks(ProductionCard[] decks) {
        this.decks = decks;
    }

    public ProductionCard[] getDecks() {
        return decks;
    }

    public List<Resource> getChest() {
        return chest;
    }

    public int getPosition() {
        return position;
    }

    public String getNickname() {
        return nickname;
    }

    public Deposit[] getStorage() {
        return storage;
    }

    public ProductionCard[] getPlayerCards()
    {
        return this.decks;
    }
    public void setStorage(Deposit[] storage) {
        this.storage = storage;
        this.notifyObserver(scene -> scene.updateStorage(index,storage));
    }

    public ProductionCard[] getCards()
    {
        return this.decks;
    }

    public void setLeaderCards(LeaderCard[] leaderCards) {
        this.leaderCards = leaderCards;

        this.notifyObserver(scene-> scene.updateLeaders(index,leaderCards));
    }

    public LeaderCard[] getLeaderCards()
    {
        return this.leaderCards;
    }

    public void updateChest(List<Resource> chest)
    {
        this.chest = chest;
        this.notifyObserver(scene -> scene.updateChest(index,chest));
    }

    public void setDiscount(List<Resource> discount)
    {
        this.discount = discount;
    }

    public void setTrade(BonusProductionInterface[] bonusProductionInterfaces)
    {
        this.trade = bonusProductionInterfaces;
    }
    public void setWhiteBalls(ResourceType[] resourceTypes)
    {
        this.whiteBalls = resourceTypes;
    }
    public List<Resource> getDiscount()
    {
        return this.discount;
    }
    public ResourceType[] getWhiteBalls()
    {
        return this.whiteBalls;
    }

    public BonusProductionInterface[] getTrade()
    {
        return this.trade;
    }

}
