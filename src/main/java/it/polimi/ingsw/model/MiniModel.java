package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.ProductionCard;
import it.polimi.ingsw.model.dashboard.Deposit;
import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.utils.ConstantValues;

import java.util.Stack;

public class MiniModel
{
    private String[] nicknames;
    private int[]    positions;

    private ProductionCard [][] decks;
    private Deposit[]           storage;

    private ProductionCard[] playerCards;

    public MiniModel()
    {
        this.nicknames = new String[ConstantValues.numberOfPlayer];
        this.positions = new int   [ConstantValues.numberOfPlayer];
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
    public void addPlayer(String nickname,int index)
    {
        this.nicknames[index] = nickname;
        this.positions[index] = 0;
    }

    public void removePlayer(String nickname,int index)
    {

    }


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

}
