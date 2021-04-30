package it.polimi.ingsw;

import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.controller.interpreters.JsonInterpreterServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable {

    private Socket          socket;
    private Scanner         input;
    private PrintWriter     output;
    JsonInterpreterServer   interpreter;


    public ClientHandler(Socket client,ServerController controller)
    {

        this.interpreter= new JsonInterpreterServer(0,controller);
        this.socket = client;

        this.initializeReader(client);
        this.initializeWriter(client);

    }

    @Override
    public void run() {
        waitClientMassages();
    }

    public void initializeReader(Socket s)
    {
        try {
            this.input  = new Scanner(s.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void initializeWriter(Socket s)
    {
        try {
            this.output = new PrintWriter(s.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void waitClientMassages()
    {
        try {
            while (true) {
                String message = this.input.nextLine();
                if (message.equals("quit")) {
                    System.out.println("Client " + socket.getInetAddress() + "Exit the server");
                    break;
                } else {
                    readMessage(message);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readMessage(String message)
    {
        try {
            interpreter.analyzePacket(message);
            respondToClient();

            System.out.println("COMMAND: -> " + message);
        } catch (Exception e) {
            System.out.println("Not JSON MESSAGE: " + message);
        }
    }

    public void respondToClient()
    {
        try {
            interpreter.getResponse();
            output.println(interpreter.getResponse());
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
