package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.enumeration.ResourceType;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.ProductionCard;
import it.polimi.ingsw.model.cards.leaders.BonusProductionInterface;
import it.polimi.ingsw.model.lorenzo.token.ActionToken;
import it.polimi.ingsw.model.market.balls.BasicBall;
import it.polimi.ingsw.model.dashboard.Deposit;
import it.polimi.ingsw.model.minimodel.MiniPlayer;
import it.polimi.ingsw.model.resources.Resource;

import java.util.List;

public interface View {

    /**
     * print end screen with charts
     */
    public void printEndScreen(String []charts,int []score);

    /**
     * print end screen for lorenzo game
     */
    public void printEndScreenLorenzo(Boolean lorenzoWin);

    public void printWelcomeScreen();

    public void setMiniMarketDiscardedResouce(BasicBall miniMarketBalls);

    /**
     * show market's client
     * @param
     */
    public void showMarket();

    /**
     * set mini market
     * @param balls balls
     * @param discarted ball discarted
     */
    public void setMarket(BasicBall[][] balls, BasicBall discarted);

    public void showMessage(String msg);
    /**
     * getter for mini market
     * @return balls
     */
    public BasicBall[][] getMiniMarketBalls();

    /**
     * getter for mini ball discarted
     * @return
     */
    public BasicBall getMiniMarketDiscardedResouce();

    public void showError(String error);
    /**
     * ask user a valid nickname
     */
    public void askNickname();

    /**
     * ask ip and port then notify controller to try connecting to the server
     */
    public void askServerData();

    /**
     * show an error message and then
     * ask ip and port then notify controller to try connecting to the server
     * @param errore
     */
    public void askServerData(String errore);

    //TURN TYPE METHODS

    /**
     * ask user what card he want to buy
     */
    public void askBuy();

    /**
     * ask user what production card he want to asctivate
     */
    public void askProduction();

    /**
     * ask the user what bonus producton card he want activate
     */
    public void askBonusProduction(BonusProductionInterface[] bonus);


    public void showDiscount(List<Resource> resourceList);
    /**
     * ask user what kind of basic production he want
     */
    public void askBasicProduction();

    /**
     * ask user which line/row he want to select for the market extraction
     */
    public void askMarketExtraction() throws InterruptedException;

    public void showDecks(ProductionCard[][] ProductionCards);
    /**
     * ask user which resouce extracted from market he want to discard
     * @param resource recived resources
     */
    public Resource askDiscardResource(Resource resource);

    //Cost and gain of resource

    /**
     * ask user where to put resources
     * @param resourceList ask user where to put recived resources (eventualy call discard resources)
     */
    public void askResourceInsertion(List<Resource> resourceList);

    /**
     * show papal cell
     * @param p mini player
     */
    public void showPapalCell(MiniPlayer[] p,int lorenzo);

    /**
     * ask user where to exract resources to pay a production/buy...
     * @param resourceList
     */
    public void askResourceExtraction(List<Resource> resourceList);//Pending cost

    //Deposit action

    /**
     * ask user which deposit he want to swap
     */
    public void askSwapDeposit(int index);

    //Commands

    /**
     * ask user which kind of turn he want to do
     */
    public void askTurnType();

    public void showPlayer(Deposit[]deposits, List<Resource> chest, ProductionCard[] cards,LeaderCard[] leaderCards,String name) ;
    /**
     * ask user which command he want to execute
     */
    public void askCommand();

    /**
     * Ask user which of the 4 initial leaders he want (he can chose 2 of them)
     * @param cards 4 leaders drawed
     */
    public void askLeaders(LeaderCard[] cards);

    /**
     * ask user which leader he want to activate
     */
    public void askLeaderActivation();

    /**
     * ask user wich leader he want to discard
     */
    public void askDiscardLeader();

    public List<Resource> askWhiteBalls(ResourceType[] resourceTypes,int num) ;
    /**
     * Show user his initial resources (called only the first turn)
     * he can chose "number" quantity of resource of his choice
     * @param number number of resources to select
     */
    public void askInitialResoruce(int number);

    /**
     * display "Game started" message
     */
    public void showGameStarted();

    /**
     * Function of cli that abort help command wait loop when notifyturn is recived
     * (Avoid input blocking thread)
     * */
    public void abortHelp();

    /**
     * Show result of extraction from market
     * @param resourceList list of resources gained
     * @param whiteballs   number of white ball extracted (only if user has 2 white leader)
     */
    public void showMarketExtraction(List<Resource> resourceList, int whiteballs, ResourceType[] types);

    /**
     * show storage and chest
     * @param deposits storage deposits
     * @param chest    chest
     */
    public void showStorage(Deposit[] deposits,List<Resource> chest);

    /**
     * show user dashboard
     * @param deposits      Deposit of this user
     * @param chest         chest of this user
     * @param cards         production cards
     * @param leaderCards   leaders he own
     */
    public void showDashboard(Deposit[] deposits, List<Resource> chest, ProductionCard[] cards,LeaderCard[] leaderCards);

    /**
     * ask user if he want end turn or continue with his turn type
     */
    public void askEndTurn();

    /**
     * ask player if he want to move resources froma  deposit to another
     */
    public void askMoveResources();

    /**
     * If connection fail
     */
    public void connectionfailed();


    /**
     * notify user that a new user logged the game
     * @param nickname nickname of new player
     */
    public void playerLogged(String nickname);



    /**
     * Set a controller as observer of this view
     * @param controller client controller
     */
    public void setObserver(ClientController controller);


    void setStarted();

    /**
     * show lorenzo actions
     * @param cliColor color of actionToken
     * @param token    action token used by lorenzo
     */
    void lorenzoTurn(String cliColor, String token);

    void serverDisconnected();
}
