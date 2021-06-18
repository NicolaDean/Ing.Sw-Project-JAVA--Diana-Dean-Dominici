package it.polimi.ingsw.utils;

import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;
import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.model.Game;

import java.io.*;
import java.util.List;

public class LoadGameState {



    /**
     *
     * @param id id of match to load
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static ServerController loadGame(long id) throws IOException, ClassNotFoundException {

        ObjectInputStream objectinputstream = null;
        FileInputStream streamIn = new FileInputStream(ConstantValues.saveFileName + id + ".ser");
        objectinputstream = new ObjectInputStream(streamIn);
        GameSaveState loadData = (GameSaveState) objectinputstream.readObject();

        return loadData.getController();
    }

    /**
     * save the Server controller to file if all player are disconnected or at each turn end
     * @param controller
     * @throws IOException
     */
    public static void saveGame(ServerController controller) throws IOException {

        FileOutputStream fout  = new FileOutputStream(ConstantValues.saveFileName +controller.getMatchId()+".ser");
        ObjectOutputStream oos = new ObjectOutputStream(fout);
        oos.writeObject(new GameSaveState(controller));

        oos.close();
        fout.close();
    }

    /**
     * Delete the saving data relative to a specific match
     * @param id match id
     */
    public static void deleteGameSave(long id)
    {
        //TODO
    }

    public static void writeCurrentId(long id) throws IOException {

        Writer writer = new FileWriter(ConstantValues.currentIdFile);
        Gson gson = new Gson();
        gson.toJson(id,writer);

        writer.close();
    }

    public static long loadCurrentId() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(ConstantValues.currentIdFile));
        Gson gson = new Gson();

        int x =  gson.fromJson(bufferedReader,int.class);
        bufferedReader.close();
        return x;
    }
}
