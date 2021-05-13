package it.polimi.ingsw.controller;

import it.polimi.ingsw.ClientHandler;
import it.polimi.ingsw.controller.packets.*;
import it.polimi.ingsw.enumeration.ErrorMessages;
import it.polimi.ingsw.enumeration.ResourceType;
import it.polimi.ingsw.exceptions.AckManager;
import it.polimi.ingsw.exceptions.WrongPosition;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.ProductionCard;
import it.polimi.ingsw.model.dashboard.Dashboard;
import it.polimi.ingsw.model.lorenzo.LorenzoGame;
import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.view.utils.CliColors;

import java.util.ArrayList;
import java.util.List;

public class ServerController{

    //view
    Game game;
    List<ClientHandler> clients;
    int currentClient = 0;
    boolean isSinglePlayer;
    boolean isStarted;
    private int idpartita;

    /**
     *
     * @param real if true create a real controller(with clientHandlers) if false an emptyController for accept Login in waitingRoom
     */
    public ServerController(boolean real)
    {
        currentClient = 0;
        isStarted = false;
        game = new Game();
        if(real)  clients = new ArrayList<>();//If is a real controller create also ClientHandlers
    }



    public void setIdpartita(int idpartita) {
        this.idpartita = idpartita;
    }

    public int getIdpartita() {
        return idpartita;
    }

    public void warning(String msg)
    {
        CliColors c = new CliColors(System.out);
        c.printColored(msg,CliColors.YELLOW_TEXT);
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
            if(c.getIndex()!=except || except == -1) c.sendToClient(message);
        }
    }

    /**
     * Remove client logged but disconnected before start (change also  index of others clinetHandlers to match pings packet)
     * @param index index of this client in the client handler list
     */
    public void removeClient(int index)
    {
        this.warning("Client "+ index + " removed from game number "+ this.getIdpartita());
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
        this.clients.add(client);

        client.setIndex(currentClient);
        currentClient++;
        if (currentClient>=4)
            currentClient = currentClient -4;
        new Thread(client.initializePingController(this)).start();
    }



    public Game getGame() {
        return game;
    }

    /**
     * Start the game (called from StartGame packet)
     * //TODO Send a broadcast to all player with "GAME STARTED" packet (and remove from clientApp the line with automatic start sender)
     * @throws Exception (if game cant start)
     */
    public void startGame() throws Exception
    {

        if(!this.isStarted)
        {
            this.isStarted = true;

            this.warning("\n-----------Game "+ this.getIdpartita() + " avviato---------- \n");

            int[] realIndex = game.startGame();

            int i=0;
            for (ClientHandler c:clients) {
                c.getPingController().setGameStarted();
                c.setRealPlayerIndex(realIndex[i]);
                i++;
            }

            int firstPlayer = this.game.getRealPlayerHandlerIndex();

            //Send broadcast with game started packet
            this.broadcastMessage(-1,new GameStarted());

            //notify first player the is its turn
            this.clients.get(firstPlayer).sendToClient(new TurnNotify());
        }
        else
        {
            this.warning("Game already started");
            //return null;
        }

        System.out.println("");

    }


    /**
     * Check if the player that send the command is the current player
     * @param playerIndex index of the packet sender
     * @return true if is the current player
     */
    public boolean isRightPlayer(int playerIndex)
    {
        return (this.game.getCurrentPlayerIndex() == this.clients.get(playerIndex).getRealPlayerIndex());
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
     * @return a NACK packet indicating that is not your turn
     */
    public Packet notYourTurn()
    {
        return new ACK(ErrorMessages.NotYourTurn);
    }

    /**
     * Player "player" buy the card in position x,y of the deks
     * @param x level
     * @param y color
     * @param pos where i want to put the card
     * @param player packet sender index
     */
    public Packet buyCard(int x,int y,int pos,int player){
        Player p = this.game.getCurrentPlayer();

        if(!isRightPlayer(player)) return this.notYourTurn();

        ProductionCard card = this.game.drawProductionCard(x,y);
        try
        {
            card.buy(p,pos);
            return setPendingCost(p.getDashboard());
        } catch (AckManager err) {
            return err.getAck();
        }

    }

    /**
     * production of a player
     * @param pos which card i wanna use
     * @param player player who wand to do an action
     */
    public Packet production(int pos,int player)
    {
        Player p = this.game.getCurrentPlayer();
        Dashboard dashboard = p.getDashboard();

        if(!isRightPlayer(player)) return this.notYourTurn();

        try {
            dashboard.production(p,pos);
            return setPendingCost(dashboard);
        } catch (AckManager err) {
            return err.getAck();
        }
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
     * @param player packet sender
     */
    public Packet basicProduction(ResourceType res1,ResourceType res2, ResourceType obt, int player)
    {
        Player p = this.game.getCurrentPlayer();
        Dashboard dashboard = p.getDashboard();

        if(!isRightPlayer(player)) return this.notYourTurn();

        try {
            dashboard.basicProduction(res1,res2,obt);
            return setPendingCost(dashboard);
        } catch (AckManager err) {
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
     * Extract resource from the storage
     * @param resource resource to remove
     * @param pos      deposit selected
     * @param player   packet sender
     * @return Ack or Nack
     */
    public Packet storageExtraction(Resource resource, int pos, int player)
    {
        if(!isRightPlayer(player)) return this.notYourTurn();

        Player p = this.game.getCurrentPlayer();
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

        Player p = this.game.getCurrentPlayer();
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

        Player p = this.game.getCurrentPlayer();
        try {
            if(action)
            {
                p.activateLeader(pos);
            }
            else
            {
                p.discardLeader(pos);
            }

            return new ACK(0);
        } catch (Exception e) {
            e.printStackTrace();
            return new ACK(1);
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

        Player p = this.game.getCurrentPlayer();
        try {
            p.bonusProduction(pos,obt);
            return new ACK(0);
        } catch (Exception e) {
            e.printStackTrace();
            return new ACK(1);
        }
    }

    public Packet swapDeposit(int pos1,int pos2,int player)
    {
        if(!isRightPlayer(player)) return this.notYourTurn();

        Player p = this.game.getCurrentPlayer();
        try {
            p.getDashboard().getStorage().swapDeposit(pos1,pos2);
            return new ACK(0);
        } catch (Exception e) {
            e.printStackTrace();
            return new ACK(1);
        }
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


        List <Resource> res = m.getPendingResourceExtracted();
        int white           = m.getWhiteCount();
        return  new MarketResult(res,white);

    }

    /**
     * if end condition are true send to all a "last Turn" packet
     * if the current player is 4 and the match is ended then send an "end game" packet to all
     * if nor of prewious is true then send a message "typeTurn" to the next player
     * @return null
     */
    public Packet nextTurn(){
        Player player = game.nextTurn();

        //se risulterà positivo invierà in broadcast EndTurn e chiudera la connessione in maniera safe
        if(game.checkEndGame()) lastTurn();
        if(game.IsEnded())
        {
            endGame();
            return null;
        }

        //TODO Send message to next player client
        return null;
    }

    /**
     * Broadcast endgame packet
     */
    public void endGame()
    {
        this.broadcastMessage(-1, new EndGame());
    }

    /**
     * Broadcast lastTurn packet
     */
    public void lastTurn()
    {
        this.broadcastMessage(-1, new EndGame());
    }


}
