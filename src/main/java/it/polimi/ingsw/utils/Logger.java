package it.polimi.ingsw.utils;

import java.io.PrintStream;
import java.io.PrintWriter;

public class Logger {

    CliColors out;

    public Logger()
    {
        out = new CliColors(System.out);
    }


    public void Welcome()
    {
        out.printColored("WELCOMEE TO LORENZO IL MAGNIFICO", CliColors.WHITE_BACKGROUND, CliColors.RED_TEXT);
    }
    public void printLogo()
    {

    }
}
