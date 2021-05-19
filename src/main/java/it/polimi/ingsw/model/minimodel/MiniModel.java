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

    public MiniModel()
    {
        players = new MiniPlayer[ConstantValues.numberOfPlayer];
    }



    public void updateCard(ProductionCard newCard,int x,int y,int dashboardPos,int index)
    {
        players[index].getDecks()[dashboardPos] = decks[x][y];
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

    public void setPlayers(MiniPlayer[] players) { this.players = players; }

    public MiniPlayer[] getPlayers() { return players; }

    public void updateStorage(Deposit[] deposits,int index)
    {
        players[index].setStorage(deposits);
    }

    public Deposit[] getStorage()
    {
        return players[persanalIndex].getStorage();
    }

    public ProductionCard[][] getDecks()
    {
        return this.decks;
    }

    public int getPersanalIndex() { return persanalIndex; }

    public ProductionCard[] getPlayerCards() { return this.players[persanalIndex].getDecks(); }

    public void setPersanalIndex(int index)
    {
        this.persanalIndex = index;
    }

}
