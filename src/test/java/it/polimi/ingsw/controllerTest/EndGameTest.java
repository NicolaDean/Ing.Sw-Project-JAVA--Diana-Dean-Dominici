package it.polimi.ingsw.controllerTest;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.controller.packets.EndGame;
import it.polimi.ingsw.controller.packets.Packet;
import it.polimi.ingsw.model.Player;
import org.junit.jupiter.api.Test;

public class EndGameTest {
    @Test
    public void serverControlChartsCheck(){
        ServerController server = new ServerController(true);
        try {
            server.getGame().addPlayer("richi");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            server.getGame().addPlayer("fede");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            server.getGame().addPlayer("nico");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            server.getGame().addPlayer("franco");
        } catch (Exception e) {
            e.printStackTrace();
        }
        server.getGame().getPlayers().get(3).increaseScore(3);
        server.getGame().getPlayers().get(2).increaseScore(1);
        server.getGame().getPlayers().get(1).increaseScore(3);
        server.getGame().getPlayers().get(0).increaseScore(5);
        Packet p=new EndGame(server.exstractCharts());
        System.out.println("classifica vera:");
        for (Player i:server.getGame().getPlayers())
            System.out.println(i.getNickname()+" VP:"+i.getScore());
        System.out.println("classifica estratta:");
        ClientController c = new ClientController(true);
        try {
            p.analyze(c);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
