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

    private ProductionCard[] playerCards;

    private BasicBall[][] miniMarketBalls=new BasicBall[marketRow][marketCol];
    private BasicBall miniDiscardedResouce;

    public void setMarket(BasicBall[][] balls, BasicBall discarted){
        miniMarketBalls=balls;
        miniDiscardedResouce=discarted;
        this.storage = new Deposit[0];
    }

    public MiniModel()
    {
        this.nicknames = new String[ConstantValues.numberOfPlayer];
        this.positions = new int   [ConstantValues.numberOfPlayer];
    }


    public void addPlayer(String nickname,int index)
    {
        this.nicknames[index] = nickname;
        this.positions[index] = 0;
    }

    public void removePlayer(String nickname,int index)
    {

    }

    public BasicBall[][] getMiniMarketBalls() {
        return miniMarketBalls;
    }

    public BasicBall getMiniDiscardedResouce() {
        return miniDiscardedResouce;
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
