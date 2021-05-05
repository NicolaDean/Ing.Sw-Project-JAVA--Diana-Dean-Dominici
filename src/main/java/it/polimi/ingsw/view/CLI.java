package it.polimi.ingsw.view;

import it.polimi.ingsw.utils.Logger;

public class CLI implements View{

    Logger terminal; //print formatted and colored text on the cli

    public CLI()
    {
        terminal = new Logger();
    }
}
