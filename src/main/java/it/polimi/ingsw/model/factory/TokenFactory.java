package it.polimi.ingsw.model.factory;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.enumeration.CardType;
import it.polimi.ingsw.model.lorenzo.token.*;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Stack;

public class TokenFactory {

    public static Stack<BasicToken> loadTokenDeckFromJson(ServerController controller)
    {
        String path = "json/token.json";
        InputStream is = TokenFactory.class.getClassLoader().getResourceAsStream(path);

        if (is == null) try {
            throw new Exception("File " + path + " not found");
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonParser parser = new JsonParser();
        JsonArray array = parser.parse(new InputStreamReader(is)).getAsJsonArray();

        return elaborateJsonArray(array,controller);
    }


    public static Stack<BasicToken> elaborateJsonArray(JsonArray array, ServerController controller)
    {
        Stack<BasicToken> out = new Stack<>();

        for(JsonElement element:array)
        {
            JsonObject obj = element.getAsJsonObject();

            String type = obj.get("type").getAsString();
            String id = obj.get("id").getAsString();
            int    qty  = obj.get("quantity").getAsInt();

            for(int i=0;i<qty;i++)
            {
                BasicToken token;
                switch (type)
                {
                    case "DiscardCard":
                        CardType cardType = CardType.valueOf(obj.get("cardType").getAsString());
                        int      discard  =  obj.get("discard").getAsInt();
                        token = new ColoredActionToken(cardType,discard);
                        break;
                    case "OneCross":
                        token = new SpecialBlackCrossToken();
                        break;
                    case "TwoCross":
                        token = new BlackCrossToken();
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + type);
                }

                token.setObserver(controller);
                token.setId(id);
                out.add(token);
            }

        }
        Collections.shuffle(out);
        return out;
    }
}
