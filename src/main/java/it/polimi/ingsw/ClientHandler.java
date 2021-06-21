package it.polimi.ingsw;

import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.controller.interpreters.JsonInterpreterServer;
import it.polimi.ingsw.controller.packets.Packet;
import it.polimi.ingsw.controller.pingManager.PingController;
import it.polimi.ingsw.utils.DebugMessages;
import it.polimi.ingsw.view.utils.CliColors;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable, Serializable {

    transient private Socket    socket;
    transient private Scanner         input;
    transient private PrintWriter     output;
    transient JsonInterpreterServer   interpreter;
    transient private Object    lock;
    private int index;
    private int realPlayerIndex;
    private boolean ping = false;
    transient private PingController pingController;


    //TODO aggiungere una funzione nel game "getIndexFromIndex" che viene chiamata quando mischio i giocatori
    public ClientHandler(Socket client,ServerController controller)
    {

        this.interpreter= new JsonInterpreterServer(0,controller);
        this.socket = client;

        this.initializeReader(client);
        this.initializeWriter(client);
        lock = new Object();

    }

    public boolean checkSocket()
    {
        return this.socket!=null;
    }

    public void disconnect()
    {
        this.input.close();
        this.output.close();

        try {
            this.socket.close();
        } catch (IOException e) {
            DebugMessages.printWarning("Client aborted for endgame");
        }
    }

    /**
     * get the ping controller (its needed to setPing)
     * @return ping controller associated with this client controller
     */
    public PingController getPingController()
    {
        return this.pingController;
    }

    /**
     *  comunicate to this client handler the index of its assigned player
     * @param index the player index inside "players" model inside GAME class
     */
    public void setRealPlayerIndex(int index)
    {
        //this.interpreter.setPlayerIndex(index);
        this.realPlayerIndex = index;
    }

    /**
     *
     * @return the player index inside "players" model inside GAME class
     */
    public int getRealPlayerIndex() {
        return realPlayerIndex;
    }

    /**
     * initialize pingController class by setting ServerController  as an observer
     * @param controller sererController that contain this clientHandler
     * @return
     */
    public PingController initializePingController(ServerController controller)
    {
        this.pingController = new PingController(index,output);
        this.pingController.setObserver(controller);
        return this.pingController;
    }

    /**
     * set the index of this client inside serverController list
     * @param index
     */
    public void setIndex(int index) {
        this.interpreter.setPlayerIndex(index);
        if(this.pingController!=null) this.pingController.setIndex(index);
        this.index = index;
    }

    /**
     *
     * @return the client index inside "clients" list of serverController
     */
    public int getIndex() {
        return index;
    }

    /**
     *
     * @return the json interpreter working inside this client
     */
    public JsonInterpreterServer getInterpreter() {
        return interpreter;
    }

    /**
     *
     * @return client socket
     */
    public Socket getSocket() {
        return socket;
    }

    @Override
    public void run() {
        waitClientMassages();
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

    /**
     * Exit condition of the waiting room
     * @return
     */
    public boolean exitCondition()
    {
        return  true;
    }

    /**
     * Wait message from the client
     */
    public void waitClientMassages()
    {
        boolean flag = true;
            while (flag) {
                //System.out.println("wait command");
                String message = this.input.nextLine();
                if (message.equals("quit")) {
                    System.out.println("Client " + socket.getInetAddress() + " Exited the server");
                    break;
                } else {
                    readMessage(message);
                    flag = exitCondition();
                }

            }
        System.out.println("Exit Waiting room");
    }

    /**
     * Read a message and analyze it, then get a response if available and send it back
     * @param message
     */
    public void readMessage(String message)
    {
        try {
            interpreter.analyzePacket(message);
            respondToClient();

            System.out.println("COMMAND: -> " + message);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Not JSON MESSAGE: " + message);
           // e.printStackTrace();
        }
    }

    /**
     * Get response if available and send it back
     */

    public void respondToClient()
    {
        try {
            String response = interpreter.getResponse();
            if(response!=null)
            {
                synchronized (lock) {
                System.out.println("RESPONSE : -> " + response);
                    output.println(response);
                    output.flush();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkLock()
    {
        if(lock==null) lock = new Object();
    }
    /**
     * send a message to this specific client
     * @param p packet to send
     */
    public void sendToClient(Packet p)
    {
        //TODO CHECK WHY LOCK IS NULL
        //Avoid using output channel at the same time
        if(lock==null)this.lock = new Object();
        synchronized (lock)
        {
            System.out.println(p.generateJson());
            output.println(p.generateJson());
            output.flush();
            lock.notify();
        }

    }

    /**
     * Add new Data to disconnected client handler (new interpreter,new socket....)
     * Used when a client try to reconnect to this server
     * @param socket        new server
     * @param clientIndex   player index
     * @param controller    match
     */

    public void reconnect(Socket socket,int clientIndex,ServerController controller) {
        this.socket = socket;
        this.lock   = new Object();
        this.initializeReader(socket);
        this.initializeWriter(socket);

        this.interpreter = new JsonInterpreterServer(clientIndex,controller);

        this.setIndex(clientIndex);

        new Thread(this.initializePingController(controller)).start();
        this.pingController.setGameStarted();

    }
}
