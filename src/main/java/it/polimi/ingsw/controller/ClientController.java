package it.polimi.ingsw.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.controller.interpreters.JsonInterpreterClient;
import it.polimi.ingsw.controller.packets.*;
import it.polimi.ingsw.controller.pingManager.PongController;
import it.polimi.ingsw.enumeration.ResourceType;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.ProductionCard;
import it.polimi.ingsw.model.cards.leaders.LeaderTradeCard;
import it.polimi.ingsw.model.dashboard.Deposit;
import it.polimi.ingsw.model.lorenzo.token.ActionToken;
import it.polimi.ingsw.model.market.balls.BasicBall;
import it.polimi.ingsw.model.minimodel.MiniModel;
import it.polimi.ingsw.model.minimodel.MiniPlayer;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.utils.ConstantValues;
import it.polimi.ingsw.view.GUI;
import it.polimi.ingsw.utils.DebugMessages;
import it.polimi.ingsw.view.scenes.BasicSceneUpdater;
import it.polimi.ingsw.view.utils.ErrorManager;
import it.polimi.ingsw.view.CLI;
import it.polimi.ingsw.view.View;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.function.Consumer;

import static it.polimi.ingsw.utils.ConstantValues.*;

public class ClientController implements Runnable{

    private Socket                server;
    private Scanner               input;
    private PrintWriter           output;
    private JsonInterpreterClient interpreter;

    private final Object          lock;

    private PongController        pongController;
    private int                   index;
    private boolean               connected;
    private boolean               isMyTurn;

    private ErrorManager          errorManager;
    private View                  view;   //Interface with all view methods
    private MiniModel             model;
    private AckExample            resolver;

    private List<Integer>         activatedLeaders;



    public ClientController(boolean type)
    {
        this.connected = false;
        if(type)view = new CLI(this.index);
        else view = new GUI();//GUI()
        isMyTurn=false;
        activatedLeaders = new ArrayList<>();

        this.view.setObserver(this);

        this.interpreter= new JsonInterpreterClient(this);
        this.errorManager = new ErrorManager();
        this.resolver = new AckExample();
        model= new MiniModel(); //provvisiorio
        this.lock = new Object();
    }


    public int getIndex() {
        return index;
    }


    public void setActivatedLeaders(int i)
    {
        activatedLeaders.add(i);
    }

    public List<Integer> getActivatedLeaders() {
        return activatedLeaders;
    }


    public void setFirstTurnView(boolean firstTurn)
    {
        if(!firstTurn)
        {
            DebugMessages.printError("aaaaaa");
            this.view.setStarted();
        }

    }

    /**
     * Set information about match inside minimodel, used to reconnect playr
     * @param model              minimodel of this mathc
     * @param miniBallsMarket
     * @param miniBallDiscarted
     * @param firstTurn
     */
    public void setInformation(MiniModel model, BasicBall[][] miniBallsMarket, BasicBall miniBallDiscarted,boolean firstTurn)
    {
        DebugMessages.printError("LOOOOOL");
        this.model = model;
        view.setMarket(miniBallsMarket,miniBallDiscarted);
        this.showDashboard();
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
        DebugMessages.printWarning("Turn order:");
        for(MiniPlayer p: this.model.getPlayers())
        {
            DebugMessages.printWarning(p.getNickname() + " -> "+ i);
            i++;
        }

    }

    public void setMyTurn(boolean myTurn) {
        isMyTurn = myTurn;
    }

    public boolean isMyTurn() {
        return isMyTurn;
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

        lock = new Object();
    }

    /**
     * Print to player that game started
     */
    public void printGameStarted()
    {
        this.view.showGameStarted();
    }

    /**
     * retrive Error messages (string) from and int code inside NACK
     * @param code
     */
    public void exampleACK(int code)
    {
        this.view.showError(errorManager.getErrorMessageFromCode(code));
    }


    /**
     * Set connection to server  status
     * @param conn connection status
     */
    public void setConnected(boolean conn)
    {
        this.connected = conn;
    }

    /**
     * set this controller player index inside server game model
     * @param index player index inside game model
     */
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

    /**
     * Start the Reading/input channel of the socket (wait for packets)
     */
    public void starttolisten(){

        Thread t = new Thread(this);
        DebugMessages.printNetwork("\nmi metto in ascolto \n");
        t.start();
    }

    /**
     *
     * @return true if connected/false if socket failed
     */
    public boolean isConnected() {
        return connected;
    }

    /**
     *
     * @return the pong controller object, used to set ping recived
     */
    public PongController getPongController() {
        return pongController;
    }

    /**
     * Set a gui scene as an observer to the minimodel
     * @param currScene scene to set as observer
     */
    public void addModelObserver(BasicSceneUpdater currScene)
    {
        this.model.setModelObserver(currScene);
    }

    /**
     * send to server a BUYCARD packet
     * @param x     x pos in deck
     * @param y     y pos in deck
     * @param pos   dashboard position
     */
    public void sendBuyCard(int x,int y,int pos)
    {
        this.sendMessage(new BuyCard(x,y,pos));
    }

    /**
     * send to server a PRODUCTION packet
     * @param pos dashboard position to activate
     */
    public void sendProduction(int pos)
    {
        this.sendMessage(new Production(pos));
    }

    /**
     * send to server a BASIC PRODUCTION packet
     * @param res1  resource to pay
     * @param res2  resource to pay
     * @param obt   wanted resource
     */
    public void sendBasicProduction(ResourceType res1, ResourceType res2, ResourceType obt)
    {
        this.sendMessage(new BasicProduction(res1,res2,obt, index));
    }

    /**
     * show Players nicknames (called at first start to show turns order)
     */
    public void showAvailableNickname()
    {
        int i=0;
        for(MiniPlayer p:this.model.getPlayers())
        {
            System.out.println((i+1 )+ "- "+p.getNickname());
            i++;
        }
    }

    /**
     * notify players that game ended (in cli show dashboard of current player, in GUI show a Points Scene)
     * @param charts array with nicknames
     * @param score array with scores
     */
    public void endGame(String []charts,int [] score)
    {
        this.showEndScene(charts,score);
    }

    /**
     * notify player that game ended (
     * @param lorenzoWin true if lorenzo win
     */
    public void endGameLorenzo(Boolean lorenzoWin)
    {
        this.view.printEndScreenLorenzo(lorenzoWin);
    }

    /**
     * Show end game screen
     * @param charts array with nicknames
     * @param score array with scores
     */
    public void showEndScene(String []charts,int []score){ 
        this.view.printEndScreen(charts,score);
    }

    /**
     * call view method to print "index" player dashboard
     * @param index player to spy
     */
    public void spyPlayer(int index)
    {
        MiniPlayer p = this.model.getPlayers()[index];
        this.view.showPlayer(p.getStorage(),p.getChest(),p.getDecks(),p.getLeaderCards(),p.getNickname());
    }

    /**
     * Update leaders inside minimodel
     * @param leaderCard New leaders
     * @param index      player which do the update
     */
    public void updateLeader(LeaderCard [] leaderCard,int index)
    {
        this.model.getPlayers()[index].setLeaderCards(leaderCard);
    }

    /**
     * send to server a Leader Select packet to choose initial leader
     * @param pos1 first leader
     * @param pos2 second leader
     */
    public void sendLeader(int pos1,int pos2)
    {
        this.sendMessage(new SelectLeader(pos1,pos2));
    }

    /**
     * show view method to ask player which initila leaders he want
     */
    public void askLeaders()
    {
        int index = this.model.getPersanalIndex();
        MiniPlayer p = this.model.getPlayers()[index];
        this.view.askLeaders(p.getLeaderCards());
    }


    /**
     * send to server a request to activate leader in pos
     * @param pos leader to activate
     */
    public void activateLeader(int pos)
    {
        this.sendMessage(new ActivateLeader(pos,true));
    }

    /**
     * send to server a request to discard leader in pos
     * @param pos leader to discard
     */
    public void discardLeader(int pos)
    {
        this.sendMessage(new ActivateLeader(pos,false));
    }

    /**
     * method called by CLI when "she" need to abort "waitingOtherTurn" help command loop
     *
     * User are blocked inside an "infinite" help command loop where he can only use commands inside help list
     */
    public void abortHelp()
    {
        this.view.abortHelp();
    }

    /**
     * show "welcome" message to user
     */
    public void startGame()
    {
        view.printWelcomeScreen();
    }


    /**
     * Send to server a request to start the match (if thers 2 or more players inside the game the match will start)
     * */

    public void sendStartCommand()
    {
        this.sendMessage(new StartGame());
    }

    public void printHelp()
    {
        view.askCommand();
    }


    /**
     * create a new socket for this ip/port pair
     * @param ip    ip server
     * @param port  port server
     * @throws IOException
     */
    public void basicConnect(String ip,int port) throws IOException {

        this.interpreter = new JsonInterpreterClient(this);
        this.server = new Socket(ip,port);

        initializeReader(server);
        initializeWriter(server);

        new Thread(this);//create input messages manager thread
        setConnected(true);
        this.pongController = new PongController(index, output);
    }

    /**
     * Open a connection with this server
     * @param ip server ip
     * @param port server port
     */
    public void connectToServer(String ip,int port) {
        try {

            basicConnect(ip,port);
            this.view.askNickname();
        } catch (IOException e) {
            setConnected(false);
            view.connectionfailed();
            return;
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

        this.starttolisten();
    }

    /**
     * send server a deposits swap request
     * @param d1 start deposit
     * @param d2 destination deposit
     */
    public void askSwap (int d1, int d2)
    {
        this.sendMessage(new AskSwap(d1, d2, this.getMiniModel().getPersanalIndex()));

    }

    /**
     * similar to swap but move resources beetween a normal and a bonus deposit of same type
     * @param d1        start deposit
     * @param d2        destination
     * @param quantity  quantity to move
     */
    public void askMove (int d1, int d2, int quantity)
    {
        this.sendMessage(new AskMove(d1, d2, quantity));

    }

    /**
     * send to server a discarded resource notify
     * @param quantity quantity discarded
     * @param type     type discarded (to remove from pendingGain list)
     */
    public void sendResourceDiscard(int quantity,ResourceType type)
    {
        this.sendMessage(new DiscardResource(quantity,type));
    }

    /**
     * change minimodel by updating index player storage
     * @param deposits stprage
     * @param index    player index
     */
    public void storageUpdate(Deposit[] deposits,int index)
    {
        //System.out.println("aggiorno lo storage!");
        this.model.updateStorage(deposits,index);
    }

    /**
     * change minimodel by updating chest of index player
     * @param chest  chest of player
     * @param index  player index
     */
    public void chestUpdate(List<Resource> chest,int index)
    {
        this.model.updateChest(chest,index);
    }

    /**
     * call view method to print storage
     */
    public void showStorage()
    {
        this.view.showStorage(this.model.getStorage(),this.model.getChest());
    }


    /**
     * call view method to show dashboard
     */
    public void showDashboard(){
        MiniPlayer p = this.model.getPersonalPlayer();


        if(p!=null)
        {
            this.view.showPapalCell(this.model.getPlayers(),this.model.getLorenzo());
            this.view.showDashboard(
                    p.getStorage(),
                    p.getChest(),
                    p.getDecks(),
                    p.getLeaderCards()
            );
        }

    }
    /**
     * send packet to server
     * @param p
     */
    public void sendMessage(Packet p)
    {
        synchronized (lock)
        {
            this.output.println(p.generateJson()); ;   //(p.generateJson());
            this.output.flush();
        }
    }

    public void analyze(String message)
    {
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
     * wait server messages
     */
    public void waitMessage()
    {
        try {
            String message = this.input.nextLine();
            analyze(message);
        }catch (Exception e)
        {
            connected = false;
            this.view.serverDisconnected();

        }

    }

    /**
     * increment players position without player index
     * @param index player index
     * @param quantity quantity to add
     */
    public void incrementPositionPlayersWithOut(int index,int quantity){
        /*for(MiniPlayer p:this.model.getPlayers())
            if(!p.equals(this.model.getPlayers()[index]))

            p.incrementPosition(quantity);
         */

            MiniPlayer[] players = this.model.getPlayers();
            for(int i=0; i<players.length;i++)
            {
                if(i != index)  players[i].incrementPosition(quantity);
            }
    }

    /**
     *
     * @param index     player index
     * @param pos       new position
     */
    public void incrementPositionPlayer(int index,int pos){
        this.model.getPlayers()[index].incrementPosition(pos);
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
        ResourceType[] resourceTypes = this.model.getPersonalPlayer().getWhiteBalls();
        this.view.showMarketExtraction(res,whiteBalls,resourceTypes);
    }

    public void showmarket()
    {
        this.view.showMarket();
    }

    public void showshop()
    {
        this.view.askBuy();
    }

    public void askBonusProductions()
    {
        this.view.askBonusProduction(this.model.getPersonalPlayer().getTrade());
    }

    public void showDiscount()
    {
        this.view.showDiscount(this.model.getPersonalPlayer().getDiscount());
    }
    /**
     * update minimodel by adding discount to indedx player
     * @param discount new discount
     * @param index    player index
     */
    public void updateDiscount(List<Resource> discount,int index)
    {
        this.model.getPlayers()[index].setDiscount(discount);
    }

    /**
     * Update minimodel by adding trade bonus to index player
     * @param bonus trade bonus new
     * @param index player index
     */
    public void updateTrade(LeaderTradeCard[] bonus, int index)
    {
        this.model.getPlayers()[index].setTrade(bonus);
    }

    /**
     * update minimodel by change white balls behaviour of a specific player
     * @param whiteBalls different type of resource i can chose for white baals
     * @param index      player index
     */
    public void updateWhiteBalls(ResourceType[] whiteBalls,int index)
    {
        this.model.getPlayers()[index].setWhiteBalls(whiteBalls);
    }

    public void showDecks()
    {
        view.showDecks(this.model.getDecks());
    }

    public void excecuteTurn()
    {
        DebugMessages.printError("TURNO");
        this.view.askTurnType();
    }

    public void askInitialResoruce()
    {

        if(this.model.getPersanalIndex() == 1 || this.model.getPersanalIndex() == 2)
            this.view.askInitialResoruce(1);

        else if(this.model.getPersanalIndex() == 3)
            this.view.askInitialResoruce(2);
        else
            this.view.askInitialResoruce(0);
    }

    public void askEndTurn(){
        this.view.askEndTurn();
    }

    public void askBuy(){
            this.view.askBuy();
    }

    public void askResourceExtraction(List<Resource> resourceList)
    {
        this.view.askResourceExtraction(resourceList);
    }

    public void sendResourceInsertion(List<InsertionInstruction> instructions)
    {
        DebugMessages.printError("Inviato");
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
        this.sendMessage(new MarketExtraction(dir,pos));
    }

    /**
     *extraction row from minimarker
     * @param pos row position, it must be between 1 and 3
     */
    public void exstractRow(int pos)  {
        pos--;
        BasicBall tmp;
        for (int i = 1; i < ConstantValues.marketCol; i++) {
            tmp = view.getMiniMarketBalls()[pos][i];
            view.getMiniMarketBalls()[pos][i] = view.getMiniMarketBalls()[pos][0];
            view.getMiniMarketBalls()[pos][0] = tmp;
        }

        tmp = view.getMiniMarketDiscardedResouce();
        view.setMiniMarketDiscardedResouce(view.getMiniMarketBalls()[pos][0]);
        view.getMiniMarketBalls()[pos][0] = tmp;
        view.setMarket(null,null);
    }

    public void showError(String msg)
    {
        this.view.showError(msg);
    }
    public void showMessage(String msg)
    {
        this.view.showMessage(msg);
    }
    /**
     *extraction column fror minimarket
     * @param pos column position, it must be between 1 and 4
     */
    public void exstractColumn(int pos) {
        pos--;
        BasicBall tmp;
        for (int i = 1; i < marketRow; i++) {
            tmp = view.getMiniMarketBalls()[i][pos];
            view.getMiniMarketBalls()[i][pos] = view.getMiniMarketBalls()[0][pos];
            view.getMiniMarketBalls()[0][pos] = tmp;
        }
        tmp = view.getMiniMarketDiscardedResouce();
        view.setMiniMarketDiscardedResouce( view.getMiniMarketBalls()[0][pos]);
        view.getMiniMarketBalls()[0][pos] = tmp;
        view.setMarket(null,null);
    }

    /**
     * Update lorenzo position inside minimodel
     * @param pos lorenzo position
     */
    public void lorenzoPositionUpdate(int pos)
    {
        this.model.lorenzoPositionUpdate(pos);
    }

    /**
     * Update decks card due to lorenzo discard token
     * @param x         col deck
     * @param y         row deck
     * @param newCard   new card to insert in minimodel
     */
    public void lorenzoCardDiscard(int x,int y,ProductionCard newCard)
    {

        this.model.lorenzoCardDiscard(x,y,newCard);
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

    public void sendDashReset() {
        this.sendMessage(new DashReset());
    }

    /**
     * send to server a bonus production
     * @param res
     * @param type
     */
    public void sendBonusProduction(int res,ResourceType type) {

        this.sendMessage(new BonusProduction(res,type));
    }


    /**
     * save inside a json the necessary information to reconnect to the server
     * @param gameId a unique code used to reconnect to the game (rappresent the serverController to search)
     */
    public void saveReconnectInfo(long gameId) {

        String nickname = this.model.getPersonalPlayer().getNickname();
        String ip       = this.server.getInetAddress().getHostAddress();
        int    port     = this.server.getPort();
        boolean single = this.model.getPlayers().length == 1;
        Reconnect saveInfo = new Reconnect(nickname,ip,port,gameId,single);

        Writer writer = null;
        try {

            writer = new BufferedWriter(new FileWriter("reconnectInfo.json"));
            System.out.println(saveInfo.generateJson());
            writer.write(saveInfo.generateJson());
            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (writer != null) try { writer.close(); } catch (IOException ignore) {}
        }
    }

    /**
     * send to server inside "reconnectInfo.json" a reconnect request
     */
    public void
    reconnect() {

        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader("reconnectInfo.json"));
            JsonParser parser = new JsonParser();

            //Parse packet
            JsonObject json = parser.parse(reader).getAsJsonObject();

            //Extract info
            String      type    = json.get("type").getAsString();
            JsonObject  content = json.get("content").getAsJsonObject();

            Reconnect reconnect = (Reconnect) BasicPacketFactory.getPacket(type,content,Reconnect.class);

            try {

                System.out.println("Reconnect to " + reconnect.getIp() + " -> " + reconnect.getPort() + " -> "+ reconnect.getNickname());
                this.basicConnect(reconnect.getIp(),reconnect.getPort());
                this.sendMessage(reconnect);
                this.starttolisten();

                if(!reconnect.isSingle())
                {
                    this.view.askCommand();
                }


            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    public void executePacket(BasicPacketFactory lastAction) {
        analyze(lastAction.toJson());
    }

    public void lorenzoTurn(String cliColor, String token) {
        this.view.lorenzoTurn(cliColor,token);
    }

    public void updatePapalToken(boolean[] papalToken, int index) {
        this.model.getPlayers()[index].setPapalSpace(papalToken);
    }
}
