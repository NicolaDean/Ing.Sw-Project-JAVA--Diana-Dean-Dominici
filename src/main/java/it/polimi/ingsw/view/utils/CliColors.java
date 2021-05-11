package it.polimi.ingsw.view.utils;

import java.awt.*;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

public class CliColors extends PrintStream
{

    public static String CLI_RESET          = "\u001b[0m";
    public static String BLACK_BACKGROUND   = "\u001b[40m";
    public static String RED_BACKGROUND     = "\u001b[41m";
    public static String GREEN_BACKGROUND   = "\u001b[42m";
    public static String YELLOW_BACKGROUND  = "\u001b[43m";
    public static String BLUE_BACKGROUND    = "\u001b[44m";
    public static String MAGENTA_BACKGROUND = "\u001b[45m";
    public static String CYAN_BACKGROUND    = "\u001b[46m";
    public static String WHITE_BACKGROUND   = "\u001b[47m";

    public static String BLACK_TEXT         = "\u001b[30m";
    public static String RED_TEXT           = "\u001b[31m";
    public static String GREEN_TEXT         = "\u001b[32m";
    public static String YELLOW_TEXT        = "\u001b[33m";
    public static String BLUE_TEXT          = "\u001b[34m";
    public static String MAGENTA_TEXT       = "\u001b[35m";
    public static String CYAN_TEXT          = "\u001b[36m";
    public static String WHITE_TEXT         = "\u001b[37m";


    public static String BOLD               = "\u001b[1m";
    public static String UNDERLINE          = "\u001b[4m";
    public static String REVERSED           = "\u001b[7m";

    public static String CLEAR_FULL         = "\033[2J";
    public static String CLEAR_UP           = "\u001b[1J";
    public static String CLEAR_DOWN         = "\u001b[0J";//dal cursore in giu


    public CliColors(OutputStream out) {
        super(out,true);
    }

    public void reset()
    {
        this.println(CLI_RESET);
    }

    public void clear(){
        this.println(CLEAR_FULL);
        this.flush();
    }

    public  void setBold()
    {
        this.print(BOLD);
    }

    public void setUnderline()
    {
        this.print(UNDERLINE);
    }

    public void setReversed()
    {
        this.print(REVERSED);
    }

    public void setBackgroundColor(String backgroundStyle)
    {
        this.print(backgroundStyle);
    }

    public void setTextColor(String textColor)
    {
        this.print(textColor);
    }

    public void printColored(String content,String textColor)
    {
        this.setTextColor(textColor);
        this.print(content);
        reset();
    }

    public void printColored(String content,String textColor,String backgroundStyle)
    {
        this.setBackgroundColor(backgroundStyle);
        this.printColored(content,textColor);
    }

















    public void printlnColored(String content,String backgroundStyle,String textStyle)
    {
        printColored(content,backgroundStyle,textStyle);
        this.println("");
    }
}
