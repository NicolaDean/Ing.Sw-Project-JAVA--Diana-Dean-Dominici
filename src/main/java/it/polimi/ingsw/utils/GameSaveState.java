package it.polimi.ingsw.utils;

import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.model.Game;

import java.io.Serializable;
import java.util.Date;

public class GameSaveState implements Serializable {

    private static final long serialVersionUID = 6970123627732910952L;
    private final ServerController saveData;
    private final Date             expiring;

    public GameSaveState(ServerController saveData) {
        this.saveData = saveData;
        this.expiring = new Date();
        this.expiring.setTime(this.expiring.getTime() + days(30));//30 days expiring Save data
    }

    /**
     *  load controller from file
     * @return the server contoller loaded from file
     */
    public ServerController getController()
    {
        Date today = new Date();
        if(today.getTime()<this.expiring.getTime()) //If not expired
        {
            return this.saveData;
        }
        else
        {
            return null;
        }
    }

    /**
     * Set an expiring date
     * @param days
     * @return
     */
    public static long days(int days)
    {
        return 1000L *60*60*24* days;
    }

}
