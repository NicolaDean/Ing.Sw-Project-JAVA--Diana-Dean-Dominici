package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.ProductionCard;
import it.polimi.ingsw.model.market.Market;

import java.util.List;
import java.util.Stack;

public class Game {
    List<Player> players;
    LeaderCard[] leaders;
    Stack<ProductionCard>[][] productionDecks;
    Market market;
    CellScore[] scorePositions;
    PapalSpace [] papalSpaces;
    int currentPapalSpaceToReach;
    int calamaio;
    int currentPlayer;

    public Game()
    {

    }

    //public boolean addPlayer(String nickname)
    //
}
