package it.polimi.ingsw.utils;

import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.model.Game;

import java.io.*;
import java.util.List;

public class LoadGameState {


    public static void searchGame(int id, List<ServerController> matchs) throws IOException, ClassNotFoundException {
        for(ServerController c:matchs)
        {
            if(c.isPaused())
            {
                loadGame(c);
            }
            else {

            }
        }
    }

    /**
     *
     * @param controller
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void loadGame(ServerController controller) throws IOException, ClassNotFoundException {

        ObjectInputStream objectinputstream = null;
        FileInputStream streamIn = new FileInputStream("save.ser");
        objectinputstream = new ObjectInputStream(streamIn);
        GameSaveState a = (GameSaveState) objectinputstream.readObject();
    }

    /**
     * save the Server controller to file if all player are disconnected or at each turn end
     * @param controller
     * @throws IOException
     */
    public static void saveGame(ServerController controller) throws IOException {

        FileOutputStream fout  = new FileOutputStream("save-state-"+controller.getMatchId()+".ser");
        ObjectOutputStream oos = new ObjectOutputStream(fout);
        oos.writeObject(new GameSaveState(controller));

        oos.close();
        fout.close();
    }
}
