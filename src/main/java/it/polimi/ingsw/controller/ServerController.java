package it.polimi.ingsw.controller;

import it.polimi.ingsw.ClientHandler;
import it.polimi.ingsw.ServerApp;
import it.polimi.ingsw.controller.packets.*;
import it.polimi.ingsw.enumeration.ErrorMessages;
import it.polimi.ingsw.enumeration.ResourceType;
import it.polimi.ingsw.exceptions.AckManager;
import it.polimi.ingsw.exceptions.FullDepositException;
import it.polimi.ingsw.exceptions.NoBonusDepositOwned;
import it.polimi.ingsw.exceptions.WrongPosition;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.ProductionCard;
import it.polimi.ingsw.model.dashboard.Dashboard;
import it.polimi.ingsw.model.dashboard.Deposit;
import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.minimodel.MiniModel;
import it.polimi.ingsw.model.minimodel.MiniPlayer;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceList;
import it.polimi.ingsw.utils.DebugMessages;
import it.polimi.ingsw.utils.LoadGameState;
import it.polimi.ingsw.view.observer.Observable;
import it.polimi.ingsw.view.utils.CliColors;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;

import static it.polimi.ingsw.enumeration.ResourceType.*;

public class ServerController extends Observable<ServerApp> implements Serializable {

    //view
    protected Game                game;
    protected List<ClientHandler> clients = null;
    transient protected Object  lock;
    protected int                 currentClient = 0;
    protected boolean             isSinglePlayer;
    protected boolean             isStarted;
    protected  List<Resource>     pendingGain;
    protected long                id;
    protected boolean             isReconnected = false;
    protected Packet              reconnected;
    protected boolean             paused = false;
    protected Socket              waitingRoomSocket;
    /**
     *
     * @param real if true create a real controller(with clientHandlers) if false an emptyController for accept Login in waitingRoom
     */
    public ServerController(boolean real)
    {
        this.currentClient = 0;
        this.isStarted = false;
        this.game = new Game();
        this.lock = new Object();
        this.pendingGain = new ResourceList();
        if(real)  clients = new ArrayList<>();//If is a real controller create also ClientHandlers
        this.id = id;
        this.isReconnected = false;
    }

    /**
     * Fake controller constructor, it allow to save socket of client inside waiting room to understand his actions
     * (eg recive login,singleLogin,reconnect)
     * @param s socket of waitingRoom
     */
    public ServerController(Socket s)
    {
        clients = null;
        this.lock = null;
        this.waitingRoomSocket = s;
    }


    /**
     * set id of this match
     * @param matchId assiged id
     */
    public void setMatchId(long matchId) {
        this.id = matchId;
    }

    /**
     *
     * @return the id assegated to this match
     */
    public long getMatchId() {
        return this.id;
    }

    public void warning(String msg)
    {
        CliColors c = new CliColors(System.out);
        c.printlnColored(msg,CliColors.YELLOW_TEXT);
    }


    /**
     * Send a broadcast to all clients (except sender)
     * @param except if -1 send message also to itself
     * @param message packet to broadcast
     */
    public void broadcastMessage(int except,Packet message)
    {
        if(clients == null) return;

        this.warning("Broadcast sending: "+ message.generateJson());
        for(ClientHandler c: clients)
        {
            if(c.getIndex()!=except || except == -1)
            {
                if(c.checkSocket())
                    c.sendToClient(message);
            }
        }
    }

    /**
     * Remove client logged but disconnected before start (change also  index of others clinetHandlers to match pings packet)
     * @param index index of this client in the client handler list
     */
    public void removeClient(int index)
    {
        synchronized (this.lock)
        {

            if(isStarted)
            {
                this.warning("Client "+ index + " disconnected from game number "+ this.getMatchId());
                this.game.getPlayer(clients.get(index).getRealPlayerIndex()).setConnectionState(false);

                if(currentClient == index)
                    this.nextTurn();
            }
            else
            {
                this.warning("Client "+ index + " removed completly from game number "+ this.getMatchId());
                this.clients.remove(index);
                currentClient = currentClient -1;

                //Change other client index
                int i=0;
                for(ClientHandler c : clients)
                {
                    this.warning("Now Client "+  c.getIndex() + " is -> " + i);

                    c.setIndex(i);
                    i++;
                }
            }

            this.lock.notify();
        }

    }


    /**
     * "fake" controller need this function to "notify" waiting room that user logged as single player
     * method used by "LoginSinglePlayer"
     */
    public void setSinglePlayer() {
        isSinglePlayer = true;
    }

    /**
     *  method used by waiting room to check if user logged as single player to "fake controler"
     * @return return true if user logged as single player
     */

    public boolean isSinglePlayer()
    {
        return this.isSinglePlayer;
    }

    /**
     *
     * @return the list of clients connected to this match
     */
    public List<ClientHandler> getClients() {
        return clients;
    }

    /**
     * add a player to the match
     * @param client client to add
     */
    public void addClient(ClientHandler client) {
        if(clients == null) clients = new ArrayList<>();
        this.clients.add(client);
        if(client==null)return;

        client.setIndex(this.clients.size()-1);
        new Thread(client.initializePingController(this)).start();
    }


    /**
     * Reset player dashboard gained resources
     *
     * whenever user produce with a card obtained mat are inserted into a "turnGain" list
     * and when production card check if can activate itself exclude those resources from the available one
     * @param index client index of sender
     */
    public void dashReset(int index)
    {
        this.game.getPlayer(this.clients.get(index).getRealPlayerIndex()).getDashboard().resetGain();
    }

    /**
     * Send users new position of a specific player
     * @param pos
     * @param clientIndex
     */
    public void sendPositionUpdate(int pos,int clientIndex)
    {
        //TODO non sempre inviata
        if(this.isStarted)
        {
            this.broadcastMessage(-1,new IncrementPosition(pos,this.clients.get(clientIndex).getRealPlayerIndex()));
        }
    }

    public Game getGame() {
        return game;
    }


    /**
     * Generate MiniPlayers, add cheat and draw them 4 leaders each then send to clients "gameStarted" packet with minimodel data
     * @throws FullDepositException
     * @throws WrongPosition
     * @throws NoBonusDepositOwned
     */
    public void initializeMinimodel() throws FullDepositException, WrongPosition, NoBonusDepositOwned {
        //Add cheat to minimodel if flags are active
        this.addCheat();

        //Draw 4 leaders for each player
        this.game.initializeLeaders();

        MiniPlayer[] miniPlayers = this.generateMiniPlayer();
        for (ClientHandler c : clients) {
            c.sendToClient(generateGameStartedPacket(miniPlayers,c.getRealPlayerIndex()));
        }
    }
    /**
     * Start the game (called from StartGame packet)
     * Send a broadcast to all player with "GAME STARTED" packet (and remove from clientApp the line with automatic start sender)
     * @throws Exception (if game cant start)
     */
    public void startGame() throws Exception
    {
        synchronized (this.lock) {
            for(Player player:this.game.getPlayers()) player.setObserver(this); //set observer for papal space

            if (!this.isStarted) {

                if(this.game.getPlayers().size()==1 && !isSinglePlayer)
                {
                    this.sendMessage(new ACK(12), 0);
                    return;
                }

                this.warning("\n-----------Game " + this.getMatchId() + " avviato---------- \n");

                int[] realIndex = game.startGame();

                int i = 0;
                for (ClientHandler c : clients) {
                    c.getPingController().setGameStarted();
                    c.setRealPlayerIndex(realIndex[i]);
                    i++;
                }

                int firstPlayer = this.game.getPlayer(0).getControllerIndex();
                currentClient = firstPlayer;

                //Send broadcast with game started packet
                initializeMinimodel();

                this.isStarted = true;
                TimeUnit.SECONDS.sleep(2);
                //notify first player the is its turn
                //this.clients.get(firstPlayer).sendToClient(new TurnNotify());
                turnNotifier();
            } else {
                this.warning("Game already started");
                //return null;
            }
            //DebugMessages.printGeneric("\n new currplayer: "+ currentClient + ", total players: "+this.clients.size()+"\n");



            this.lock.notify();
        }

    }


    /**
     * Generate entire minimodel/miniplayers for the "startGame" packet (sended only the first time then update only changed parts)
     * @param index player index inside game
     * @return game started packet
     */
    public Packet generateGameStartedPacket(MiniPlayer[] players,int index){
        return new GameStarted(this.id,index,players,game.getProductionDecks(),game.getMarket().getResouces(),game.getMarket().getDiscardedResouce());
    }



    /**
     * Add cheat to player (for debug purpose)
     * if Debug infinite resouces flag is true add to all players 100 resources of all type and fullify storage
     * @throws WrongPosition
     * @throws NoBonusDepositOwned
     * @throws FullDepositException
     */
    public void addCheat() throws WrongPosition, NoBonusDepositOwned, FullDepositException {
        if(DebugMessages.infiniteResourcesStorage) {
            for (Player p : game.getPlayers()) {
                p.getDashboard().getStorage().safeInsertion(new Resource(COIN, 1), 0);
                p.getDashboard().getStorage().safeInsertion(new Resource(SHIELD, 2), 1);
                p.getDashboard().getStorage().safeInsertion(new Resource(SERVANT, 2), 2);
            }
        }

        List<Resource> resources = new ResourceList();
        if(DebugMessages.infiniteResourcesChest)
        {
            resources.add(new Resource(COIN,100));
            resources.add(new Resource(SERVANT,100));
            resources.add(new Resource(SHIELD,100));
            resources.add(new Resource(ROCK,100));

            for (Player p : game.getPlayers()) {
                p.chestInsertion(resources);
            }
        }
    }
    /**
     *
     * @return a list of miniplayer to send to the client to VIEW purpose
     * is a reduced version of Game model
     */
    public MiniPlayer[] generateMiniPlayer() throws FullDepositException, NoBonusDepositOwned, WrongPosition {
        MiniPlayer[] players= new MiniPlayer[game.getNofplayers()];
        int i=0;

        for (Player p:game.getPlayers()){
            players[i]=new MiniPlayer(p.getNickname());
            players[i].setStorage(p.getDashboard().getStorage().getDeposits());
            players[i].updateChest(p.getDashboard().getChest());
            players[i].setIndex(i);
            players[i].setLeaderCards(p.getLeaders());
            i++;
        }

        return players;
    }

    /**
     * check papalspace position and ad point
     */
    public void checkPapalSpaceActivation(){
        int nOfplayer= this.game.getPlayers().size();
        int[] tmp_score = new int[nOfplayer];
        for (int i = 0; i < nOfplayer; i++) { //save score to check if someone activate a papal cell
                tmp_score[i]=this.game.getPlayers().get(i).getScore();
        }

        this.game.papalSpaceCheck();  //increment point

        int index=0;
        boolean out=false;
        for (int i = 0; i < nOfplayer; i++) { //check if someone have activated papal space
            if(tmp_score[i]!=this.game.getPlayers().get(i).getScore()){
                out=true;
            }
        }

        for (int j = 0; j < nOfplayer-1; j++) { //found player that activate papal cell
            if(this.game.getPlayers().get(j).getPosition()>this.game.getPlayers().get(j+1).getPosition())
                index=j;
        }

        if(out){
            this.broadcastMessage(-1, new PapalScoreActiveted(index));
        }
    }

    /**
     * send turn packet to the next player and notify the other withthe name of current player
     */
    public void turnNotifier()
    {
        ClientHandler c = clients.get(currentClient);
        Player curr = this.game.getPlayer(c.getRealPlayerIndex());

        //Check if user have some not completed action after reconnecting
        if(!curr.getPendingCost().isEmpty())
        {
            c.sendToClient(new TurnNotifySpecialPending(curr.getPendingCost()));
        }
        else if(!this.pendingGain.isEmpty())
        {
            c.sendToClient(new TurnNotifySpecialGain(this.pendingGain));
        }
        else
        {
            c.sendToClient(new TurnNotify());
        }


        for (Player p: this.getGame().getPlayers()) {
            if (p.getControllerIndex() != currentClient)
            {
                if(p.checkConnection())
                     clients.get(p.getControllerIndex()).sendToClient(new NotifyOtherPlayerTurn(this.game.getCurrentPlayer().getNickname()));
            }


        }
    }


    /**
     * Check if the player that send the command is the current player
     * @param playerIndex index of the packet sender
     * @return true if is the current player
     */
    public boolean isRightPlayer(int playerIndex)
    {
        //TODO CHECK BETTER THE BOOLEAN EXPRESSION
        return (this.game.getCurrentPlayerIndex() == this.clients.get(playerIndex).getRealPlayerIndex());
        //return true;
    }

    /**
     * Set a pending cost response
     * @param dashboard dashboard from wich extract the pending cost
     */
    public Packet setPendingCost(Dashboard dashboard)
    {
        return  new PendingCost(dashboard.getPendingCost());
    }


    /**
     *
     * @param pos1   first leader
     * @param pos2   second leader
     * @param index  controller index
     * @return  a response packet (only if exception occur)
     */
    public Packet setLeaders(int pos1,int pos2, int index)
    {
        int playerIndex = this.clients.get(index).getRealPlayerIndex();
        Player p = this.game.getPlayer(playerIndex);
        p.setLeaders(pos1,pos2);
        this.broadcastMessage(-1,new UpdateLeaders(p.getLeaders(),playerIndex));
        return null;
    }
    /**
     *
     * @return a NACK packet indicating that is not your turn
     */
    public Packet notYourTurn()
    {
        return new ACK(ErrorMessages.NotYourTurn);
    }


    public void sendPendingCard(int index)
    {
        this.broadcastMessage(-1,this.game.getCurrentPlayer().getPendingCard());
    }
    /**
     * Player "player" buy the card in position x,y of the deks
     * @param x level
     * @param y color
     * @param pos where i want to put the card
     * @param player packet sender index
     */
    public Packet buyCard(int x,int y,int pos,int player){
        Player p = this.game.getPlayer(this.clients.get(player).getRealPlayerIndex());

        if(!isRightPlayer(player)) return this.notYourTurn();

        ProductionCard card = this.game.drawProductionCard(x,y);
        try
        {
            card.buy(p,pos);

            this.game.getProductionDecks()[x][y].pop();
            //Set a pending card, when user finish to pay it i will send the updateBuyedCard packet i added to player
            ProductionCard newCard = null;
            if( !this.game.getProductionDecks()[x][y].isEmpty())
            {
                newCard = this.game.getProductionDecks()[x][y].peek();
            }

            p.setPendingBuy(newCard,x,y,pos,this.clients.get(player).getRealPlayerIndex());

            return setPendingCost(p.getDashboard());
        } catch (AckManager err) {
            err.printStackTrace();
            return err.getAck();
        }

    }

    /**
     * production of a player
     * @param pos which card i wanna use
     * @param player client index
     */
    public Packet production(int pos,int player)
    {
        Player p = this.game.getPlayer(this.clients.get(player).getRealPlayerIndex());
        Dashboard dashboard = p.getDashboard();

        if(!isRightPlayer(player)) return this.notYourTurn();

        try {
            dashboard.production(p,pos);
            return setPendingCost(dashboard);
        } catch (AckManager err) {
            return err.getAck();
        }
    }

    public boolean isStarted()
    {
        return this.game.isGamestarted();
    }
    public boolean isFull(String nickname)
    {
        return this.game.isFull(nickname);
    }
    /**
     * basic production from player
     * @param res1 first spended resource
     * @param res2 second spended resource
     * @param obt  wanted resource
     * @param player client index
     */
    public Packet basicProduction(ResourceType res1,ResourceType res2, ResourceType obt, int player)
    {

        if(!isRightPlayer(player)) return this.notYourTurn();

        System.out.println(game.getCurrentPlayer().getNickname());
        Player p = this.game.getPlayer(this.clients.get(player).getRealPlayerIndex());
        Dashboard dashboard = p.getDashboard();

        //if(!isRightPlayer(player)) return this.notYourTurn();
        System.out.println("Res 1: "+ res1);
        System.out.println("Res 2: "+ res2);
        System.out.println("obt: "+ obt);

        try {

            dashboard.basicProduction(res1,res2,obt);
            return setPendingCost(dashboard);
        } catch (AckManager err) {
            System.out.println("\nerrore nella production\n");
            return err.getAck();
        }
    }

    /**
     * Try to login to the game
     * @param nickname player name
     * @return ack if of Nack if exception occurred
     */
    public Packet login(String nickname)
    {
        if(clients == null)
        {
            this.notifyObserver(serverApp -> {
                if(isSinglePlayer)
                {
                    serverApp.singleLogin(nickname, this.waitingRoomSocket);
                }
                else {
                    serverApp.login(nickname,this.waitingRoomSocket);
                }

            });
            //for waiting room exit condition
            paused = true;
            return null;
        }


        try {
            this.game.addPlayer(nickname);
            //System.out.println("Login di " + nickname);
            return new ACK(0);
        } catch (Exception e) {
            //System.out.println("Login di " +nickname + " FALLITO");
            return new ACK(4);
        }
    }

    /**
     * allow to insert resources into a specific player storage
     * @param resource resourtce to insert
     * @param pos      deposit to select
     * @param player   client index
     * @return         true
     */
    public Packet storageInsertion(Resource resource,int pos, int player)
    {
        if(!isRightPlayer(player)) return this.notYourTurn();

        Player p = this.game.getPlayer(this.clients.get(player).getRealPlayerIndex());

        try {
            p.storageInsertion(resource,pos);
        } catch (AckManager err) {
            DebugMessages.printError("Un errore di inserimento");
            return err.getAck();
        }
        return  null;
    }
    /**
     * Extract resource from the storage
     * @param resource resource to remove
     * @param pos      deposit selected
     * @param player   client index
     * @return Ack or Nack
     */
    public Packet storageExtraction(Resource resource, int pos, int player)
    {
        if(!isRightPlayer(player)) return this.notYourTurn();

        Player p = this.game.getPlayer(this.clients.get(player).getRealPlayerIndex());;
        try {
            p.payStorageResource(resource,pos);
        } catch (AckManager err) {
            return err.getAck();
        }
        return  null;
    }
    /**
     * Extract resource from the chest
     * @param resource resource to remove
     * @param player   packet sender
     * @return Ack or Nack
     */
    public Packet chestExtraction(Resource resource, int player)
    {
        if(!isRightPlayer(player)) return this.notYourTurn();

        Player p = this.game.getPlayers().get(this.clients.get(player).getRealPlayerIndex());
        p.payChestResource(resource);
        return  null;
    }

    /**
     *
     * @param pos leader to activate/discard
     * @param player
     * @return
     */
    public Packet activateLeader(int pos,boolean action, int player)
    {
        if(!isRightPlayer(player)) return this.notYourTurn();

        Player p = this.game.getPlayer(this.clients.get(player).getRealPlayerIndex());
        try {
            if(action)
            {
                p.activateLeader(pos);
                Packet update = p.getLeaderCardUpdate(pos,this.clients.get(player).getRealPlayerIndex());
                this.broadcastMessage(-1,update);

                this.sendLeaderUpdate(player);
            }
            else
            {
                p.discardLeader(pos);
                this.broadcastMessage(-1,new UpdateLeaders(p.getLeaders(),this.clients.get(player).getRealPlayerIndex()));
                //TODO send leaderUpdate with discarded leader
            }

            if(p.getLeaders()[pos].getCliRappresentation().equals("DEPOSIT")) {

                sendMessage(new LeaderActivated(p.getLeaders()[pos].getId()), p.getControllerIndex());

                if(DebugMessages.infiniteResourcesStorage)
                {
                    /*int d=3;
                    if(p.getDashboard().getStorage().getStorage()[4] != null)
                        d=4;
                    try {
                        p.getDashboard().getStorage().safeInsertion(new Resource(p.getLeaders()[pos].getType(), 1), d);
                    } catch (Exception e) {

                        System.out.println("deposit: "+d+"  tipo: "+p.getLeaders()[pos].getType());

                    }
                    sendStorageUpdate(p.getControllerIndex());

                     */
                }



                return null;
            }
            else
                return new ACK(0);
        } catch (AckManager err) {
            return err.getAck();
        }
    }

    /**
     * bonus production
     * @param pos   card to activate
     * @param obt   wanted res
     * @param player packet sender
     * @return ack or nack
     */
    public Packet bonusProduction(int pos,ResourceType obt,int player)
    {
        if(!isRightPlayer(player)) return this.notYourTurn();

        Player p = this.game.getPlayer(this.clients.get(player).getRealPlayerIndex());
        try {
            p.bonusProduction(pos,obt);
            return setPendingCost(p.getDashboard());
        } catch (AckManager err) {
            return err.getAck();
        }
    }

    /**
     * execute swap on a specific player storage
     * @param pos1    first deposit
     * @param pos2    destination deposit
     * @param player  client index
     * @return
     */
    public Packet swapDeposit(int pos1,int pos2,int player)
    {
        //if(!isRightPlayer(player)) return this.notYourTurn();

        //System.out.println("\n" +player +"\n");
        Player p = this.game.getPlayer(player);
        //System.out.println(player + "!!!!\n\n\n");



        //System.out.println("\n preswap d1: "+ p.getDashboard().getStorage().getStorage()[0].getResource().getType());
        //System.out.println(" preswap d2: "+ p.getDashboard().getStorage().getStorage()[1].getResource().getType());


        try {


            p.getDashboard().getStorage().swapDeposit(pos1 -1,pos2 -1);

            Deposit[] tmp = (p.getDashboard().getStorage().getDeposits());

            //System.out.println("\n postswap d1: "+ p.getDashboard().getStorage().getStorage()[0].getResource().getType());
            //System.out.println(" postswap d2: "+ p.getDashboard().getStorage().getStorage()[1].getResource().getType()+"\n");
            sendStorageUpdate(p.getControllerIndex());

            return null;

        } catch (AckManager err) {
            return err.getAck();
        }

    }

    /**
     * same as swap but allow to transfer a specific resource quantity from a deposit A to a deposit B
     * @param pos1   start deposit
     * @param pos2   dest deposit
     * @param q      quantity to move
     * @param player client index
     * @return
     */
    public Packet MoveResources(int pos1,int pos2,int q, int player)
    {
        Player p = this.game.getPlayer(this.clients.get(player).getRealPlayerIndex());
        try{
        p.getDashboard().getStorage().moveResource(pos1,pos2,q);
        Deposit[] tmp = (p.getDashboard().getStorage().getDeposits());
        sendStorageUpdate(p.getControllerIndex());

        return null;}

        catch (AckManager err) {
            return err.getAck();
        }

    }

    /**
     * called when a speecific user discard market resource
     * @param quantity  quantity discarded
     * @param index     client index
     * @return
     */
    public Packet discardResource(int quantity,int index)
    {
        this.game.discardResource(quantity);

        this.broadcastMessage(-1,new UpdatePosition(quantity,this.clients.get(index).getRealPlayerIndex()));
        return new ACK(0);
    }

    public List<Resource> getPendingGain() {
        return pendingGain;
    }

    /**
     *
     * @param direction row or column (row = false,col = true)
     * @param pos    row/col position of the market
     * @param player packet sender
     * @return a packet containing the resources extracted and eventual "whiteballs" to ask the user
     */
    public Packet marketExtraction(boolean direction,int pos,int player)
    {
        if(!isRightPlayer(player)) return this.notYourTurn();

        Player p = this.game.getCurrentPlayer();
        Market m = this.game.getMarket();

        try
        {
            if(direction)
            {
                m.exstractColumn(pos,p);
            }
            else
            {
                m.exstractRow(pos,p);
            }
        }catch (AckManager err) {
            return err.getAck();
        }

        if(this.game.getMarket().getRedBallExtracted()){
            this.broadcastMessage(-1,new ExtreactedRedBall(1,clients.get(player).getRealPlayerIndex()));
        }

        List <Resource> res = m.getPendingResourceExtracted();
        int white           = m.getWhiteCount();
        this.broadcastMessage(-1,new UpdateMiniMarket(direction,pos));

        pendingGain.addAll(res);
        return  new MarketResult(res,white);

    }


    /**
     * send to clients the information a specific player updated his leaders
     * @param index client index
     */
    public void sendLeaderUpdate(int index)
    {
        LeaderCard[] leaderCards = this.game.getPlayer(this.clients.get(index).getRealPlayerIndex() ).getLeaders();
        this.broadcastMessage(-1,new UpdateLeaders(leaderCards,this.clients.get(index).getRealPlayerIndex()));
    }

    /**
     * send to clients the information a specific player updated his storage
     * @param index client index
     */
    public void sendStorageUpdate(int index)
    {
        Deposit[] tmp = this.game.getPlayer(this.clients.get(index).getRealPlayerIndex() ).getDashboard().getStorage().getDeposits();
        this.broadcastMessage(-1,new StorageUpdate(tmp,this.clients.get(index).getRealPlayerIndex()));
    }

    /**
     * send to clients the information a specific player updated his chest
     * @param index client index
     */
    public void sendChestUpdate(int index)
    {
        List<Resource>chest = this.game.getPlayer(this.clients.get(index).getRealPlayerIndex()).getDashboard().getChest();
        this.broadcastMessage(-1,new ChestUpdate(chest,this.clients.get(index).getRealPlayerIndex()));
    }

    /**
     *
     * @return true if all player was disconnected (if true the recconnect packet will load game from file)
     */
    public boolean isPaused()
    {
        return paused;
    }
    /**
     * Save Game state for the "evryOneDisconnected" or "serverCrush" possibility
     */
    public void saveGameState() {
        try {
            LoadGameState.saveGame(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * When reconnecting a player check if other player have invalid socket (to avoid using null socket)
     */
    public void checkClientsStateReconnecting()
    {
        for(ClientHandler c:clients)
        {
            if(!c.checkSocket())
                this.game.getPlayer(c.getRealPlayerIndex()).setConnectionState(false);
        }
    }
    /**
     * if end condition are true send to all a "last Turn" packet
     * if the current player is 4 and the match is ended then send an "end game" packet to all
     * if nor of prewious is true then send a message "typeTurn" to the next player
     * @return null
     */
    public Packet nextTurn(){

        //se risulterà positivo invierà in broadcast EndTurn e chiudera la connessione in maniera safe
        if(game.checkEndGame()) lastTurn();
        Player player=null;

        saveGameState();

        int nOfDisconnected=0;
        do
        {
            player = game.nextTurn();

            if(!player.checkConnection())
            {
                nOfDisconnected ++;
                DebugMessages.printError(player.getNickname()+" Skip turn due disconnection");
            }

            if(nOfDisconnected == this.game.getPlayers().size())
            {
                paused = true;
                DebugMessages.printError("All players Disconnected, Game paused");

                return null;
            }
        }while (!player.checkConnection());

        DebugMessages.printError("PLAYER "+ this.game.getCurrentPlayerIndex() + "->controller:"+this.game.getCurrentPlayer().getControllerIndex());

        if(game.IsEnded())
        {
            endGame();
            return null;
        }

        currentClient = player.getControllerIndex();

        turnNotifier();
        return null;
    }

    /**
     * Broadcast endgame packet
     */
    public void endGame()
    {
        //remove itself from availableControllers
        this.notifyObserver(serverApp -> {serverApp.closeController(this);});

        this.broadcastMessage(-1, new EndGame(exstractCharts()));
    }

    /**
     * Extract from game the charts
     * @return charts of player
     */
    public String [] exstractCharts(){
        List<String> nick=new ArrayList<>();
        List<Integer> score=new ArrayList<>();
        List<String> out=new ArrayList<>();

        for (int i = 0; i < game.getNofplayers(); i++) {
            nick.add(game.getPlayers().get(i).getNickname());
            score.add(game.getPlayers().get(i).getScore());
        }
        String tmpN;
        int tmpS;
        for (int i = 0; i < game.getNofplayers(); i++) {
            tmpN=nick.get(0);
            tmpS=score.get(0);
            for (int j = 0; j < nick.size()-1; j++) {
                if(tmpS<game.getPlayers().get(j+1).getScore()){
                    tmpN=nick.get(j+1);
                    tmpS=score.get(j+1);
                }
            }
            out.add(tmpN);
            nick.remove(tmpN);
            score.remove(Integer.valueOf(tmpS));
        }

        return out.toArray(new String[0]);
    }

    /**
     * Broadcast lastTurn packet
     */
    public void lastTurn()
    {
        this.broadcastMessage(-1, new EndGame(exstractCharts()));
    }

    public void sendMessage(Packet p,int index)
    {
        this.clients.get(index).sendToClient(p);
        /*for(ClientHandler c: clients )
        {
            if(c.getRealPlayerIndex() == index) c.sendToClient(p);
        }*/
    }


    /**
     * try to reconnect socket to this match
     * @param socket    client new socket
     * @param nickname  user who try to reconnect
     */
    public ClientHandler reconnect(Socket socket, String nickname) {

        if(lock==null) lock = new Object();
        int i=0;
        ClientHandler handler = null;
        for(Player p:this.game.getPlayers())
        {
            //If same name and player is disconnected (forbit to reconnect multiple time simultaneusly)
            if(p.getNickname().equals(nickname) && (!p.checkConnection() || paused))
            {
                    handler = this.clients.get(p.getControllerIndex());
                    //Set new Socket
                    handler.reconnect(socket,handler.getIndex(),this);
                    handler.getPingController().setGameStarted();

                    if(nickname.equals(this.game.getPlayer(this.clients.get(p.getControllerIndex()).getRealPlayerIndex()).getNickname()))
                    {
                        DebugMessages.printError("EURECAAA");
                    }
                    p.setConnectionState(true);

                    try {
                        //Create Minimodel of CURRENT STATE of game
                        MiniModel m = new MiniModel();
                        //ADD player to it
                        MiniPlayer[] players = this.generateMiniPlayer();
                        m.setPlayers(players);
                        //ADD shop to it
                        m.setDeck(game.getProductionDecks());
                        //Set Personal Index
                        int index = this.clients.get(p.getControllerIndex()).getRealPlayerIndex();
                        m.setPersanalIndex(index);
                        //Create a reconnecting Info to send to client
                        Packet reconn = new ReconnectingInfo(id,m,this.game.getMarket().getResouces(),this.game.getMarket().getDiscardedResouce());
                        handler.sendToClient(reconn);
                        return handler;

                    } catch (FullDepositException e) {
                        e.printStackTrace();
                    } catch (NoBonusDepositOwned noBonusDepositOwned) {
                        noBonusDepositOwned.printStackTrace();
                    } catch (WrongPosition wrongPosition) {
                        wrongPosition.printStackTrace();
                    }



            }
            i++;
        }
        return  handler;
    }

    /**
     *
     * @return true if fake controller is setted to reconnect state by reconnect packet inside waiting room
     */
    public boolean isReconnected()
    {
        return this.isReconnected;
    }
    public Packet getReconnected()
    {
        return this.reconnected;
    }

    /**
     * if waiting room recive a reconnect packet it affect the fake controller by setting true this flag
     */
    public void setReconnect(String nickname,long id)
    {
        this.paused = true;
        this.notifyObserver(serverApp -> {serverApp.reconnect(nickname,id,this.waitingRoomSocket);});
        this.isReconnected = true;
    }

    /**
     * exit pause state and lauch turn
     */
    public void exitPause() {

        paused = false;
        nextTurn();
    }

    public void exitPauseOnline()
    {
        paused = false;
    }


    /**
     * Indicate that now game is in paused mode , it happen when server crush and user reload, or when all player disconnect
     */
    public void setPaused()
    {
        lock = new Object();
        paused = true;
    }


}
