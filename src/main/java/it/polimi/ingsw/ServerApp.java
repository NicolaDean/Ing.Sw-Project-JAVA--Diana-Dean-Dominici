package it.polimi.ingsw;

import it.polimi.ingsw.controller.ServerController;

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
    List<ServerController>          availableControllers;

    //Potrei mantenere il client in una "waitingRoom" finche non arriva un messaggio di login
    public ServerApp(int port)
    {
        this.executor= Executors.newCachedThreadPool();
        this.port = port;
        this.availableControllers = new ArrayList<>();
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

        ServerApp serverApp = new ServerApp(1234);
        serverApp.start();

    }

}
