package it.polimi.ingsw.controllerTest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.controller.BasicJsonInterpreter;
import it.polimi.ingsw.controller.JsonInterpreterServer;
import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.controller.packets.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.ProductionCard;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceList;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.util.List;

import static it.polimi.ingsw.enumeration.ResourceType.*;

public class JsonInterpreterTest {

    @Test
    public void dispatchingTest()
    {

        String ack          = "{\"type\":\"ACK\",\"content\":{\"errorMSG\":3}}";
        String updatePos    = "{\"type\":\"UpdatePosition\",\"content\":{\"position\" : 2,\"player\" : 1 }}";


        JsonInterpreterServer interpreter = new JsonInterpreterServer(1,new ServerController());


        System.out.println("----------------------------------");
        System.out.println("JSON TEST-------------------------");

        //Generate an ACK
        ACK a = new ACK(2);
        interpreter.analyzePacket(ack);

        //Generate an update Pos packet
        UpdatePosition pos = new UpdatePosition(1,2);
        interpreter.analyzePacket(updatePos);

        //Try to generate json from the packet created above
        System.out.println("JSON1: -> " +a.generateJson());
        interpreter.analyzePacket( a.generateJson());
        System.out.println("JSON2: -> " +pos.generateJson());
        interpreter.analyzePacket( pos.generateJson());


        Production prod = new Production(2,1);
        System.out.println("JSON3: -> " +prod.generateJson());

        System.out.println("----------------------------------");
    }

    @Test
    public void LoginTest()
    {
        Login log1   = new Login("Nicola");
        Login log2  = new Login("Federico");
        Login log3   = new Login("Riccardo");
        Login log4  = new Login("Biagio");
        Login log5  = new Login("Marco");

        JsonInterpreterServer interpreter = new JsonInterpreterServer(1,new ServerController());

        interpreter.analyzePacket(log1.generateJson());
        interpreter.analyzePacket(log2.generateJson());
        interpreter.analyzePacket(log3.generateJson());
        interpreter.analyzePacket(log4.generateJson());
        interpreter.analyzePacket(log5.generateJson());

        interpreter.getResponse();
    }

    @Test
    public void ProductionTest()
    {
        System.out.println("----------------------------------");
        Login log1   = new Login("Nicola");
        Login log2  = new Login("Federico");
        Login log3   = new Login("Riccardo");
        Login log4  = new Login("Biagio");

        JsonInterpreterServer interpreter = new JsonInterpreterServer(0,new ServerController());

        interpreter.analyzePacket(log1.generateJson());
        interpreter.analyzePacket(log2.generateJson());
        interpreter.analyzePacket(log3.generateJson());
        interpreter.analyzePacket(log4.generateJson());


        Game g = interpreter.getController().getGame();
        Player p =  interpreter.getController().getGame().getPlayers().get(interpreter.getPlayerIndex());

        System.out.println(p.getNickname());
        ProductionCard card  = g.getProductionDecks()[0][0].peek();

        p.chestInsertion(card.getCost());

        try {
            card.buy(p,interpreter.getPlayerIndex());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //should get an error NACK (not enough money)
        System.out.println("Production with no resource TEST");
        Production prod = new Production(0,interpreter.getPlayerIndex());
        interpreter.analyzePacket(prod.generateJson());
        interpreter.getResponse();

        System.out.println("Production with correct resource TEST");
        //Shouuld go well
        p.chestInsertion(card.getRawMaterials());
        interpreter.analyzePacket(prod.generateJson());
        interpreter.getResponse();
        System.out.println("----------------------------------");



    }
    @Test
    public void ResourceListTest()
    {



    }
}
