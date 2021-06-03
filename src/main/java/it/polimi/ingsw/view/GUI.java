package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.controller.packets.EndTurn;
import it.polimi.ingsw.enumeration.ResourceType;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.ProductionCard;
import it.polimi.ingsw.model.cards.leaders.BonusProductionInterface;
import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.market.balls.BasicBall;
import it.polimi.ingsw.model.dashboard.Deposit;
import it.polimi.ingsw.model.minimodel.MiniPlayer;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceList;
import it.polimi.ingsw.utils.DebugMessages;
import it.polimi.ingsw.view.observer.Observable;
import it.polimi.ingsw.view.scenes.*;
import it.polimi.ingsw.view.utils.FXMLpaths;
import it.polimi.ingsw.view.utils.Logger;
import it.polimi.ingsw.view.utils.ToastMessage;
import javafx.application.Platform;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.util.List;

public class GUI extends Observable<ClientController> implements View{

    boolean singleplayer;
    private BasicBall[][]   miniMarketBalls;
    private BasicBall       miniMarketDiscardedResouce;
    private boolean         isMarketLoaded;
    boolean                 isMyTurn; //used just for endTurn button and market exstraction

    boolean firstTurn = true;
    Thread gui;


    public GUI()
    {
        gui = new Thread(()->{GuiHelper.main(this);});
        isMarketLoaded = false;
        gui.start();
    }

    public ClientController getObserver(ClientController controller)
    {
        return this.getObserver();
    }

    /**
     * set mini model of market in the view if put balls and discartedBall null update images
     * @param balls balls
     * @param discarted ball discarted
     */
    public void setMarket(BasicBall[][] balls, BasicBall discarted){
        if((balls != null)&&(discarted != null)) {
            miniMarketBalls = balls;
            miniMarketDiscardedResouce = discarted;
            isMarketLoaded=true;
        }else Platform.runLater(() -> { try { GuiHelper.updateMarket(); }catch(Exception e){ e.printStackTrace();}});
    }

    /**
     * wait untill market is setted
     */
    public void waitMarketLoaded(){
        this.notifyObserver(controller -> {
            if(isMarketLoaded) return;

            //Loop until market is loaded
            while(!isMarketLoaded)
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
    public void showMessage(String msg) {
        Platform.runLater(()->GuiHelper.sendMessage(msg));
    }

    /**
     *
     * @return the matrix of balls in the market
     */
    public BasicBall[][] getMiniMarketBalls() {
        waitMarketLoaded();
        return miniMarketBalls;
    }

    /**
     *
     * @return discarded ball
     */
    public BasicBall getMiniMarketDiscardedResouce() {
        waitMarketLoaded();
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
        miniMarketDiscardedResouce=miniMarketBall;
    }


    @Override
    public void showMarket(){
        waitMiniModelLoading();
        Platform.runLater(() -> { try { GuiHelper.setRoot(FXMLpaths.market); }catch(Exception e){ e.printStackTrace();}});
    }


    @Override
    public void showError(String error) {
        DebugMessages.printError(error);
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
        DebugMessages.printError("inserimento");
        Platform.runLater(()->{
            try{ GuiHelper.setRoot(FXMLpaths.dashboard,new DashboardScene(resourceList));}catch (Exception e){
                e.printStackTrace();
            }
        });

    }

    @Override
    public void askResourceExtraction(List<Resource> resourceList) {
        System.out.println("PAYMENT ");
        PaymentDialog dialog = new PaymentDialog(resourceList);
        Platform.runLater(()->{
            GuiHelper.loadDialog(FXMLpaths.payment,"Payment",dialog);
        });
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
            Platform.runLater(()->this.notifyObserver(ClientController::askLeaders));;         //SHOW DIALOG with leaders
            Platform.runLater(()->this.notifyObserver(ClientController::askInitialResoruce));  //SHOW DIALOG WHIT initial resources
            this.notifyObserver(ClientController::showDashboard);
            firstTurn = false;
        }
        else
        {
            //SHOW DASHBOARD BASE
        }

        //TODO find a way to detect automaticly the turn type
        //LOAD DASHBOARD SCENE WITH "turnSelectionController" when user do concrete action controller swith
        // to a specific controller that allow him to do only some actions
        //askBuy();
        //showMarket();
        this.notifyObserver(ClientController::showDashboard);

    }

    @Override
    public void showPlayer(Deposit[]deposits, List<Resource> chest, ProductionCard[] cards,LeaderCard[] leaderCards,String name) {
        //Spy scene
    }

    @Override
    public void askCommand() {
        //trurn chosing scene
        //TODO da riumovere da qui perchè andra dove fede mette il pulzante
        //waitMiniModelLoading();
        //showMarket();
        waitMiniModelLoading();
        this.notifyObserver(ClientController::showDashboard);
    }

    @Override
    public void askLeaders(LeaderCard[] cards) {

        DialogLeader dialog = new DialogLeader(cards);


        //Continue until user press ok (if exit windows in other way (eg clicking X) it will recall dialog
        ButtonType result = ButtonType.CANCEL;
        do {
            result = GuiHelper.loadDialog(FXMLpaths.askLeaders,"Choose 2 of those leaders" ,dialog);
        }while (result.equals(ButtonType.CANCEL));

        int out[] = dialog.getLeaders();

        //Send to server Leader setting
        this.notifyObserver(controller -> controller.sendLeader(out[0],out[1]));

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
        //Aggiunge a pending gain
        return dialog.getResources();
    }

    @Override
    public void askInitialResoruce(int number) {

        if(number == 0) return;

        InitialResources dialog = new InitialResources(number);

        Platform.runLater(()->{

            ButtonType result = ButtonType.CANCEL;
            do {
                result = GuiHelper.loadDialog(FXMLpaths.initialResource,"Chose " + number + "of those resources",dialog);
            }while (result.equals(ButtonType.CANCEL));

            this.askResourceInsertion(dialog.getResources());
        });



    }

    @Override
    public void showGameStarted() {
        this.askCommand();
        Platform.runLater(()-> GuiHelper.sendMessage("Game Started"));
    }

    @Override
    public void abortHelp() {

    }


    @Override
    public void showMarketExtraction(List<Resource> resourceList, int whiteballs, ResourceType[] types) {

        List<Resource> out  = new ResourceList();
        out.addAll(resourceList);

        Platform.runLater(()->
        {
            //if whiteballs is >0 ask user how he want to convert them
            if(whiteballs>0)
            {
                    out.addAll(this.askWhiteBalls(types,whiteballs));
            }
            this.askResourceInsertion(out);
        });

    }

    @Override
    public void showStorage(Deposit[] deposits,List<Resource>chest) {

    }



    @Override
    public void showDashboard(Deposit[] deposits, List<Resource> chest, ProductionCard[] cards,LeaderCard[] leaderCards) {

        waitMiniModelLoading();

        Platform.runLater(()->{
            try {
                GuiHelper.setRoot(FXMLpaths.dashboard,new DashboardScene());
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }

    @Override
    public void askEndTurn() {
        this.notifyObserver(clientController -> isMyTurn=clientController.isMyTurn());

        if(isMyTurn) {
            Platform.runLater(() -> {
                if (GuiHelper.YesNoDialog("End TURN", "Do you want to end turn?")) {
                    this.notifyObserver(ClientController::sendDashReset);
                    this.notifyObserver(controller -> controller.sendMessage(new EndTurn()));
                    this.notifyObserver(clientController -> clientController.setMyTurn(false));
                    try {
                        GuiHelper.setRoot(FXMLpaths.dashboard,new DashboardScene());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{}
                try {
                    GuiHelper.setRoot(FXMLpaths.dashboard,new DashboardScene());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }


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
