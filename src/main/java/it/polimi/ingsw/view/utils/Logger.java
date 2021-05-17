package it.polimi.ingsw.view.utils;

import it.polimi.ingsw.enumeration.ResourceType;
import it.polimi.ingsw.model.MiniModel;
import it.polimi.ingsw.model.dashboard.Deposit;
import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.utils.ConstantValues;
import it.polimi.ingsw.utils.DebugMessages;

import java.util.List;
import java.util.Locale;

import static it.polimi.ingsw.utils.ConstantValues.*;

public class Logger {

    public CliColors out;

    public static String istructionText = CliColors.CYAN_TEXT;
    public String istructionBackground  = CliColors.BLACK_TEXT;

    public String warningColor          = CliColors.YELLOW_TEXT;
    public String goodMessage           = CliColors.GREEN_TEXT;

    public Logger()
    {
        out = new CliColors(System.out);
    }

    /**
     * Print a colored message corresponding to "command request"
     * @param content message
     */
    public void printRequest(String content)
    {
        out.printlnColored(content,this.istructionText,this.istructionBackground);
    }

    public void printError(String content)
    {
        out.printlnColored(content,CliColors.RED_TEXT,this.istructionBackground);
    }

    /**
     * print mini market
     * @param m mini model
     */
    public void printMarket(MiniModel m){

        System.out.println("Market:");
        for (int i = 0; i < marketRow; i++) {
            for(int j = 0; j < marketCol; j++){
                try {
                    out.printColored(" ◍ ",CliColors.R_WHITE_BACKGROUND,m.getMiniMarketBalls()[i][j].getCliColor());
                    if(j==3)
                        System.out.print("← "+(i+1));
                }catch (Exception e){
                }
            }
            System.out.println("");
        }
        System.out.print(" ↑  ↑  ↑  ↑ \n 1  2  3  4\n");
        out.printColored(" Discarded resource: ◍ ",CliColors.R_WHITE_BACKGROUND,m.getMiniDiscardedResouce().getCliColor());

    }

    public void spacer(int space)
    {
        for(int i=0;i<space;i++)
        {
            System.out.print(" ");
        }
    }

    public void colorSpacer(int space,String color)
    {
        this.out.setBackgroundColor(color);
        for(int i=0;i<space;i++)
        {
            System.out.print(" ");
        }
        this.out.reset();
    }
    public void printEmptyDeposit()
    {
        this.out.printColored(" ◍ ",CliColors.BLACK_TEXT,CliColors.R_WHITE_BACKGROUND);
    }
    public void printStorage(Deposit[] deposits)
    {

        if(deposits == null)
        {
            DebugMessages.printError("Empty Storage");
            return;
        }
        for(Deposit d:deposits)
        {
            if(d == null) break;
            int size            = d.getSizeMax();
            Resource res = d.getResource();
            int qty;
            String  color ="";
            if(res != null)
            {
                 qty             = d.getResource().getQuantity();
                 color       = resourceRappresentation.getColorRappresentation(d.getResource().getType());
            }
            else
            {
                 qty =0;
            }



            if(size==1) spacer(8);
            if(size==2) spacer(4);

            for(int i=0;i<size;i++)
            {
                if(qty ==0)
                {
                    spacer(4);
                    printEmptyDeposit();
                }
                else
                {
                    spacer(4);
                    colorSpacer(4,color);
                    qty--;
                }
            }
            System.out.println("");

        }
    }
    /**
     * Print a colored message corresponding to "warnings"
     * @param content message
     */
    public void printWarning(String content)
    {
        out.printlnColored(content,this.warningColor,this.istructionBackground);
    }

    /**
     * Print a colored message corresponding to "operation completed with no errors"
     * @param content message
     */
    public void printGoodMessages(String content)
    {
        out.printlnColored(content,this.goodMessage,this.istructionBackground);
    }

    public void Welcome()
    {
        out.printlnColored("WELCOMEE TO LORENZO IL MAGNIFICO", CliColors.RED_TEXT,CliColors.WHITE_BACKGROUND);
    }

    /**
     * Print game Logo with asii art
     */
    public void printLogo()
    {
        String logo =
                                "  _                                                          \n" +
                                " | |                                                         \n" +
                                " | |     ___  _ __ ___ _ __  _______                         \n" +
                                " | |    / _ \\| '__/ _ \\ '_ \\|_  / _ \\                        \n" +
                                " | |___| (_) | | |  __/ | | |/ / (_) |                       \n" +
                                " |______\\___/|_|  \\___|_| |_/___\\___/      _  __ _           \n" +
                                " |_   _| |       |  \\/  |                 (_)/ _(_)          \n" +
                                "   | | | |       | \\  / | __ _  __ _ _ __  _| |_ _  ___ ___  \n" +
                                "   | | | |       | |\\/| |/ _` |/ _` | '_ \\| |  _| |/ __/ _ \\ \n" +
                                "  _| |_| |____   | |  | | (_| | (_| | | | | | | | | (_| (_) |\n" +
                                " |_____|______|  |_|  |_|\\__,_|\\__, |_| |_|_|_| |_|\\___\\___/ \n" +
                                "                                __/ |                        \n" +
                                "                               |___/    ";

        String logo2   =
            "╭╮╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╭╮╱╭━╮╭━╮╱╱╱╱╱╱╱╱╱╱╭━╮\n" +
            "┃┃╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱┃┃╱┃┃╰╯┃┃╱╱╱╱╱╱╱╱╱╱┃╭╯\n" +
            "┃┃╱╱╭━━┳━┳━━┳━╮╭━━━┳━━╮╭┫┃╱┃╭╮╭╮┣━━┳━━┳━╮╭┳╯╰┳┳━━┳━━╮\n" +
            "┃┃╱╭┫╭╮┃╭┫┃━┫╭╮╋━━┃┃╭╮┃┣┫┃╱┃┃┃┃┃┃╭╮┃╭╮┃╭╮╋╋╮╭╋┫╭━┫╭╮┃\n" +
            "┃╰━╯┃╰╯┃┃┃┃━┫┃┃┃┃━━┫╰╯┃┃┃╰╮┃┃┃┃┃┃╭╮┃╰╯┃┃┃┃┃┃┃┃┃╰━┫╰╯┃\n" +
            "╰━━━┻━━┻╯╰━━┻╯╰┻━━━┻━━╯╰┻━╯╰╯╰╯╰┻╯╰┻━╮┣╯╰┻╯╰╯╰┻━━┻━━╯\n" +
            "╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╭━╯┃";

        this.out.clear();
        this.out.setBold();

       //this.out.printColored(logo,CliColors.RED_TEXT,CliColors.BLACK_BACKGROUND);
       this.out.reset();
       this.out.printlnColored(logo2,CliColors.RED_TEXT,CliColors.BLACK_BACKGROUND);
       this.out.reset();
    }

    /**
     * if user write help this menu will be shown
     */
    public void printHelp()
    {
        this.out.clear();
        this.out.println("------------------------------------------");
        this.printWarning(" This list of commands is always usable, even if it's not your turn!");
        this.printWarning(" Whenever you need you can type \"h\" or \"help\" to show this message again");
        this.out.println("------------------------------------------");
        this.out.println(" default = cancel");
        this.out.println(" \"-quit\" to quit the game");
        this.out.println(" \"-exit\" to exit the turntype you selected (only if you haven't already played) ");
        this.out.println(" \"-startgame\" to start the game");
        this.out.println(" \"-dashboard\" to show the Dashboard");
        this.out.println(" \"-swapdeposits\" to enter the deposit swapping function");
        this.out.println(" \"-spy\" to spy the dashboard of other players");
        this.out.println("------------------------------------------");
    }

    public void printSeparator()
    {
        this.out.println("------------------------------------------");
    }

    public void printTurnTypesHelp()
    {
        this.out.clear();
        this.printSeparator();
        this.printRequest("Choose one of the following Turn type");
        this.printRequest("If you want to cancel the turn you can by typing -exit (only if you havent done any action)");
        this.printSeparator();
        this.out.println(" default = 1");
        this.out.println(" 1 - Buy a Card");
        this.out.println(" 2 - Extract from market");
        this.out.println(" 3 - Activate a production");
        this.printSeparator();
    }

    public void printResource(Resource res)
    {
        int          qty    = res.getQuantity();
        ResourceType type   = res.getType();
        String       color  = ConstantValues.resourceRappresentation.getColorRappresentation(type);

        this.out.printColored(" " + qty + " ",CliColors.BLACK_TEXT,color);
        this.out.setBold();
        this.reset();
    }

    public void printResourceList(List<Resource> resourceList)
    {
        for(Resource res : resourceList)
        {
            if(res.getQuantity() != 0) this.printResource(res);
        }
        this.out.println("");
    }


    public void reset()
    {
        this.out.reset();
    }

    /**
     * Display all cards currently visible (buyable)
     */
    public void displayDecks()
    {

    }

    /**
     * Print the storage/deposits owned by this user
     */
    public void displayStorage()
    {

    }

    /**
     * Print a "summary" of others player resources/position
     */
    public void printOthersPlayer()
    {

    }

    /**
     * Print the productionCards visible
     */
    public void printProductions()
    {

    }

}
