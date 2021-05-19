package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.interpreters.JsonInterpreterClient;
import it.polimi.ingsw.controller.packets.*;
import it.polimi.ingsw.controller.pingManager.PongController;
import it.polimi.ingsw.exceptions.WrongPosition;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.ProductionCard;
import it.polimi.ingsw.model.dashboard.Deposit;
import it.polimi.ingsw.model.market.balls.BasicBall;
import it.polimi.ingsw.model.minimodel.MiniModel;
import it.polimi.ingsw.model.minimodel.MiniPlayer;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceList;
import it.polimi.ingsw.utils.ConstantValues;
import it.polimi.ingsw.view.GUI;
import it.polimi.ingsw.utils.DebugMessages;
import it.polimi.ingsw.view.utils.ErrorManager;
import it.polimi.ingsw.view.CLI;
import it.polimi.ingsw.view.View;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.function.Consumer;

import static it.polimi.ingsw.utils.ConstantValues.marketCol;
import static it.polimi.ingsw.utils.ConstantValues.marketRow;

public class ClientController implements Runnable{

    private Socket                server;
    private Scanner               input;
    private PrintWriter           output;
    private JsonInterpreterClient interpreter;


    private PongController        pongController;
    private int                   index;
    private boolean               connected;

    private ErrorManager          errorManager;
    private View                  view;   //Interface with all view methods
    private MiniModel             model;
    private AckExample            resolver;



    public ClientController(boolean type)
    {
        this.connected = false;
        if(type)view = new CLI();
        else view = new GUI();//GUI()

        this.view.setObserver(this);

        this.interpreter= new JsonInterpreterClient(this);
        this.errorManager = new ErrorManager();
        this.resolver = new AckExample();
        model= new MiniModel(); //provvisiorio

    }

    /*
     * set all initial information into miniMarted
     */
    public void setInformation(int index,MiniPlayer[] players, Stack<ProductionCard>[][] productionDecks, BasicBall[][] miniBallsMarket, BasicBall miniBallDiscarted){
        view.setMarket(miniBallsMarket,miniBallDiscarted);
        this.model.setDeck(productionDecks);
        this.model.setPlayers(players);
        this.model.setPersanalIndex(index);

        DebugMessages.printWarning("INDEX : " + index);

        int i=0;
        for(MiniPlayer p: this.model.getPlayers())
        {
            DebugMessages.printWarning(p.getNickname() + " -> "+ i);
            i++;
        }

    }

    public View getView() {
        return view;
    }

    public void setAckManagmentAction(Consumer <View> action)
    {
        this.resolver.setAction(action);
    }

    public MiniModel getMiniModel()
    {
        return this.model;
    }

    /**
     *
     * @param newCard new card (under the buyed one)
     * @param x col
     * @param y row
     * @param dashboardPos where i set the old card in my dashboard
     */
    public void updateCardBuyed(ProductionCard newCard,int x,int y,int dashboardPos,int index)
    {
        this.model.updateCard(newCard,x,y,dashboardPos,index);
    }
    /**
     * Add new logged player to the minimodel
     * @param index
     * @param nickname
     */
    public void notifyPlayer(int index, String nickname)
    {
        this.view.playerLogged(nickname);
    }

    public ClientController() {

    }

    public void printGameStarted()
    {
        this.view.showGameStarted();
    }

    public void exampleACK(int code)
    {
        errorManager.getErrorMessageFromCode(code);//TODO magari oltre al numero passo la view che chiamera "showError"
    }


    public void setConnected(boolean conn)
    {
        this.connected = conn;
    }
    public void setIndex(int index)
    {
        if(!connected)
        {
            this.index = index;
            this.setConnected(true);
            this.pongController = new PongController(index,output);
            new Thread(this.pongController);
        }
    }

    public void starttolisten(){

        Thread t = new Thread(this);
        DebugMessages.printNetwork("\nmi metto in ascolto \n");
        t.start();
    }

    public boolean isConnected() {
        return connected;
    }

    public PongController getPongController() {
        return pongController;
    }

    public void sendBuyCard(int x,int y,int pos)
    {
        this.sendMessage(new BuyCard(x,y,pos));
    }

    public void sendProduction(int pos)
    {
        this.sendMessage(new Production(pos));
    }

    public void showAvailableNickname()
    {
        int i=0;
        for(MiniPlayer p:this.model.getPlayers())
        {
            System.out.println((i+1 )+ "- "+p.getNickname());
            i++;
        }
    }
    public void spyPlayer(int index)
    {
        MiniPlayer p = this.model.getPlayers()[index];
        this.view.showPlayer(p.getStorage(),p.getChest(),p.getDecks(),p.getNickname());
    }
    public void abortHelp()
    {
        this.view.abortHelp();
    }
    public void startGame()
    {

        //0System.out.println("Start Game");
        view.printWelcomeScreen();
        view.askServerData();
        view.askNickname();
    }


    public void sendStartCommand()
    {
        this.sendMessage(new StartGame());
    }

    public void printHelp()
    {
        view.askCommand();
    }

    /**
     * Open a connection with this server
     * @param ip server ip
     * @param port server port
     */
    public void connectToServer(String ip,int port) {
        try {
            this.interpreter = new JsonInterpreterClient(this);
            this.server = new Socket(ip,port);
            initializeReader(server);
            initializeWriter(server);
            new Thread(this);//create input messages manager thread
            setConnected(true);
            this.pongController = new PongController(index, output);

        } catch (IOException e) {
            setConnected(false);
            view.askServerData("Connection failed, try insert new server data:");
        }
    }

    /**
     * Initialize the Input stream of the socket
     * @param s
     */
    public void initializeReader(Socket s)
    {
        try {
            this.input  = new Scanner(s.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initialize the Output stream of the socket
     * @param s
     */
    public void initializeWriter(Socket s)
    {
        try {
            this.output = new PrintWriter(s.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Set his own nickname to minimodel
     * @param nickname his nickname
     * @param singlePlayer if hes singleplayer
     */
    public void setNickname(String nickname,boolean singlePlayer)
    {
        if(singlePlayer)
            sendMessage(new LoginSinglePlayer(nickname));
        else
            sendMessage(new Login(nickname));
    }

    public void sendResourceDiscard(int quantity)
    {
        this.sendMessage(new DiscardResource(quantity));
    }

    public void storageUpdate(Deposit[] deposits,int index)
    {
        this.model.updateStorage(deposits,index);
    }

    public void showStorage()
    {
        this.view.showStorage(this.model.getStorage());
    }

    public void showDashboard(){
        this.view.showDashboard(
                this.model.getStorage(),
                this.model.getPlayers()[this.model.getPersanalIndex()].getChest(),
                this.model.getPlayerCards()
        );
    }
    /**
     * send packet to server
     * @param p
     */
    public void sendMessage(Packet p)
    {
        this.output.println(p.generateJson()); ;   //(p.generateJson());
        this.output.flush();
    }
    /**
     * wait server messages
     */
    public void waitMessage()
    {
        String message = this.input.nextLine();

        Thread t = new Thread(()->{

            try
            {
                this.interpreter.analyzePacket(message);
                this.respondToClient();
                DebugMessages.printNetwork("Recived command:" + message);
            }catch (Exception e)
            {
                e.printStackTrace();
                DebugMessages.printError("Not a json Message: "+ message);
            }

        });
        t.start();
    }

    /**
     * //TODO Obsolete function, client almost never respond to server message immediatly but use directly "sendMessage"
     * Send respond to server
     */
    public void respondToClient()
    {
        try {
            String response = interpreter.getResponse();
            if(response!=null)
            {
                output.println(response);
                output.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showMarketResult(List<Resource>res,int whiteBalls)
    {
        this.view.showMarketExtraction(res,whiteBalls);
    }

    public void showDecks()
    {
        view.showDecks(this.model.getDecks());
    }

    public void excecuteTurn()
    {
        this.view.askTurnType();
    }

    public void askEndTurn(){
        this.view.askEndTurn();
    }

    public void askBuy(){this.view.askBuy();}

    public void askResourceExtraction(List<Resource> resourceList)
    {
        this.view.askResourceExtraction(resourceList);
    }

    public void sendResourceInsertion(List<InsertionInstruction> instructions)
    {
        this.sendMessage(new StorageMassInsertion(instructions));
    }
    public void sendResourceExtraction(boolean buyturn,List<ExtractionInstruction> instructions)
    {
        this.sendMessage(new StorageMassExtraction( buyturn,instructions));
    }
    /**
     * Send a packet of market exrtrction
     * @param dir col = true row = false
     * @param pos col,row to select
     */
    public void sendMarketExtraction(boolean dir,int pos)
    {
        if(dir) exstractColumn(pos);
        else exstractRow(pos);
        this.sendMessage(new MarketExtraction(dir,pos));
    }

    /**
     *extraction row from minimarker
     * @param pos row position, it must be between 1 and 3
     */
    public void exstractRow(int pos)  {
        BasicBall tmp;
        for (int i = 1; i < ConstantValues.marketCol; i++) {
            tmp = view.getMiniMarketBalls()[pos][i];
            view.getMiniMarketBalls()[pos][i] = view.getMiniMarketBalls()[pos][0];
            view.getMiniMarketBalls()[pos][0] = tmp;
        }

        tmp = view.getMiniMarketDiscardedResouce();
        view.setMiniMarketDiscardedResouce(view.getMiniMarketBalls()[pos][0]);
        view.getMiniMarketBalls()[pos][0] = tmp;
    }

    /**
     *extraction column fror minimarket
     * @param pos column position, it must be between 1 and 4
     */
    public void exstractColumn(int pos) {
        BasicBall tmp;
        for (int i = 1; i < marketRow; i++) {
            tmp = view.getMiniMarketBalls()[i][pos];
            view.getMiniMarketBalls()[i][pos] = view.getMiniMarketBalls()[0][pos];
            view.getMiniMarketBalls()[0][pos] = tmp;
        }
        tmp = view.getMiniMarketDiscardedResouce();
        view.setMiniMarketDiscardedResouce( view.getMiniMarketBalls()[0][pos]);
        view.getMiniMarketBalls()[0][pos] = tmp;

    }

    /**
     * This thread listen to the server packet and analyze them
     */
    @Override
    public void run() {
        //Thread con server
        DebugMessages.printNetwork("Waiting message thread chreated");
        while(this.connected)
        {
            this.waitMessage();
        }
    }
}
