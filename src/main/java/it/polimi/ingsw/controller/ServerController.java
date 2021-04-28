package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.packets.*;
import it.polimi.ingsw.enumeration.ResourceType;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.ProductionCard;
import it.polimi.ingsw.model.dashboard.Dashboard;
import it.polimi.ingsw.model.resources.Resource;

import java.util.List;

public class ServerController extends Controller{

    //view
    Game game;
    PacketManager response;
    boolean responseAvailable;

    public ServerController()
    {
        responseAvailable = false;
        game = new Game();
    }

    public Game getGame() {
        return game;
    }


    /**
     * Check if the player that send the command is the current player
     * @param playerIndex index of the packet sender
     * @return true if is the current player
     */
    public boolean isRightPlayer(int playerIndex)
    {
        if(!(this.game.getCurrentPlayerIndex() == playerIndex))
        {
            this.setResponse(new ACK(1));
        }
        return this.game.getCurrentPlayerIndex() == playerIndex;
    }

    /**
     * Set a pending cost response
     * @param dashboard dashboard from wich extract the pending cost
     */
    public void setPendingCost(Dashboard dashboard)
    {
        this.setResponse(new PendingCost(dashboard.getPendingCost()));
    }
    /**
     * Player "player" buy the card in position x,y of the deks
     * @param x level
     * @param y color
     * @param pos where i want to put the card
     * @param player packet sender index
     */
    public void buyCard(int x,int y,int pos,int player){
        Player p = this.game.getCurrentPlayer();
        if(!isRightPlayer(player)) return;

        ProductionCard card = this.game.drawProductionCard(x,y);
        try
        {
            card.buy(p,pos);
            setPendingCost(p.getDashboard());
        } catch (Exception e) {
            this.setResponse(new ACK(1));
        }


    }

    /**
     * production of a player
     * @param pos which card i wanna use
     * @param player player who wand to do an action
     */
    public void production(int pos,int player)
    {
        Player p = this.game.getCurrentPlayer();
        Dashboard dashboard = p.getDashboard();

        if(!isRightPlayer(player)) return;

        try {
            dashboard.production(p,pos);
            setPendingCost(dashboard);
        } catch (Exception e) {
            this.setResponse(new ACK(1));
        }
    }

    /**
     * basic production from player
     * @param res1 first spended resource
     * @param res2 second spended resource
     * @param obt  wanted resource
     * @param player packet sender
     */
    public void basicProduction(ResourceType res1,ResourceType res2, ResourceType obt, int player)
    {
        Player p = this.game.getCurrentPlayer();
        Dashboard dashboard = p.getDashboard();

        if(!isRightPlayer(player)) {
            return;
        }

        try {
            dashboard.basicProduction(res1,res2,obt);
            //Add pending cost
        } catch (Exception e) {
            this.setResponse(new ACK(1));
        }
    }

    public void login(String nickname)
    {
        try {
            this.game.addPlayer(nickname);
            System.out.println("Login di " + nickname);
            this.setResponse(new ACK(0));
        } catch (Exception e) {
            System.out.println("Login di " +nickname + " FALLITO");
            this.setResponse(new ACK(1));
        }
    }
    /**
     *
     * @return true if is available a response
     */
    public boolean responseAvailable()
    {
        return this.responseAvailable;
    }

    /**
     *
     * @return get back the response available in json format
     */
    public String getResponse() {
        this.responseAvailable = false;
        return this.response.generateJson();
    }

    /**
     * Set a response packet
     * @param response packet to use as a response
     */
    public void setResponse(PacketManager response) {
        this.responseAvailable = true;
        this.response = response;
    }

}
