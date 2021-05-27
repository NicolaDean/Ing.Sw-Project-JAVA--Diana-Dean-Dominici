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
import it.polimi.ingsw.model.resources.ResourceList;
import it.polimi.ingsw.view.observer.Observable;
import it.polimi.ingsw.view.scenes.DialogLeader;
import it.polimi.ingsw.view.scenes.InitialResources;
import it.polimi.ingsw.view.utils.FXMLpaths;
import javafx.application.Platform;

import java.io.IOException;
import java.util.List;

public class GUI extends Observable<ClientController> implements View{

    boolean singleplayer;
    boolean firstTurn = true;
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
    public BasicBall[][] getMiniMarketBalls() {
        return null;
    }

    @Override
    public BasicBall getMiniMarketDiscardedResouce() {
        return null;
    }

    @Override
    public void showMarket(){

    }

    @Override
    public void setMarket(BasicBall[][] balls, BasicBall discarted) {

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
                GuiHelper.setRoot(FXMLpaths.buy);
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

        //show resource obtained in a dialog with only OK button
        //
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
        waitMiniModelLoading();

        if(firstTurn)
        {
            this.notifyObserver(ClientController::askLeaders);          //SHOW DIALOG with leaders
            this.notifyObserver(ClientController::askInitialResoruce);  //SHOW DIALOG WHIT initial resources
        }
        else
        {
            //SHOW DASHBOARD BASE
        }

        //TODO find a way to detect automaticly the turn type
        //LOAD DASHBOARD SCENE WITH "turnSelectionController" when user do concrete action controller swith
        // to a specific controller that allow him to do only some actions
        askBuy();
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

        DialogLeader dialog = new DialogLeader(cards);

        Platform.runLater(()->{GuiHelper.loadDialog(FXMLpaths.askLeaders,"Choose 2 of those leaders" ,dialog);});

        while(!dialog.isReady())
        {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //getResult
    }

    @Override
    public void askLeaderActivation() {
            //si puo usare per mostrare un dialog "do you want to activate,discard this leader? ACTIVE DISCARD CANCEL"
    }

    @Override
    public void askDiscardLeader() {

    }

    /**
     * show a dialog showing white balls convertion options and Drag & drop selection
     * @param resourceTypes  possible convertions
     * @param num            numbers of resources to choose
     * @return a list of resources corresponding to balls convertion
     */
    @Override
    public List<Resource> askWhiteBalls(ResourceType[] resourceTypes,int num)  {

        InitialResources dialog = new InitialResources(resourceTypes,num);
        GuiHelper.loadDialog(FXMLpaths.initialResource,"Chose " + num + "of those resources",dialog);
        return dialog.getResources();
    }

    @Override
    public void askInitialResoruce(int number) {

        if(number == 0) return;

        InitialResources dialog = new InitialResources(number);

        Platform.runLater(()->{
            GuiHelper.loadDialog(FXMLpaths.initialResource,"Chose " + number + "of those resources",dialog);
        });

        while(!dialog.isReady())
        {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        this.askResourceInsertion(dialog.getResources());
    }

    @Override
    public void showGameStarted() {
        GuiHelper.sendMessage("Game Started");
        this.askCommand();
    }

    @Override
    public void abortHelp() {

    }


    @Override
    public void showMarketExtraction(List<Resource> resourceList, int whiteballs, ResourceType[] types) {

        List<Resource> out  = new ResourceList();
        out.addAll(resourceList);

        //if whiteballs is >0 ask user how he want to convert them
        if(whiteballs>0)
        {
            out.addAll(this.askWhiteBalls(types,whiteballs));
        }

        this.askResourceInsertion(out);
    }

    @Override
    public void showStorage(Deposit[] deposits,List<Resource>chest) {

    }



    @Override
    public void showDashboard(Deposit[] deposits, List<Resource> chest, ProductionCard[] cards,LeaderCard[] leaderCards) {

    }

    @Override
    public void askEndTurn() {
        //Dialog "Do you want to end turn? YES NO
    }



    @Override
    public void askMoveResources() {

    }

    @Override
    public void connectionfailed() {
        GuiHelper.sendError("Connection failed, Try again");
    }

    @Override
    public void playerLogged(String nickname) {
        Platform.runLater(()->GuiHelper.sendMessage(nickname));
    }
}
