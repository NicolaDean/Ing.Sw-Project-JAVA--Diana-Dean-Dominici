package it.polimi.ingsw.model.minimodel;

import it.polimi.ingsw.model.cards.ProductionCard;
import it.polimi.ingsw.model.dashboard.Deposit;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.utils.ConstantValues;
import it.polimi.ingsw.utils.DebugMessages;
import it.polimi.ingsw.view.observer.Observable;
import it.polimi.ingsw.view.scenes.BasicSceneUpdater;

import java.util.List;
import java.util.Stack;

public class MiniModel extends Observable<BasicSceneUpdater>
{

    private int persanalIndex = 0;
    private boolean isLoaded = false;
    private MiniPlayer[] players;

    private ProductionCard [][] decks;

    public MiniModel()
    {
        players = new MiniPlayer[ConstantValues.numberOfPlayer];
    }


    public MiniPlayer getPersonalPlayer()
    {
        return this.players[persanalIndex];
    }
    public void updateCard(ProductionCard newCard,int x,int y,int dashboardPos,int index)
    {
        players[index].getDecks()[dashboardPos] = decks[x][y];
        decks[x][y] = newCard;
        System.out.println("Dash updated");

        this.notifyObserver(scene -> scene.updateDeckCard(newCard,x,y));
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
        DebugMessages.printError("set finisched loading");
        isLoaded = true;
    }

    public boolean isLoaded()
    {
        return isLoaded;
    }
    public void setModelObserver(BasicSceneUpdater currScene)
    {
        for(MiniPlayer p:players)
        {
            if(p!=null)
                p.setObserver(currScene);
        }

        this.setObserver(currScene);
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
    public List<Resource> getChest()
    {
        return players[persanalIndex].getChest();
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

    public void updateChest(List<Resource> chest,int index)
    {
        this.players[index].updateChest(chest);
    }

}
