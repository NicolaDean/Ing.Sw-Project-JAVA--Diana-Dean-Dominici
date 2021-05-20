package it.polimi.ingsw.model.minimodel;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.ProductionCard;
import it.polimi.ingsw.model.dashboard.Deposit;
import it.polimi.ingsw.model.dashboard.Storage;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceList;

import java.util.List;

public class MiniPlayer {
    private List<Resource>      chest;
    private LeaderCard []       leaderCards;
    private ProductionCard[]    decks;
    private int                 position;
    private String              nickname;
    private Deposit[]           storage;


    public MiniPlayer(String nickname) {
        this.position = 0;
        this.nickname = nickname;
        this.chest = new ResourceList();
        decks = new ProductionCard[3];
    }

    public void incrementPosition(int position) {
        this.position += position;
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

    public void setStorage(Deposit[] storage) {
        this.storage = storage;
    }

    public ProductionCard[] getCards()
    {
        return this.decks;
    }

    public void setLeaderCards(LeaderCard[] leaderCards) {
        this.leaderCards = leaderCards;
    }
    public LeaderCard[] getLeaderCards()
    {
        return this.leaderCards;
    }

    public void updateChest(List<Resource> chest)
    {
        this.chest = chest;
    }
}
