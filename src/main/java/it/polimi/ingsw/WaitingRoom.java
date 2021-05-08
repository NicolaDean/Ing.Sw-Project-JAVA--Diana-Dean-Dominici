package it.polimi.ingsw;

import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.controller.interpreters.JsonInterpreterServer;
import it.polimi.ingsw.view.utils.CliColors;
import it.polimi.ingsw.view.utils.Logger;

import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class WaitingRoom extends ClientHandler{


    List<ServerController> controllers;
    ServerController fakeController;
    private final ExecutorService executor;
    int currentClient = 0;

    public WaitingRoom(Socket socket,List<ServerController> controllers,ServerController fakeController,ExecutorService executor)
    {
        super(socket,fakeController);
        this.fakeController = fakeController;
        this.controllers = controllers;
        this.executor = executor;
    }


    @Override
    public void run() {
        System.out.println("Waiting room...");
        waitClientMassages();
    }

    /**
     *
     * @return true if the player Logged in (Game players list of fake controller is not empty)
     */
    @Override
    public boolean exitCondition()
    {
        boolean out = !(fakeController.getGame().getPlayers().size() == 1);

        return out;
    }

    /**
     * execute packet on fake controller (becouse not logged yet)
     * @param message packets recived
     */
    @Override
    public void readMessage(String message)
    {
        System.out.println("------------------------------------------");
        try {

            this.getInterpreter().analyzePacket(message); //
            this.checkLogin(message);
            System.out.println("COMMAND: -> " + message);
        } catch (Exception e) {
            System.out.println("NotJson: -> " + message);
        }
        System.out.println("------------------------------------------");

    }

    /**
     *  if the client logged to the fake client this function redirect the login packet to a real controller/game
     * @param message recived packet
     * @return
     */
    public void  checkLogin(String message)
    {
        boolean out = exitCondition();
        if(!out)
        {
            ServerController c;
            if(this.interpreter.getController().isSinglePlayer())
            {
                //Create single player
                c = new ServerController();
            }
            else
            {
                //Find a free controller
                c = findFreeController();
            }

            //Create new ClientHandler with this controller
            ClientHandler handler = new ClientHandler(this.getSocket(),c);
            //Add Handler to Real Controller
            c.addClient(handler);

            handler.interpreter.analyzePacket(message); //Login,  this time on a real controller
            handler.respondToClient();



            //Create Thread
            this.createRealClientThread(handler);
        }
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
     * Find a non full match if available, else ccreate new one
     */

    public ServerController findFreeController()
    {
        Logger terminal = new Logger();
        System.out.println("---------------FIND MATCH-------------------");
        int i=0;
        for(ServerController controller:controllers)
        {
            if(!controller.isFull())
            {
                terminal.out.printColored("Player logged to the "+ i + "^ Match", CliColors.GREEN_TEXT,CliColors.BLACK_BACKGROUND);
                return controller;
            }
            i++;
        }
        if(i>0)
            System.out.println("All match Full, new one created");
        terminal.out.printColored("Player logged to the "+ i + "^ Match", CliColors.GREEN_TEXT,CliColors.BLACK_BACKGROUND);


        ServerController c = new ServerController(true);
        c.setIdpartita(i);
        controllers.add(c);
        return c;
    }


}
