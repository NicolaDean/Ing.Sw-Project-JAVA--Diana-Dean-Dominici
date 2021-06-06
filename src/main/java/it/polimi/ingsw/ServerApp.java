package it.polimi.ingsw;

import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.utils.DebugMessages;
import it.polimi.ingsw.utils.ConstantValues;

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
    List<ServerController>          startedControllers;

    public ServerApp(int port)
    {
        this.executor             = Executors.newCachedThreadPool();
        this.port                 = port;
        this.availableControllers = new ArrayList<>();
        this.matchId              = 0;
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
            executor.submit(new WaitingRoom(socket,availableControllers, new ServerController(false),executor));

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
            i++;
        }

        System.out.println("Port "+ port);
        ServerApp serverApp = new ServerApp(port);
        serverApp.start();

    }

}
