package it.polimi.ingsw.view.utils;

import java.util.Scanner;

public class InputReaderValidation {


    public Scanner console;
    public InputReaderValidation()
    {
        console = new Scanner(System.in);
    }


    /**
     * Wait for enter key
     */
    public void enter()
    {
        this.console.nextLine();
    }

    /**
     * read a non empty line
     * @return non empty input line
     */
    public String readLine()
    {
        return this.console.nextLine();
    }

    public int readInt()
    {
        return this.console.nextInt();
    }
    public boolean validateInt(int num,int min,int max)
    {
        return num>=min && num<=max;
    }

    /**
     * Read a line with minimum lenght
     * @param lenght minimum lenght
     * @return line with more then minimum lenght
     */
    public String readLine(int lenght)
    {
        String out;
        do {
            out= console.nextLine();
        }while(!(out.length() >= lenght));

        return out;
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
