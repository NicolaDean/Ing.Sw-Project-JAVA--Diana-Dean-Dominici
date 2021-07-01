package it.polimi.ingsw.view.utils;

import it.polimi.ingsw.utils.DebugMessages;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class InputReaderValidation {

    public static String    cancellString  = "#CANCELLED#";
    public static String    exitCodeString = "#EXIT#";
    public static int       exitCode       = -1111;
    public static int       cancellInt     = -1234;
    private Object          inputLock = new Object();
    public Scanner          console;
    BufferedReader          console2 = new BufferedReader(new InputStreamReader(System.in));

    boolean resetted        = false;
    public InputReaderValidation()
    {
        //console = new Scanner(System.in);
    }

    public boolean isResetted()
    {
        return resetted;
    }

    public void interrupt() {
        synchronized (inputLock)
        {
            resetted = true;//TODO
        }

    }

    public void restart()
    {
        synchronized (inputLock)
        {
            resetted = false;
        }

    }

    /**
     * Wait for enter key
     */
    public void enter()
    {
        //this.console.nextLine();
        try {
            this.console2.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * read a non empty line
     * @return non empty input line
     */

    public String interruptableInput() {
        try {
            while(!this.bufferReady())
            {
                if(resetted)
                {
                    DebugMessages.printError("gsfs");
                    return cancellString;
                }
                Thread.sleep(100);
            }

            return this.console2.readLine();
        } catch (IOException e) {
            DebugMessages.printError("read line cancelled");
        } catch (InterruptedException e) {
            DebugMessages.printError("thread killed: input cancellation");
            return cancellString;
        }
        return cancellString;
    }

    /**
     *
     * @return a string corresponding to console input stream
     */
    public String readLine(){
        //return this.console.nextLine();
        try {
            return this.console2.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     *
     * @param num to check
     * @param min resuisite min
     * @param max resuisite max
     * @return true if input number satisfy requisite
     */
    public boolean validateInt(int num,int min,int max)
    {
        return num>=min && num<=max;
    }


    /**
     *
     * @return true if buffer ready
     * @throws IOException
     */
    public boolean bufferReady() throws IOException {

        return this.console2.ready();
    }


    /**
     *
     * @param ip a string containing an ip
     * @return true if inpout contain a valid ip
     */
    public boolean validateIP(String ip)
    {
        if(ip.equals("localhost"))return  true;


        //TODO reminder vedi codice di fede per validare ip
        return  true;
    }


    /**
     *
     * @param port port number
     * @return true if the port number is valid (between 0 and 65535)
     */
    public boolean validatePortNumber(int port)
    {
        return validateInt(port,0,65535);
    }
}
