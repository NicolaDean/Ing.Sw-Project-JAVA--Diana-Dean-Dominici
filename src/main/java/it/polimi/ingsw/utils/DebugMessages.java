package it.polimi.ingsw.utils;

import it.polimi.ingsw.view.utils.Logger;

/**
 * this class contain static method to print colored error/messages
 * -> those messages can be disabled by changing the statics boolean inside this class
 */
public  class DebugMessages {

    //DEBUG CHEATS
    public static boolean infiniteResourcesStorage = false;
    public static boolean infiniteResourcesChest   = true;
    public static boolean leaderFree               = false;

    //DEBUG MESSAGES
    public static boolean enableError = true;
    public static boolean enableWarning = true;
    public static boolean enableNetwork= false;
    public static boolean enableGeneric = true;
    public static boolean windowsDetection = false;
    public static Logger log = new Logger();

    /**
     * allow to print an error message in red
     * @param msg
     */
    public static void printError(String msg)
    {
        if(enableError)
        {
            log.printError(msg);
        }
    }

    /**
     * allow to print a warning message in yellow
     * @param msg
     */
    public static void printWarning(String msg)
    {
        if(enableWarning)
        {
            log.printError(msg);
        }
    }

    /**
     * allow to print network debugging stuff
     * @param msg
     */
    public static void printNetwork(String msg)
    {
        if(enableNetwork)
        {
            System.out.println(msg);
        }
    }

    /**
     * allow to print generic messages
     * @param msg
     */
    public static void printGeneric(String msg)
    {
        if(enableGeneric)
        {
            System.out.println(msg);
        }
    }
}
