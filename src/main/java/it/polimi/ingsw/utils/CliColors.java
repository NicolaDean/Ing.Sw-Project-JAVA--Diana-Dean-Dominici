package it.polimi.ingsw.utils;

public class CliColors
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
    public static String RED_TEXT         = "\u001b[31m";
    public static String GREEN_TEXT         = "\u001b[32m";
    public static String YELLOW_TEXT         = "\u001b[33m";
    public static String BLUE_TEXT         = "\u001b[34m";
    public static String MAGENTA_TEXT         = "\u001b[35m";
    public static String CYAN_TEXT         = "\u001b[36m";
    public static String WHITE_TEXT         = "\u001b[37m";

    public void reset()
    {
        System.out.println(CLI_RESET);
    }

    public void printColored(String content,String backgroundStyle,String textStyle)
    {
        System.out.print(backgroundStyle);
        System.out.print(textStyle);

        System.out.print(content);
        reset();
    }

    public void printlnColored(String content,String backgroundStyle,String textStyle)
    {
        printColored(content,backgroundStyle,textStyle);
        System.out.println("");
    }
}
