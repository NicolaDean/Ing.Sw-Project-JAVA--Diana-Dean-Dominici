package it.polimi.ingsw.controllerTest;
import it.polimi.ingsw.controller.interpreters.JsonInterpreterServer;
import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.controller.packets.*;
import it.polimi.ingsw.controller.packets.ACK;
import it.polimi.ingsw.controller.packets.MarketResult;
import it.polimi.ingsw.controller.packets.PendingCost;
import it.polimi.ingsw.controller.packets.UpdatePosition;
import it.polimi.ingsw.enumeration.ResourceType;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.ProductionCard;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceList;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.enumeration.ResourceType.*;

public class JsonInterpreterTest {

    @Test
    public void dispatchingTest()
    {

        String ack          = "{\"type\":\"ACK\",\"content\":{\"errorMSG\":3}}";
        String updatePos    = "{\"type\":\"UpdatePosition\",\"content\":{\"position\" : 2,\"player\" : 1 }}";


        JsonInterpreterServer interpreter = new JsonInterpreterServer(1,new ServerController(true));


        System.out.println("----------------------------------");
        System.out.println("JSON TEST-------------------------");

        //Generate an ACK
        ACK a = new ACK(2);
        //interpreter.analyzePacket(ack);

        //Generate an update Pos packet
        UpdatePosition pos = new UpdatePosition(1,2);
        //interpreter.analyzePacket(updatePos);

        //Try to generate json from the packet created above
        System.out.println("JSON1: -> " +a.generateJson());
        //interpreter.analyzePacket( a.generateJson());
        System.out.println("JSON2: -> " +pos.generateJson());
        //interpreter.analyzePacket( pos.generateJson());


        Production prod = new Production(2);
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

        System.out.println(log1.generateJson());

        JsonInterpreterServer interpreter = new JsonInterpreterServer(1,new ServerController(true));

        interpreter.analyzePacket(log1.generateJson());
        interpreter.analyzePacket(log2.generateJson());
        interpreter.analyzePacket(log3.generateJson());
        interpreter.analyzePacket(log4.generateJson());
        interpreter.analyzePacket(log5.generateJson());

        interpreter.getResponse();
    }

    public void ProductionTest()
    {
        System.out.println("----------------------------------");
        Login log1   = new Login("Nicola");
        Login log2  = new Login("Federico");
        Login log3   = new Login("Riccardo");
        Login log4  = new Login("Biagio");

        JsonInterpreterServer interpreter = new JsonInterpreterServer(0,new ServerController(true));

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
        Production prod = new Production(0);
        interpreter.analyzePacket(prod.generateJson());
        interpreter.getResponse();

        System.out.println("Production with correct resource TEST");
        //Shouuld go well
        p.chestInsertion(card.getRawMaterials());
        interpreter.analyzePacket(prod.generateJson());
        interpreter.getResponse();
        System.out.println("----------------------------------");

        ServerController s = new ServerController(true);
    }

    @Test
    public void AllPacketGeneration()
    {
        List<Resource> resourceList = new ResourceList();
        resourceList.add(new Resource(COIN,1));
        resourceList.add(new Resource(ROCK,2));

        List<ExtractionInstruction> ins = new ArrayList<>();
        ins.add(new ExtractionInstruction(new Resource(COIN,1)));
        ins.add(new ExtractionInstruction(new Resource(ROCK,1)));
        ins.add(new ExtractionInstruction(new Resource(SERVANT,1),1));

        JsonInterpreterServer interpreter = new JsonInterpreterServer(0,new ServerController(true));
        System.out.println("----------------------------------");
        System.out.println("Packets the server is able to handle:");
        System.out.println("----------------------------------");

        System.out.println( new Login("Nicola").generateJson());
        System.out.println( new UpdatePosition(1,1).generateJson());
        System.out.println( new Production(1).generateJson());
        System.out.println( new BasicProduction(COIN,SERVANT,ROCK, 0).generateJson());
        System.out.println( new BonusProduction(1,ROCK).generateJson());
        System.out.println( new BuyCard(1,2,1).generateJson());
        System.out.println( new PendingCost(resourceList).generateJson());
        System.out.println( new MarketExtraction(false,1).generateJson());
        System.out.println( new StorageMassExtraction(false,ins).generateJson());
        System.out.println( new ActivateLeader(1,false).generateJson());
        System.out.println( new DiscardResource(1, COIN).generateJson());
        System.out.println( new SwapDeposit(1,2).generateJson());
        System.out.println( new SetTurnType(2).generateJson());
        System.out.println( new EndTurn().generateJson());
        System.out.println( new MarketResult(resourceList,2).generateJson());
        System.out.println( new Pong(1).generateJson());
        System.out.println( new StartGame().generateJson());
    }

    public void marketTest()
    {
        System.out.println("----------------------------------");
        Login log1   = new Login("Nicola");
        Login log2  = new Login("Federico");
        Login log3   = new Login("Riccardo");
        Login log4  = new Login("Biagio");

        JsonInterpreterServer interpreter = new JsonInterpreterServer(0,new ServerController(true));

        interpreter.analyzePacket(log1.generateJson());
        interpreter.analyzePacket(log2.generateJson());
        interpreter.analyzePacket(log3.generateJson());
        interpreter.analyzePacket(log4.generateJson());

        interpreter.analyzePacket(new MarketExtraction(false,3).generateJson());
        interpreter.getResponse();

    }

    public void lorenzoTest(){
        System.out.println("----------------------------------");
        LoginSinglePlayer log   = new LoginSinglePlayer("SuperRichi99XDXDXD");

        JsonInterpreterServer interpreter = new JsonInterpreterServer(0,new ServerController(true));

        interpreter.analyzePacket(log.generateJson());

        interpreter.analyzePacket(new StartGame().generateJson());


        interpreter.analyzePacket(new EndTurn().generateJson());
        interpreter.getResponse();
    }

    @Test
    public void gameStartedTest() throws IOException, ClassNotFoundException {

        ObjectInputStream objectinputstream = null;
        FileInputStream streamIn = new FileInputStream("save.ser");
        objectinputstream = new ObjectInputStream(streamIn);
        Game g = (Game) objectinputstream.readObject();

        g.checkEndGame();
    }

}
