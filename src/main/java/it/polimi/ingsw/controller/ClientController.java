package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.interpreters.JsonInterpreterClient;
import it.polimi.ingsw.controller.packets.Login;
import it.polimi.ingsw.controller.packets.LoginSinglePlayer;
import it.polimi.ingsw.controller.packets.Packet;
import it.polimi.ingsw.utils.ErrorManager;
import it.polimi.ingsw.view.CLI;
import it.polimi.ingsw.view.View;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientController implements Runnable{

    private Socket server;
    private Scanner input;
    private PrintWriter output;

    private JsonInterpreterClient interpreter;
    private PongController        pongController;
    private int                   index;
    private boolean               connected;

    private ErrorManager          errorManager;
    private View                  view;   //Interface with all view methods



    public ClientController(boolean type)
    {
        this.connected = false;
        if(type)view = new CLI();
        //else view = new GUI()

    }

    public void exampleACK(int code)
    {
        errorManager.getErrorMessageFromCode(code);//TODO magari oltre al numero passo la view che chiamera "showError"
    }


    public void setConnected(boolean conn)
    {
        this.connected = conn;
    }
    public void setIndex(int index) {

        if(!connected)
        {
            this.index = index;
            this.setConnected(true);
            this.pongController = new PongController(index,output);
            new Thread(this.pongController);
        }
    }

    public PongController getPongController() {
        return pongController;
    }

    public void startGame()
    {
        //View.HomePage()
        System.out.println("Start Game");
    }
    /**
     * Open a connection with this server
     * @param ip server ip
     * @param port server port
     */
    public void selectServer(String ip,int port) {
        try {
            this.interpreter = new JsonInterpreterClient(this);
            this.server = new Socket(ip,port);
            initializeReader(server);
            initializeWriter(server);
            new Thread(this);//create input messages manager thread

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initialize the Input stream of the socket
     * @param s
     */
    public void initializeReader(Socket s)
    {
        try {
            this.input  = new Scanner(s.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initialize the Output stream of the socket
     * @param s
     */
    public void initializeWriter(Socket s)
    {
        try {
            this.output = new PrintWriter(s.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setNickname(String nickname,boolean singlePlayer)
    {
        if(singlePlayer)
            sendMessage(new LoginSinglePlayer(nickname));
        else
            sendMessage(new Login(nickname));
    }


    public void sendMessage(Packet p)
    {
        this.output.println(p.generateJson());
        this.output.flush();
    }
    /**
     * wait server messages
     */
    public void waitMessage()
    {
        String message = this.input.nextLine();

        try
        {
            this.interpreter.analyzePacket(message);
            System.out.println("Recived command:" + message);
        }catch (Exception e)
        {
            System.out.println("Not a json Message: "+ message);
        }

    }
    @Override
    public void run() {
        //Thread con server
        System.out.println("Waiting message thread chreated");
        while(this.connected)
        {
            this.waitMessage();
        }
    }
}
