package it.polimi.ingsw;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.controller.interpreters.JsonInterpreterClient;
import it.polimi.ingsw.controller.packets.Login;
import it.polimi.ingsw.controller.packets.StartGame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;


public class ClientApp {

    ClientController controller;
    int port;


    public ClientApp()
    {

    }

    public void setViewType(boolean type)
    {
        this.controller = new ClientController(type);
    }

    /**
     * Show welcome page
     */
    public void start() throws IOException {


        String address = "0";
        /*while (address.equals("0"))
            address=iprequest();
        int port = portrequest();*/
        //String nickname = nicknamerequest();
        //this.controller.connectToServer(address,port);

        this.controller.startGame();
        //this.controller.sendMessage(new Login("Fede"));
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.controller.sendMessage(new StartGame());
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.controller.starttolisten();

    }

    public String nicknamerequest() throws IOException {
        System.out.println("\ninserisci il tuo nickname");
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        return  reader.readLine();

    }

    public static void main(String[] args) throws IOException {
        ClientApp app = new ClientApp();
        app.setViewType(true);//CLI poi il bool verra caricato da args


        app.start();

    }



    public static String iprequest() throws IOException {
        System.out.println("inserisci l'indirizzo del server a cui vuoi collegarti (\"0\" per localhost)");
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        String address = reader.readLine();
        if(address.equals("0"))
            address = "127.0.0.1";

        if (isValidInet4Address(address))
            return address;
        else {
            System.out.println("indirizzo ip non valido. \n");
            return "0";
        }



    }

    public static int portrequest() throws IOException {
        System.out.println("\ninserisci la porta a cui vuoi collegarti (\"0\" per 1234)");
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));

        String port123 = reader.readLine();
        if(port123.equals("0"))
            port123 = "1234";
        int port = Integer.parseInt(port123);
        return port;

    }

    public static boolean isValidInet4Address(String ip)
    {
        String[] groups = ip.split("\\.");

        if (groups.length != 4) {
            return false;
        }

        try {
            return Arrays.stream(groups)
                    .filter(s -> s.length() >= 1 )
                    .map(Integer::valueOf)
                    .filter(i -> (i >= 0 && i <= 255))
                    .count() == 4;
        } catch (NumberFormatException e) {
            System.out.println("-------");
            e.printStackTrace();
            return false;
        }
    }


}
