package it.polimi.ingsw.model.factory;

import com.google.gson.*;
import it.polimi.ingsw.enumeration.CardType;
import it.polimi.ingsw.enumeration.ResourceType;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.PrerequisiteCard;
import it.polimi.ingsw.model.cards.ProductionCard;
import it.polimi.ingsw.model.cards.leaders.DepositBonus;
import it.polimi.ingsw.model.cards.leaders.LeaderDiscountCard;
import it.polimi.ingsw.model.cards.leaders.LeaderTradeCard;
import it.polimi.ingsw.model.cards.leaders.LeaderWhiteCard;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceList;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class CardFactory {

    /**
     * Read a json file and give back a list of cards
     * @throws Exception file dosnt exist
     */
    public static Stack<ProductionCard>[][] loadProductionCardsFromJsonFile() {
        String path = "json/productionCards.json";
        InputStream is = CardFactory.class.getClassLoader().getResourceAsStream(path);

        if (is == null) try {
            throw new Exception("File " + path + " not found");
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonParser parser = new JsonParser();
        JsonArray array = parser.parse(new InputStreamReader(is)).getAsJsonArray();

        //Load All cards
        List<ProductionCard>cards = loadProductionCardListFromJsonArray(array);
        //Mix cards
        Collections.shuffle(cards);

        //Assign cards to the right Stack

        return spitCardsInDeks(cards);
    }

    public static Stack<ProductionCard>[][] spitCardsInDeks(List<ProductionCard> cards)
    {
        Stack<ProductionCard>[][] decks = new Stack[3][4];

        for(ProductionCard card :cards)
        {
            int level = card.getLevel()-1;
            int type= card.getType().ordinal();

            if(decks[level][type] == null) decks[level][type] = new Stack<ProductionCard>();

            decks[level][type].add(card);
        }

        return decks;
    }

    /**
     * Given a json array of cards give a list of productionCards
     * @param array Json Array
     * @return a list of Production cards
     */
    public static List<ProductionCard> loadProductionCardListFromJsonArray(JsonArray array)
    {

        List<ProductionCard> cards = new ArrayList<>();
        for(JsonElement o:array)
        {
            //LOAD CURRENT CARD
            JsonObject currCard = o.getAsJsonObject();

            ProductionCard c = buildProductionCardFromJsonObject(currCard);
            cards.add(c);
        }
        return cards;
    }

    public static ProductionCard buildProductionCardFromJsonObject(JsonObject currCard)
    {
        //Preparing the Lists of resources
        List<Resource> cost = new ResourceList();
        List<Resource> obtained= new ResourceList();
        List<Resource> raw = new ResourceList();



        //GET LEVEL,VictoryPoint,faith, and type of card
        int level = currCard.get("level").getAsInt();
        int victoryPoints = currCard.get("victoryPoints").getAsInt();
        int obtainedFaith = currCard.get("obtainedFaith").getAsInt();
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

        ProductionCard c = new ProductionCard(cost,raw,obtained,victoryPoints,level,obtainedFaith,type);
        return c;
    }
    /**
     * Given a JSON array of resources build a ResourceList from it
     * @param array JSONarray full of ressources
     * @return ResourceList
     */
    public static List<Resource> buildResourceListFromJsonArray(JsonArray array)
    {
        List<Resource> list = new ResourceList();
        if(array.size() == 0) return list;
        for(JsonElement o:array)
        {
            JsonObject res = o.getAsJsonObject();

            ResourceType t = ResourceType.valueOf(res.get("type").getAsString());
            int qty = res.get("quantity").getAsInt();

            list.add(new Resource(t,qty));
        }

        return list;
    }

    /**
     * Generate a list of prerequisite from json array
     * @return a list of prerequisite
     */
    public static List<PrerequisiteCard> buildPrerequisiteCardListFromJsonArray(JsonArray array)
    {
        List<PrerequisiteCard> list = new ArrayList<>();
        if(array.size() == 0) return list;

        for(JsonElement o:array)
        {
            JsonObject card = o.getAsJsonObject();

            CardType type = CardType.valueOf(card.get("type").getAsString());
            int level = card.get("level").getAsInt();
            int quantity = card.get("quantity").getAsInt();

            list.add(new PrerequisiteCard(type,level,quantity));
        }

        return list;
    }
    /**
     * Generate a leader card from jsonObject
     * @param card a json Object containing a card
     * @return a new leader
     */
    public static LeaderCard buildLeaderCardFromJsonObject(JsonObject card) {
        ResourceType type = ResourceType.valueOf(card.get("resourceType").getAsString());
        int victoryPoint = card.get("victoryPoints").getAsInt();
        List<PrerequisiteCard> requirmentCard = buildPrerequisiteCardListFromJsonArray(card.get("requirementsCard").getAsJsonArray());
        List<Resource> requirment = buildResourceListFromJsonArray(card.get("requirementsResource").getAsJsonArray());

        String cardType = card.get("cardType").getAsString();

        LeaderCard leaderCard = null;
        switch (cardType) {
            case "WHITE":
                leaderCard = new LeaderWhiteCard(requirment,requirmentCard, victoryPoint, type);
                break;
            case "DISCOUNT":
                leaderCard =  new LeaderDiscountCard(requirment,requirmentCard, victoryPoint, type);
                break;
            case "TRADE":
                leaderCard =  new LeaderTradeCard(requirment,requirmentCard, victoryPoint, type);
                break;
            case "DEPOSIT":
                leaderCard =  new DepositBonus(requirment,requirmentCard, victoryPoint, type);
                break;
            default:
                leaderCard =  new LeaderCard(requirment,requirmentCard, victoryPoint, type);
                break;
        }
        leaderCard.setCliRappresentation(cardType);
        return leaderCard;
    }


        public static LeaderCard[] loadLeaderCardsFromJsonFile()
        {
            String path = "json/leaderCards.json";
            InputStream is = CardFactory.class.getClassLoader().getResourceAsStream(path);

            if (is == null) try {
                throw new Exception("File " + path + " not found");
            } catch (Exception e) {
                e.printStackTrace();
            }

            JsonParser parser = new JsonParser();
            JsonArray array = parser.parse(new InputStreamReader(is)).getAsJsonArray();

            int size = array.size();
            List<LeaderCard> leaders = new ArrayList<>();

            for(int i=0;i<size;i++)
            {
                JsonObject card = array.get(i).getAsJsonObject();
                leaders.add(buildLeaderCardFromJsonObject(card));
            }

            Collections.shuffle(leaders);
            LeaderCard [] out = new LeaderCard[size];

            return leaders.toArray(out);
        }


}
