package it.polimi.ingsw.view.utils;

public  class DebugMessages {

    public static boolean enableError = true;
    public static boolean enableWarning = true;
    public static boolean enableNetwork= false;
    public static boolean enableGeneric = true;
    public static Logger log = new Logger();

    public static void printError(String msg)
    {
        if(enableError)
        {
            log.printError(msg);
        }
    }

    public static void printWarning(String msg)
    {
        if(enableWarning)
        {
            log.printError(msg);
        }
    }

    public static void printNetwork(String msg)
    {
        if(enableNetwork)
        {
            System.out.println(msg);
        }
    }
    public static void printGeneric(String msg)
    {
        if(enableGeneric)
        {
            System.out.println(msg);
        }
    }
}
