package it.polimi.ingsw.model;

import java.util.List;
import java.util.Stack;

public class Game {
    List<Player> Players;
    LeaderCard [] Leaders;
    Stack<ProductionCard>[][] ProductionDecks;
    Market Market;
    CellScore[] ScorePositions;
    PapalSpace [] PapalSpaces;
    int CurrentPapalSpaceToReach;
    int Calamaio;
    int CurrentPlayer;

    public Game()
    {

    }
}
