package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.ProductionCard;
import it.polimi.ingsw.model.dashboard.Deposit;
import it.polimi.ingsw.model.dashboard.Storage;
import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.market.balls.BasicBall;
import it.polimi.ingsw.utils.ConstantValues;

import static it.polimi.ingsw.utils.ConstantValues.marketCol;
import static it.polimi.ingsw.utils.ConstantValues.marketRow;

public class MiniModel
{
    private String[] nicknames;
    private int[]    positions;

    private ProductionCard [][] deks;
    private Deposit[]           storage;
    private Market market;

    private ProductionCard[] playerCards;

    public MiniModel()
    {
        this.nicknames = new String[ConstantValues.numberOfPlayer];
        this.positions = new int   [ConstantValues.numberOfPlayer];
        this.storage=new Deposit[0];

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

}
