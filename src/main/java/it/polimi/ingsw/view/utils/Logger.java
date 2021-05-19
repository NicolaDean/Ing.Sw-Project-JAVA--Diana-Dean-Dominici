package it.polimi.ingsw.view.utils;

import it.polimi.ingsw.enumeration.ResourceType;
import it.polimi.ingsw.model.cards.ProductionCard;
import it.polimi.ingsw.model.dashboard.Deposit;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceOperator;
import it.polimi.ingsw.utils.ConstantValues;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.utils.DebugMessages;

import java.util.List;

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
     * @param view view
     */
    public void printMarket(View view){

        System.out.println("Market:\n");
        try {
            System.out.print(" 1  2  3  4\n ↓  ↓  ↓  ↓ \n");
            for (int i = 0; i < marketRow; i++) {
                for (int j = 0; j < marketCol; j++) {
                    out.printColored(" ● ", CliColors.R_WHITE_BACKGROUND, view.getMiniMarketBalls()[i][j].getCliColor());
                    if (j == 3) System.out.print("← " + (i + 1)+"\n");
                }
            }
            System.out.print("\nBall to insert: ");
            out.printColored(" ● ", CliColors.R_WHITE_BACKGROUND, view.getMiniMarketDiscardedResouce().getCliColor());
        }catch (Exception e){
            DebugMessages.printWarning("lanciato eccezione "+e.getLocalizedMessage());
        }
    }

    /**
     * print a number of space equal to space
     * @param space number of space to print
     */
    public void spacer(int space)
    {
        for(int i=0;i<space;i++)
        {
            System.out.print(" ");
        }
    }

    /**
     * Draw a rectangle by printing colored space
     * @param space size of rectangle
     * @param color color of recrtangle
     */
    public void colorSpacer(int space,String color)
    {
        this.out.setBackgroundColor(color);
        for(int i=0;i<space;i++)
        {
            System.out.print(" ");
        }
        this.out.reset();
    }

    /**
     * print an empty deposit cell
     */
    public void printEmptyDeposit()
    {
        this.out.printColored(" NO ",CliColors.BLACK_TEXT,CliColors.R_WHITE_BACKGROUND);
    }

    /**
     *
     * @param deposits storage of a player
     * @param chest    chest of a player
     * @param dash     true if i want to print both false if i want to print only storage and not chest
     */
    public void printStorage(Deposit[] deposits,List<Resource> chest,boolean dash)
    {

        if(deposits == null)
        {
            DebugMessages.printWarning("Empty Storage");
            //return;
        }
        else
        {

            this.spacer(4);
            this.out.setUnderline();
            this.out.printColored("Storage:", CliColors.MAGENTA_TEXT);
            this.reset();
            if(dash)
            {
                this.spacer(20);
                this.out.setUnderline();
                this.out.printColored("Chest:", CliColors.MAGENTA_TEXT);
                this.reset();
            }
            System.out.println("");
        }
        for(Deposit d:deposits)
        {
            if(d == null) break;
            int         size    = d.getSizeMax();
            Resource    res     = d.getResource();
            int         qty;
            String      color   ="";

            if(res != null)
            {
                 qty             = d.getResource().getQuantity();
                 color           = resourceRappresentation.getColorRappresentation(d.getResource().getType());
            }
            else
            {
                 qty =0;
            }



            if(size==1) this.spacer(8);
            if(size==2) this.spacer(4);

            for(int i=0;i<size;i++)
            {
                if(qty ==0)
                {
                    this.spacer(4);
                    this.printEmptyDeposit();
                }
                else
                {
                    this.spacer(4);
                    this.colorSpacer(4,color);
                    qty--;
                }
            }

            if(size==1 && dash)
            {
                this.spacer(16);
                this.printInlineResourceList(chest);
            }


            System.out.println("");

        }
    }

    public void printCardRow(ProductionCard[] row)
    {
        //31
        System.out.print("╔");
        for(ProductionCard card: row) {
            if(card!=null)
            {
                int index = 11;
                for(int i=0;i<index;i++) System.out.print("═");
                this.out.setBold();
                String header = " L: "+card.getLevel()+ " VP: " + card.getVictoryPoints();
                this.out.printColored(header,CliColors.BLACK_TEXT,card.getColor());
                this.out.reset();
                index = 9;
                if(header.length() > 11) index = index-1;

                for(int i=0;i<index;i++) System.out.print("═");
                System.out.print("╦╦");
            }
            else
            {
                for(int i=0;i<13;i++) System.out.print("═");
                this.out.printColored("EMPTY",CliColors.RED_TEXT);
                for(int i=0;i<13;i++) System.out.print("═");
                System.out.print("╦╦");
            }

        }

        System.out.println("");
        for(ProductionCard card: row)
        {
            this.out.print("║");
            if(card != null)
            {
                this.out.printColored("Cost     : ",CliColors.WHITE_TEXT);
                this.printInlineResourceList(card.getCost());
                int padding = 20 - 3* ResourceOperator.getTypeCounter(card.getCost());
                this.spacer(padding);
            }
            else
            {
                this.spacer(31);
            }

            this.out.print("║");
        }
        System.out.println("");
        for(ProductionCard card: row)
        {
            this.out.print("║");
            if(card != null)
            {
                this.out.printColored("Raw  mat : ", CliColors.WHITE_TEXT);
                this.printInlineResourceList(card.getRawMaterials());

                int padding = 20 - 3 * ResourceOperator.getTypeCounter(card.getRawMaterials());
                this.spacer(padding);
            }
            else
            {
                this.spacer(31);
            }
            this.out.print("║");
        }
        System.out.println("");
        for(ProductionCard card: row)
        {

            this.out.print("║");
            if(card != null)
            {
                int faith =card.getObtainedFaith();
                this.out.printColored("Obt mat  : ",CliColors.WHITE_TEXT);
                this.printInlineResourceList(card.getObtainedMaterials());

                int padding = 20 - 3* ResourceOperator.getTypeCounter(card.getObtainedMaterials());

                if( faith !=0)
                {   padding = padding-3;
                    this.out.printColored(" "+faith+ " ",CliColors.BLACK_TEXT,CliColors.WHITE_BACKGROUND);
                }
                this.spacer(padding);
            }
            else
            {
                this.spacer(31);
            }
            this.out.print("║");
        }
        System.out.println("");
        System.out.print("╚");
        for(ProductionCard card: row)
        {

            for(int i=0;i<31;i++) System.out.print("═");
            System.out.print("╩╩");
        }

        System.out.println("");

    }

    public void printDeks(ProductionCard[][] productionCards)
    {
        for(ProductionCard[] row:productionCards)
        {
            printCardRow(row);
        }
    }

    public void printDashboard(Deposit[] storage,List<Resource> chest,ProductionCard[] productionCards)
    {
        this.printSeparator();
        this.printStorage(storage,chest,true);
        this.printSeparator();
        this.spacer(4);
        this.out.setUnderline();
        this.out.printColored("Productions:",CliColors.MAGENTA_TEXT);
        this.reset();
        this.out.println("");
        this.printCardRow(productionCards);
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
        out.printlnColored("WELCOMEE TO LORENZO IL MAGNIFICO", CliColors.WHITE_TEXT,CliColors.WHITE_BACKGROUND);
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

       //this.out.printColored(logo,CliColors.WHITE_TEXT,CliColors.BLACK_BACKGROUND);
       this.out.reset();
       this.out.printlnColored(logo2,CliColors.WHITE_TEXT,CliColors.BLACK_BACKGROUND);
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
        //this.printSeparator();
        this.printRequest("Choose what kind of turn you want to perform ");
        this.printRequest("If you want to cancel the turn you can do so by typing \"-exit\" (only if you havent done any action)");
        this.printSeparator();
        //this.out.println(" default = 1");
        //this.out.println(" 1 - Buy a Card");
        //this.out.println(" 2 - Extract from market");
        //this.out.println(" 3 - Activate a production");
        //this.printSeparator();
        //System.out.println("sto tornando!");
        return;
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

    public void printInlineResourceList(List<Resource> resourceList)
    {
        for(Resource res : resourceList)
        {
            if(res.getQuantity() != 0) this.printResource(res);
        }
    }
    public void printResourceList(List<Resource> resourceList)
    {
        this.printInlineResourceList(resourceList);
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
