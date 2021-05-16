package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.ProductionCard;
import it.polimi.ingsw.model.dashboard.Storage;
import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.utils.ConstantValues;

public class MiniModel
{
    private String[] nicknames;
    private int[]    positions;

    private ProductionCard [][] deks;
    private Storage storage;
    private Market market;

    private ProductionCard[] playerCards;



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


}
