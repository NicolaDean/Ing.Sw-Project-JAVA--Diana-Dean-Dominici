package it.polimi.ingsw.model.cards;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.enumeration.CardType;
import it.polimi.ingsw.enumeration.ResourceType;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceList;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.enumeration.ResourceType.*;

public class JsonCardFactory {

    /**
     * Read a json file and give back a list of cards
     * @throws Exception file dosnt exist
     */
    public static List<ProductionCard> loadProductionCardsFromJsonFile() throws Exception {
        String path = "json/productionCards.json";
        InputStream is = JsonCardFactory.class.getClassLoader().getResourceAsStream(path);

        if (is == null) throw new Exception("File " + path + " not found");

        JsonParser parser = new JsonParser();
        JsonArray array = parser.parse(new InputStreamReader(is)).getAsJsonArray();

        //Load All cards
        List<ProductionCard>cards = buildProductionCardListFromJsonArray(array);;

        System.out.println("test finito");
        return cards;
    }

    /**
     * Given a json array of cards give a list of productionCards
     * @param array Json Array
     * @return a list of Production cards
     */
    public static List<ProductionCard> buildProductionCardListFromJsonArray(JsonArray array)
    {

        List<ProductionCard> cards = new ArrayList<>();
        for(JsonElement o:array)
        {
            //Preparing the Lists of resources
            List<Resource> cost = new ResourceList();
            List<Resource> obtained= new ResourceList();
            List<Resource> raw = new ResourceList();

            //LOAD CURRENT CARD
            JsonObject currCard = o.getAsJsonObject();

            //GET LEVEL,VictoryPoint and type of card
            int level = currCard.get("level").getAsInt();
            int victoryPoints = currCard.get("victoryPoints").getAsInt();
            CardType type = CardType.valueOf(currCard.get("type").getAsString());


            //LOAD COST
            JsonArray lists = currCard.getAsJsonArray("cost");
            cost = buildResourceListFromJsonArray(lists);

            //LOAD RAW MAT
            lists = currCard.getAsJsonArray("rawMaterials");
            raw = buildResourceListFromJsonArray(lists);

            //LOAD OBTAINED MAT
            lists = currCard.getAsJsonArray("obtainedMaterials");
            obtained = buildResourceListFromJsonArray(lists);

            ProductionCard c = new ProductionCard(cost,raw,obtained,victoryPoints,level,type);
            cards.add(c);
        }
        return cards;
    }
    /**
     * Given a JSON array of resources build a ResourceList from it
     * @param array JSONarray full of ressources
     * @return ResourceList
     */
    public static List<Resource> buildResourceListFromJsonArray(JsonArray array)
    {
        List<Resource> list = new ResourceList();
        for(JsonElement o:array)
        {
            JsonObject res = o.getAsJsonObject();

            ResourceType t = ResourceType.valueOf(res.get("type").getAsString());
            int qty = res.get("quantity").getAsInt();

            list.add(new Resource(t,qty));
        }

        return list;
    }
}
