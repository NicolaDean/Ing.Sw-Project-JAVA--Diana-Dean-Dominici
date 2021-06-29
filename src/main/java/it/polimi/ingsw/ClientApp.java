package it.polimi.ingsw;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.view.GuiHelper;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;


/**
 * simply read arguments and load server,Cli,Gui
 */
public class ClientApp {

    ClientController controller;

    public ClientApp()
    {

    }

    /**
     *
     * @param type false = GUI true = CLI
     */
    public void setViewType(boolean type)
    {
        this.controller = new ClientController(type);
    }

    /**
     * Show welcome page
     */
    public void start() throws IOException {
        this.controller.startGame();

    }


    /**
     *
     * @param args -cli / -c / -s / -server / -gui / -g / -p / -port
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        ClientApp app = new ClientApp();

        int i=0;
        boolean viewType = false;
        boolean server   = false;

        for(String arg: args)
        {
            if(arg.equals("-cli")||arg.equals("-c"))
            {
                viewType = true;
            }

            if(arg.equals("-gui")||arg.equals("-g"))
            {
                viewType = false;
            }

            if(arg.equals("-server")||arg.equals("-s"))
            {
                server = true;
            }
            i++;
        }


        if(server)
        {
            ServerApp.main(args);
            return;
        }

        app.setViewType(viewType);//CLI poi il bool verra caricato da args
        app.start();

    }

}
