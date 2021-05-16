package it.polimi.ingsw;

import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.controller.interpreters.JsonInterpreterServer;
import it.polimi.ingsw.controller.packets.Packet;
import it.polimi.ingsw.controller.pingManager.PingController;
import it.polimi.ingsw.view.utils.CliColors;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable {

    private final Socket    socket;
    private Scanner         input;
    private PrintWriter     output;
    JsonInterpreterServer   interpreter;
    private int index;
    private int realPlayerIndex;
    private boolean ping = false;
    private PingController pingController;


    //TODO aggiungere una funzione nel game "getIndexFromIndex" che viene chiamata quando mischio i giocatori
    public ClientHandler(Socket client,ServerController controller)
    {

        this.interpreter= new JsonInterpreterServer(0,controller);
        this.socket = client;

        this.initializeReader(client);
        this.initializeWriter(client);

    }

    public PingController getPingController()
    {
        return this.pingController;
    }

    public void setRealPlayerIndex(int index)
    {
        this.realPlayerIndex = index;
    }
    public int getRealPlayerIndex() {
        return realPlayerIndex;
    }

    public PingController initializePingController(ServerController controller)
    {
        this.pingController = new PingController(index,output);
        this.pingController.setObserver(controller);
        return this.pingController;
    }
    public void warning(String msg)
    {
        CliColors c = new CliColors(System.out);
        c.printlnColored(msg,CliColors.YELLOW_TEXT);
    }
    public boolean isPing() {
        return ping;
    }

    public boolean setPing() {
        return ping = false;
    }

    public void setIndex(int index) {
        this.interpreter.setPlayerIndex(index);
        if(this.pingController!=null) this.pingController.setIndex(index);
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public JsonInterpreterServer getInterpreter() {
        return interpreter;
    }

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
       // System.out.println("Exit loop");
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
                System.out.println("RESPONSE : -> " + response);
                output.println(response);
                output.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void sendToClient(Packet p)
    {

        System.out.println(p.generateJson());
        output.println(p.generateJson());
        output.flush();
    }


}
