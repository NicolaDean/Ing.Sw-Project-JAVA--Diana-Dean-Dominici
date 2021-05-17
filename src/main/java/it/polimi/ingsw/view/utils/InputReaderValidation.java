package it.polimi.ingsw.view.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class InputReaderValidation {


    public Scanner console;
    BufferedReader console2 = new BufferedReader(new InputStreamReader(System.in));
    public InputReaderValidation()
    {
        //console = new Scanner(System.in);
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
    public String readLine()
    {
        //return this.console.nextLine();
        try {
            return this.console2.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public int readInt()
    {
        try {
            return Integer.parseInt(this.console2.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
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
            try {
                out= console2.readLine();
            } catch (IOException e) {
                e.printStackTrace();
                out="";
            }

        }while(!(out.length() >= lenght));

        return out;
    }

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
