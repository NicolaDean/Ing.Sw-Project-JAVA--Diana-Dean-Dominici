package it.polimi.ingsw.model.minimodel;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.ProductionCard;
import it.polimi.ingsw.model.dashboard.Deposit;
import it.polimi.ingsw.model.dashboard.Storage;
import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.minimodel.MiniPlayer;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.utils.ConstantValues;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MiniModel
{

    private int persanalIndex;

    private MiniPlayer[] players;

    private ProductionCard [][] decks;
    private Deposit[]           storage;

    private ProductionCard[] playerCards;

    public MiniModel()
    {
        players = new MiniPlayer[ConstantValues.numberOfPlayer];
        this.storage=new Deposit[0];
    }



    public void updateCard(ProductionCard newCard,int x,int y,int dashboardPos)
    {
        playerCards[dashboardPos] = decks[x][y];
        decks[x][y] = newCard;
        System.out.println("Dash updated");
    }
    public void setDeck(Stack<ProductionCard>[][] decks)
    {
        //this.decks = new ProductionCard[ConstantValues.colDeck][ConstantValues.rowDeck];

        this.decks = new ProductionCard[3][4];
        int i=0;
        for(Stack<ProductionCard>row[] :decks)
        {
            int j=0;
            for(Stack<ProductionCard> cards:row)
            {
                this.decks[i][j] = cards.peek();
                j++;
            }
            i++;
        }

    }
    public void addPlayer(String nickname, int index)
    {
        this.players[index] = new MiniPlayer(nickname);
    }

    public void removePlayer(String nickname,int index) { }

    public MiniPlayer[] getPlayers() { return players; }

    public void updateStorage(Deposit[] deposits)
    {
        this.storage = deposits;
    }

    public Deposit[] getStorage()
    {
        return this.storage;
    }

    public ProductionCard[][] getDecks()
    {
        return this.decks;
    }

    public int getPersanalIndex() { return persanalIndex; }

    public ProductionCard[] getPlayerCards() { return this.playerCards; }
}
