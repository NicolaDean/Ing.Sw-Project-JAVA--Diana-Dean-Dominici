package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.model.MiniModel;
import it.polimi.ingsw.model.market.balls.BasicBall;
import it.polimi.ingsw.model.dashboard.Deposit;
import it.polimi.ingsw.model.resources.Resource;

import java.util.List;

public interface View {

    public void printWelcomeScreen();

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

    public void showError();
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
    public void askBonusProduction();

    /**
     * ask user what kind of basic production he want
     */
    public void askBasicProduction();

    /**
     * ask user which line/row he want to select for the market extraction
     */
    public void askMarketExtraction();

    /**
     * ask user which resouce extracted from market he want to discard
     * @param resourceList recived resources
     */
    public Resource askDiscardResource(Resource resource);

    //Cost and gain of resource

    /**
     * ask user where to put resources
     * @param resourceList ask user where to put recived resources (eventualy call discard resources)
     */
    public void askResourceInsertion(List<Resource> resourceList);

    /**
     * ask user where to exract resources to pay a production/buy...
     * @param resourceList
     */
    public void askResourceExtraction(List<Resource> resourceList);//Pending cost

    //Deposit action

    /**
     * ask user which deposit he want to swap
     */
    public void askSwapDeposit();

    //Commands

    /**
     * ask user which kind of turn he want to do
     */
    public void askTurnType();

    /**
     * ask user which command he want to execute
     */
    public void askCommand();

    public void showGameStarted();

    public void abortHelp();
    public void showMarketExtraction(List<Resource> resourceList,int whiteballs);

    public void showStorage(Deposit[] deposits);

    public void askEndTurn();
    //Other methods

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


}
