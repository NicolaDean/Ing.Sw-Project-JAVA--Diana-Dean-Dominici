package it.polimi.ingsw;

import it.polimi.ingsw.controller.LorenzoServerController;
import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.controller.packets.ACK;
import it.polimi.ingsw.controller.packets.Login;
import it.polimi.ingsw.controller.packets.LoginSinglePlayer;
import it.polimi.ingsw.controller.packets.Packet;
import it.polimi.ingsw.enumeration.ErrorMessages;
import it.polimi.ingsw.utils.DebugMessages;
import it.polimi.ingsw.utils.ConstantValues;
import it.polimi.ingsw.view.utils.CliColors;
import it.polimi.ingsw.view.utils.Logger;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerApp {

    private final ExecutorService   executor;
    private final int               port;
    private ServerSocket            serverSocket;
    private long                    matchId;
    List<ServerController>          availableControllers;
    List<ServerController>          closed;

    public ServerApp(int port)
    {
        this.executor             = Executors.newCachedThreadPool();
        this.port                 = port;
        this.availableControllers = new ArrayList<>();
        this.matchId              = 0;
    }


    /**
     * if a game ended add controller to closed list
     * In case Server crush this list will not Reload
     * If user try to reconnect this list is not used (cant reconnect to ended game)
     * @param controller controller to close
     */
    public void closeController(ServerController controller)
    {
        DebugMessages.printError("Controller ("+controller.getMatchId()+") ended match");
        this.closed.add(controller);
        this.availableControllers.remove(controller);
    }
    /**
     * Initialize the server and then wait for clients
     */
    public void start()
    {
        this.connection();
        this.waitNewClients();
    }

    /**
     * Initialize socket on the setted port
     */
    public void connection()
    {
        try {
            serverSocket = new ServerSocket(this.port);
            System.out.println("Server started on port :" + this.port);
        } catch (IOException e) {
            //Connessione fallita/Porta occupata
            System.err.println(e.getMessage());
            return;
        }
    }

    /**
     * Infinite loop that accept new client
     */
    public void waitNewClients()
    {
            while(true)
            {
                System.out.println("Wait Client");
                acceptClient();
            }
    }

    /**
     * wait a client, when a client try to connect this function create a dedicated thread to handle it
     */
    public void acceptClient()
    {
        try {
            Socket socket = serverSocket.accept();
            //executor.submit(new ClientHandler(socket,this.controller));
            ServerController fake = new ServerController(socket);
            fake.setObserver(this);
            executor.submit(new WaitingRoom(socket,availableControllers,fake ,executor));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        int port = ConstantValues.defaultServerPort;

        int i=0;
        for(String arg : args)
        {
            if(arg.equals("-port") || arg.equals("-p"))
            {
                try {
                    port = Integer.parseInt(args[i+1]);
                } catch (NumberFormatException e) {
                    DebugMessages.printError("Invalid argument,default port setted");
                    port = ConstantValues.defaultServerPort;
                }
            }
            if(arg.equals("-restart") || arg.equals("-r"))
            {
               //TODO load server from files
            }
            i++;
        }

        System.out.println("Port "+ port);
        ServerApp serverApp = new ServerApp(port);
        serverApp.start();

    }

    /**
     * When a user try to reconnect this function is called by fakeController observable
     * @param nickname nickame of reconnected player
     * @param id       match to reconnect
     * @param s        socket of the waiting room
     */
    public void reconnect(String nickname,long id,Socket s)
    {
        DebugMessages.printError("Try to reconnect to match " + id + " -> " + nickname);
        for(ServerController match:this.availableControllers)
        {
            if(match.getMatchId()==id)
            {
                //Create new ClientHandler with this controller
                ClientHandler handler = new ClientHandler(s,match);
                //Add Handler to Real Controller
                //Create Thread
                this.createRealClientThread(handler);

                match.reconnect(handler,nickname);
                handler.respondToClient();
            }
        }

    }

    /**
     *  execute login single player from fakeController
     * @param nickname
     * @param s         waiting room socket
     */
    public void singleLogin(String nickname,Socket s)
    {
        //Create single player
        ServerController c = new LorenzoServerController();
        c.setMatchId(this.availableControllers.size());
        availableControllers.add(c);

        createHandler(c,s,new LoginSinglePlayer(nickname));

    }

    /**
     * execute login for this player from fakecontroller
     * @param nickname client nickanme
     * @param s        waiting room socket
     */
    public void login(String nickname,Socket s)
    {
        ServerController c = findFreeController(nickname);
        createHandler(c,s,new Login(nickname));

        DebugMessages.printError("Login executed");
    }

    /**
     * create a clientHandler for a new user
     * @param c             server controller assigned to player
     * @param s             waiting room socket
     * @param redirect      login or single player packet
     */
    public void createHandler(ServerController c, Socket s, Packet redirect)
    {
        //Create new ClientHandler with this controller
        ClientHandler handler = new ClientHandler(s,c);
        //Add Handler to Real Controller
        c.addClient(handler);

        handler.interpreter.analyzePacket(redirect.generateJson()); //Login,  this time on a real controller
        handler.respondToClient();

        //Create Thread
        this.createRealClientThread(handler);
    }

    /**
     * Create a new Thread to handle the client connection
     * @param clientHandler
     */
    public void createRealClientThread(ClientHandler clientHandler)
    {
        //System.out.println("CREATE NEW  THREAD FOR CLIENT");
        //System.out.println("redirecting login packet to the match");
        this.executor.submit(clientHandler);
        System.out.println("------------------------------------------");
    }

    /**
     * Find the first  match available (if not exist create one)
     * @param nickname login nickname
     * @return the available controller or a new one if all occupated
     */
    public ServerController findFreeController(String nickname)
    {
        Logger terminal = new Logger();
        System.out.println("---------------FIND MATCH-------------------");
        int i=0;
        for(ServerController controller:availableControllers)
        {
            if(!controller.isFull(nickname))
            {
                //if(controller.isStarted()) this.controllers.remove(controller)
                terminal.out.printlnColored("Player logged to the "+ i + "^ Match", CliColors.GREEN_TEXT,CliColors.BLACK_BACKGROUND);
                return controller;
            }
            i++;
        }

        if(i>0)
            System.out.println("All match Full, new one created");
        terminal.out.printlnColored("Player logged to the "+ i + "^ Match", CliColors.GREEN_TEXT,CliColors.BLACK_BACKGROUND);


        ServerController c = new ServerController(true);
        c.setMatchId(i);
        availableControllers.add(c);
        return c;
    }

}
