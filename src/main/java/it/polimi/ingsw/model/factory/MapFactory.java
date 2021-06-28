
package it.polimi.ingsw.model.factory;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.polimi.ingsw.model.CellScore;
import it.polimi.ingsw.model.PapalSpace;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MapFactory {


    /**
     * generate a list of papal spaces from a json file
     * @return a list of papal space
     */
    public static List<PapalSpace> loadPapalSpacesFromJsonFile()
    {
        String path = "json/papalSpace.json";
        InputStream is = MapFactory.class.getClassLoader().getResourceAsStream(path);

        if (is == null) try {
            throw new Exception("File " + path + " not found");
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonParser parser = new JsonParser();
        JsonArray array = parser.parse(new InputStreamReader(is)).getAsJsonArray();

        List<PapalSpace> out = new ArrayList<>();

        int i=0;
        for(JsonElement o:array)
        {
            JsonObject papalSpace = o.getAsJsonObject();

            int start  = papalSpace.get("initialPosition").getAsInt();
            int finish = papalSpace.get("finalPosition").getAsInt();
            int score  = papalSpace.get("score").getAsInt();

            PapalSpace papal = new PapalSpace(start,finish,score);
            papal.setIndex(i);
            out.add(papal);
            i++;

        }

        return out;

    }


    /**
     * Generate a list of victory points positions
     * @return the list of cells that compose the dashboard
     */
    public static List<CellScore> loadCellScoresFromJsonFile()
    {
        String path = "json/victoryPoints.json";
        InputStream is = MapFactory.class.getClassLoader().getResourceAsStream(path);

        if (is == null) try {
            throw new Exception("File " + path + " not found");
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonParser parser = new JsonParser();
        JsonArray array = parser.parse(new InputStreamReader(is)).getAsJsonArray();

        List<CellScore> out = new ArrayList<>();

        for(JsonElement o:array)
        {
            JsonObject papalSpace = o.getAsJsonObject();

            int position  = papalSpace.get("position").getAsInt();
            int score  = papalSpace.get("score").getAsInt();

            out.add(new CellScore(position,score));

        }

        return out;
    }
}
