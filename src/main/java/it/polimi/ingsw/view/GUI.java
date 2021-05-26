package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.enumeration.ResourceType;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.ProductionCard;
import it.polimi.ingsw.model.cards.leaders.BonusProductionInterface;
import it.polimi.ingsw.model.market.balls.BasicBall;
import it.polimi.ingsw.model.dashboard.Deposit;
import it.polimi.ingsw.model.minimodel.MiniPlayer;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.view.events.DashboardScene;
import it.polimi.ingsw.view.observer.Observable;
import it.polimi.ingsw.view.utils.FXMLpaths;
import javafx.application.Platform;

import java.io.IOException;
import java.util.List;

public class GUI extends Observable<ClientController> implements View{

    boolean singleplayer;
    private BasicBall[][]   miniMarketBalls;
    private BasicBall       miniMarketDiscardedResouce;

    Thread gui;
    public GUI()
    {
        gui = new Thread(()->{GuiHelper.main(this);});
        gui.start();
    }

    public ClientController getObserver(ClientController controller)
    {
        return this.getObserver();
    }

    /**
     * set mini model of market in the view
     * @param balls balls
     * @param discarted ball discarted
     */
    public void setMarket(BasicBall[][] balls, BasicBall discarted){
        miniMarketBalls=balls;
        miniMarketDiscardedResouce=discarted;
    }

    /**
     *
     * @return the matrix of balls in the market
     */
    public BasicBall[][] getMiniMarketBalls() {
        return miniMarketBalls;
    }

    /**
     *
     * @return discarded ball
     */
    public BasicBall getMiniMarketDiscardedResouce() {
        return miniMarketDiscardedResouce;
    }

    @Override
    public void printWelcomeScreen() {

    }

    public void setSingleplayer() {
        this.singleplayer = true;
    }
    public boolean isSingleplayer()
    {
        return this.singleplayer;
    }

    @Override
    public void showPapalCell(MiniPlayer[] p) {

    }

    @Override
    public void setMiniMarketDiscardedResouce(BasicBall miniMarketBall){

    }


    @Override
    public void showMarket(){
        waitMiniModelLoading();
        Platform.runLater(() -> { try { GuiHelper.setRoot(FXMLpaths.market); }catch(Exception e){ e.printStackTrace();}});
    }


    @Override
    public void showError(String error) {
        Platform.runLater(()->{GuiHelper.sendError(error);});
    }

    @Override
    public void askNickname() {
        try {
            GuiHelper.setRoot(FXMLpaths.askNickname);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void askServerData() {
        try {
            GuiHelper.setRoot(FXMLpaths.askServerData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void askServerData(String errore) {

    }

    @Override
    public void askBuy() {

        //wait until model is loaded
        waitMiniModelLoading();

        Platform.runLater(()->{
            try {
                //GuiHelper.setRoot(FXMLpaths.buy);
                GuiHelper.setRoot("dashboard",new DashboardScene());
            } catch (IOException e) {
                e.printStackTrace();
            }

        });


        //this.notifyObserver(ClientController::showDecks);
    }

    /**
     * To avoid scene to try acces minimodel before its loading is finisched
     * this function allow us to wait until then to load the scene (this problem occure only during first turn)
     */
    public void waitMiniModelLoading()
    {
        this.notifyObserver(controller -> {
            if(controller.getMiniModel().isLoaded()) return;

            //Loop until model is loaded
            while(!controller.getMiniModel().isLoaded())
            {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    @Override
    public void askProduction() {
        try {
            GuiHelper.setRoot("production");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void askBonusProduction(BonusProductionInterface[] bonus) {

    }

    @Override
    public void showDiscount(List<Resource> resourceList) {

    }

    @Override
    public void askBasicProduction() {

    }

    @Override
    public void askMarketExtraction() {
        //TODO send to market scene
    }

    @Override
    public void showDecks(ProductionCard[][] productionCards) {

    }

    @Override
    public Resource askDiscardResource(Resource resource) {
        return null;
    }

    @Override
    public void askResourceInsertion(List<Resource> resourceList) {

        //TODO change controller of dashbard view to insetion controller
        //TODO add dinamicly controller update to GUIHELPER
    }

    @Override
    public void askResourceExtraction(List<Resource> resourceList) {

    }

    @Override
    public void askSwapDeposit(int index) {

    }

    @Override
    public void askTurnType()
    {
        showMarket();
        //askBuy();
    }

    @Override
    public void showPlayer(Deposit[]deposits, List<Resource> chest, ProductionCard[] cards,LeaderCard[] leaderCards,String name) {
        //Spy scene
    }

    @Override
    public void askCommand() {
        //trurn chosing scene
    }

    @Override
    public void askLeaders(LeaderCard[] cards) {

    }

    @Override
    public void askLeaderActivation() {

    }

    @Override
    public void askDiscardLeader() {

    }

    @Override
    public List<Resource> askWhiteBalls(ResourceType[] resourceTypes,int num)  {

        //Display a message showing the 2 resource type between he can chose
        return null;
    }

    @Override
    public void askInitialResoruce(int number) {

    }

    @Override
    public void showGameStarted() {
        //TODO show a "TOAST" message with "game started"
        this.askCommand();
    }

    @Override
    public void abortHelp() {

    }


    @Override
    public void showMarketExtraction(List<Resource> resourceList, int whiteballs, ResourceType[] types) {
        
    }

    @Override
    public void showStorage(Deposit[] deposits,List<Resource>chest) {

    }



    @Override
    public void showDashboard(Deposit[] deposits, List<Resource> chest, ProductionCard[] cards,LeaderCard[] leaderCards) {

    }

    @Override
    public void askEndTurn() {

    }



    @Override
    public void askMoveResources() {

    }

    @Override
    public void connectionfailed() {

    }

    @Override
    public void playerLogged(String nickname) {
        Platform.runLater(()->GuiHelper.sendMessage(nickname));
    }
}
