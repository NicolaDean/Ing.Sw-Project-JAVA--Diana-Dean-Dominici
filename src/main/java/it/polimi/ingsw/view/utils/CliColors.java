package it.polimi.ingsw.view.utils;

import it.polimi.ingsw.utils.CurrentOS;

import java.io.OutputStream;
import java.io.PrintStream;

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
    public static String R_WHITE_BACKGROUND = "\u001b[107m"; //real white

    public static String BLACK_TEXT         = "\u001b[30m";
    public static String RED_TEXT           = "\u001b[31m";
    public static String GREEN_TEXT         = "\u001b[32m";
    public static String YELLOW_TEXT        = "\u001b[33m";
    public static String BLUE_TEXT          = "\u001b[34m";
    public static String MAGENTA_TEXT       = "\u001b[35m";
    public static String CYAN_TEXT          = "\u001b[36m";
    public static String WHITE_TEXT         = "\u001b[37m";
    public static String GRAY_TEXT          = "\u001b[30;1m";
    public static String R_WHITE_TEXT       = "\033[0;97m";

    public static String UP_CURSOR          = "A";
    public static String DOWN_CURSOR        = "B";
    public static String RIGHT_CURSOR       = "C";
    public static String LEFT_CURSOR        = "D";

    public static String BOLD               = "\u001b[1m";
    public static String UNDERLINE          = "\u001b[4m";
    public static String REVERSED           = "\u001b[7m";

    public static String CLEAR_FULL         = "\033[H\033[2J";
    public static String CLEAR_UP           = "\u001b[1J";
    public static String CLEAR_DOWN         = "\u001b[0J";//dal cursore in giu


    public CliColors(OutputStream out) {
        super(out,true);
    }

    /**
     * delete style
     */
    public void reset()
    {
        if(!CurrentOS.IsWindows())
            this.print(CLI_RESET);
    }

    /**
     * print clear character
     */
    public void clear(){
        if(!CurrentOS.IsWindows())
            this.println(CLEAR_FULL);
        else
            for(int i=0;i<25;i++) System.out.println("");

        this.flush();
    }

    /**
     * set bold
     */
    public  void setBold()
    {
        if(!CurrentOS.IsWindows())
            this.print(BOLD);
    }

    /**
     * set underline
     */
    public void setUnderline()
    {
        if(!CurrentOS.IsWindows())
            this.print(UNDERLINE);
    }

    /**
     * set reversed
     */
    public void setReversed()
    {
        if(!CurrentOS.IsWindows())
            this.print(REVERSED);
    }

    /**
     * print a text with color and background
     * @param backgroundStyle background color
     */
    public void setBackgroundColor(String backgroundStyle)
    {
        if(!CurrentOS.IsWindows())
            this.print(backgroundStyle);
    }

    /**
     * print a text with color and background
     * @param textColor        text color
     */
    public void setTextColor(String textColor)
    {
        if(!CurrentOS.IsWindows())
            this.print(textColor);
    }

    /**
     * print a text with color and background
     * @param content         string to print
     * @param textColor        text color
     */
    public void printlnColored(String content, String textColor)
    {
        this.printColored(content,textColor);
        System.out.println();
    }

    /**
     * print a text with color and background
     * @param content         string to print
     * @param textColor        text color
     * @param backgroundStyle background color
     */
    public void printlnColored(String content, String textColor, String backgroundStyle)
    {
        this.setBackgroundColor(backgroundStyle);
        this.printlnColored(content,textColor);
    }

    /**
     * print a text with color and background
     * @param content         string to print
     * @param textColor        text color
     */
    public void printColored(String content, String textColor)
    {
        this.setTextColor(textColor);
        this.print(content);
        reset();
    }

    /**
     * print a text with color and background
     * @param content         string to print
     * @param textColor        text color
     * @param backgroundStyle background color
     */
    public void printColored(String content, String textColor, String backgroundStyle)
    {
        this.setBackgroundColor(backgroundStyle);
        this.printColored(content,textColor);
    }



}
