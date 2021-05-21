package it.polimi.ingsw.view.utils;

import it.polimi.ingsw.enumeration.ResourceType;
import it.polimi.ingsw.model.CellScore;
import it.polimi.ingsw.model.PapalSpace;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.PrerequisiteCard;
import it.polimi.ingsw.model.cards.ProductionCard;
import it.polimi.ingsw.model.dashboard.Deposit;
import it.polimi.ingsw.model.factory.MapFactory;
import it.polimi.ingsw.model.minimodel.MiniPlayer;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceList;
import it.polimi.ingsw.model.resources.ResourceOperator;
import it.polimi.ingsw.utils.ConstantValues;
import it.polimi.ingsw.utils.CurrentOS;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.utils.DebugMessages;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.enumeration.ResourceType.COIN;
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

    public void printPapalPosition(MiniPlayer[] players){
        List<CellScore> scorePositions = MapFactory.loadCellScoresFromJsonFile();
        List<PapalSpace> papalSpaces= MapFactory.loadPapalSpacesFromJsonFile();
        int dim=(papalSpaces.get(papalSpaces.size()-1).getFinalPosition()+1);
        boolean controll=false;
        int nOfPlayer=players.length;
        String[] colorCLI= new String[nOfPlayer];

        printPapalPositionLegend(players);

        for (int i = 0; i < nOfPlayer; i++) {
            if(i==0) colorCLI[i]=CliColors.BLUE_TEXT;
            if(i==1) colorCLI[i]=CliColors.MAGENTA_TEXT;
            if(i==2) colorCLI[i]=CliColors.YELLOW_TEXT;
            if(i==3) colorCLI[i]=CliColors.GREEN_TEXT;
        } //color


        for (int i = 0; i < dim; i++) {
            controll=false;
            for (PapalSpace p:papalSpaces) {
                if (((i > p.getInitialPosition()) && (i < p.getFinalPosition()))) {
                    this.out.printColored("═════", CliColors.RED_TEXT);
                    controll=true;
                    break;
                }
                else if((i==p.getInitialPosition())){
                    this.out.printColored("╔════",CliColors.RED_TEXT);
                    controll=true;
                    break;
                }
                else if((i==p.getFinalPosition())) {
                    this.out.printColored("══ ✝︎ ╗",CliColors.RED_TEXT);
                    controll=true;
                    break;

                }
                else if((i==p.getFinalPosition()+1)){
                    this.out.printColored("    ",CliColors.RED_TEXT);
                    controll=true;
                    break;
                }
                else controll=false;
            }
            if(!controll) System.out.print("     ");
        } //papal space marker


        System.out.print("\n╔");
        for (int i = 0; i < dim-1; i++) System.out.print("════╦"); //up border
        System.out.print("════");

        System.out.print("╗\n║");

        int l,n,k,nOfPlayerWhitComunCell;
        for (int i = 0; i < dim; i++) {
            controll=false;
            nOfPlayerWhitComunCell=0;
            for (int j = 0; j < nOfPlayer; j++) {
                for (k = 0; k < nOfPlayer; k++) {
                    if((players[j].getPosition()==players[k].getPosition())&&(j!=k)&&(players[j].getPosition()==i)) {
                        nOfPlayerWhitComunCell++;
                    }
                }

                if ((nOfPlayerWhitComunCell==0)&&(players[j].getPosition() == i)) { // 1 player
                    this.out.printColored(" ⚑ ", colorCLI[j]);
                    System.out.print(" ║");
                    controll=true;
                    break;
                }
                if ((nOfPlayerWhitComunCell==1)&&(players[j].getPosition() == i)) { // 2 player
                    for (k = 0; k < nOfPlayer; k++) { if((players[j].getPosition()==players[k].getPosition())&&(j!=k)&&(players[j].getPosition()==i)) { break; } }

                    this.out.printColored(" ⚑", colorCLI[j]);
                    this.out.printColored("⚑", colorCLI[k]);
                    System.out.print(" ║");

                    controll=true;
                    break;
                }

                if ((nOfPlayerWhitComunCell==2)&&(players[j].getPosition() == i)) { // 3 player

                    for (k = 0; k < nOfPlayer; k++) { if((players[j].getPosition()==players[k].getPosition())&&(j!=k)&&(players[j].getPosition()==i)) { break; } }

                    for (l = k; l < nOfPlayerWhitComunCell; l++) { if((players[j].getPosition()==players[l].getPosition())&&(j!=l)&&(l!=k)&&(players[j].getPosition()==i)) { break; } }

                    this.out.printColored("⚑", colorCLI[j]);
                    this.out.printColored("⚑", colorCLI[k]);
                    this.out.printColored("⚑", colorCLI[l]);
                    System.out.print(" ║");

                    controll=true;
                    break;
                }

                if ((nOfPlayerWhitComunCell==3)&&(players[j].getPosition() == i)) { // 4 player

                    for (k = 0; k < nOfPlayer; k++) { if((players[j].getPosition()==players[k].getPosition())&&(j!=k)&&(players[j].getPosition()==i)) { break; } }

                    for (l = k; l < nOfPlayerWhitComunCell; l++) { if((players[j].getPosition()==players[l].getPosition())&&(j!=l)&&(l!=k)&&(players[j].getPosition()==i)) { break; } }

                    for (n = l; n < nOfPlayerWhitComunCell; n++) { if((players[j].getPosition()==players[n].getPosition())&&(j!=n)&&(n!=l)&&(l!=k)&&(players[j].getPosition()==i)) { break; } }

                    this.out.printColored("⚑", colorCLI[j]);
                    this.out.printColored("⚑", colorCLI[k]);
                    this.out.printColored("⚑", colorCLI[l]);
                    this.out.printColored("⚑", colorCLI[n]);
                    System.out.print("║");

                    controll=true;
                    break;
                }

            }
            if(!controll){
                if ((i) < 10) System.out.print(" " + (i) + "  ║");
                else System.out.print(" " + (i) + " ║");
            }

        }
        System.out.print("\n║");
        for (int i = 0; i < dim; i++) {
            controll=false;
            for(CellScore c:scorePositions){
                if(c.getPosition()==i) {
                    if(c.getScore()>=10) this.out.printColored(c.getScore()+"VP", CliColors.RED_TEXT);
                    else this.out.printColored(c.getScore()+"VP ", CliColors.RED_TEXT);
                    controll = true;
                }
            }
            if(!controll){
                System.out.print("    ");
            }
            System.out.print("║");
        }


        System.out.print("\n╚");
        for (int i = 0; i < dim-1; i++) System.out.print("════╩");

        System.out.println("════╝");
    }

    public void printPapalPositionLegend(MiniPlayer[] players){
        int nOfPlayer=players.length;
        String[] colorCLI= new String[nOfPlayer];
        for (int i = 0; i < nOfPlayer; i++) {
            if(i==0) colorCLI[i]=CliColors.BLUE_TEXT;
            if(i==1) colorCLI[i]=CliColors.MAGENTA_TEXT;
            if(i==2) colorCLI[i]=CliColors.YELLOW_TEXT;
            if(i==3) colorCLI[i]=CliColors.GREEN_TEXT;
        }
        System.out.println(nOfPlayer);
        for (int i = 0; i < nOfPlayer; i++) {
            System.out.print(players[i].getNickname()+": ");
            out.printColored("⚑  ",colorCLI[i]);
        }
        System.out.println();
    }

    /**
     * print mini market
     * @param view view
     */
    public void printMarket(View view){

        System.out.println("Market:\n");
        try {
            System.out.print("     1  2  3  4\n     ↓  ↓  ↓  ↓ \n");
            for (int i = 0; i < marketRow; i++) {
                for (int j = 0; j < marketCol; j++) {
                    if (j == 0) System.out.print((i + 1)+" → ");
                    out.printColored(" ● ", CliColors.BLACK_BACKGROUND, view.getMiniMarketBalls()[i][j].getCliColor());
                }
                System.out.print("\n");
            }
            System.out.print("\nBall to insert: ");
            out.printColored(" ● ", CliColors.BLACK_BACKGROUND, view.getMiniMarketDiscardedResouce().getCliColor());
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
        System.out.println("");

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
                    //this.colorSpacer(4,color);
                    this.out.print(res.getCliRappresentation(false));
                    this.reset();
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
                int index = 7;
                for(int i=0;i<index;i++) System.out.print("═");
                this.out.setBold();
                String header = " L: "+card.getLevel()+ " VP: " + card.getVictoryPoints();
                this.out.printColored(header,CliColors.BLACK_TEXT,card.getColor());
                this.out.reset();
                index = 6;
                if(header.length() > 11) index = index-1;

                for(int i=0;i<index;i++) System.out.print("═");
                System.out.print("╦╦");
            }
            else
            {
                for(int i=0;i<10;i++) System.out.print("═");
                this.out.printColored("EMPTY",CliColors.RED_TEXT);
                for(int i=0;i<9;i++) System.out.print("═");
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
                int padding = 13 - 3* ResourceOperator.getTypeCounter(card.getCost());
                this.spacer(padding);
            }
            else
            {
                this.spacer(24);
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

                int padding = 13 - 3 * ResourceOperator.getTypeCounter(card.getRawMaterials());
                this.spacer(padding);
            }
            else
            {
                this.spacer(24);
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

                int padding = 13 - 3* ResourceOperator.getTypeCounter(card.getObtainedMaterials());

                if( faith !=0)
                {   padding = padding-3;
                    this.out.printColored(" "+faith+ " ",CliColors.BLACK_TEXT,CliColors.RED_BACKGROUND);
                }
                this.spacer(padding);
            }
            else
            {
                this.spacer(24);
            }
            this.out.print("║");
        }
        System.out.println("");
        System.out.print("╚");
        for(ProductionCard card: row)
        {
            for(int i=0;i<24;i++) System.out.print("═");
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


    public void printLeaders(LeaderCard[] cards)
    {

        System.out.print("╔");
        for(LeaderCard card:cards)
        {
            String header = CliColors.RED_TEXT + "Discarded" ;

            int headerSize = 9;
            int padding = 24 - headerSize;

            if(card !=null)
            {
                 header = card.getHeader();
                 headerSize = card.getPadding();
                 padding   = 24 - card.getPadding();
            }


                padding = padding/2 + padding%2 -2;

                for(int i=0;i<padding;i++) System.out.print("═");
                this.out.print(header);
                this.reset();
                if(headerSize%2 == 0) padding = padding+1;
                for(int i=0;i<padding;i++) System.out.print("═");
                System.out.print("╦╦");

        }

        this.out.println("");

        for(LeaderCard card:cards)
        {
            this.out.print("║");
            if(card == null)
            {
                spacer(21);
            }
            else
            {

                spacer(6);
                if(card.isActive())
                {
                    this.out.printColored("Active",CliColors.RED_TEXT);
                    spacer(3);
                }
                else  this.out.printColored("Disactive",CliColors.RED_TEXT);
                spacer(6);

            }
            this.out.print("║");

        }
        this.out.println("");
        for(LeaderCard card:cards)
        {
            this.out.print("║");
            this.spacer(4);
            this.out.printColored("Requirements:", CliColors.YELLOW_TEXT);
            this.spacer(4);
            this.out.print("║");

        }
        this.out.println("");
        for(int i=0;i<2;i++)
        {
            for(LeaderCard card:cards)
            {
                List<PrerequisiteCard> prerequisites = new ArrayList<>();
                if(card!= null)
                {
                    prerequisites = card.getCardPrequisite();
                }



                this.out.print("║");
                if(prerequisites.size() >= i+1)
                {

                    PrerequisiteCard prerequisiteCard = prerequisites.get(i);
                    int lv  = prerequisiteCard.getLevel();
                    int qty = prerequisiteCard.getQuantity();

                    this.spacer(7);
                    this.out.print(prerequisiteCard.getCliRappresentation() + CliColors.BLACK_TEXT + CliColors.BOLD);
                    if(lv == -1 )this.spacer(1);
                    else this.out.print("L:" +lv);
                    this.out.print(" Q:" + prerequisiteCard.getQuantity());
                    if(lv == -1 )this.spacer(2);
                    this.reset();
                    this.spacer(7);

                }
                else this.spacer(21);
                this.out.print("║");

            }
            this.out.println("");
        }

        for(LeaderCard card:cards)
        {
            List<Resource> resourceList = new ResourceList();
            if(card!= null)
            {
                resourceList = card.getCost();
            }

            this.out.print("║");
            if(!ResourceOperator.isEmpty(resourceList))
            {
                this.spacer(6);
                this.out.printColored("Res:",CliColors.YELLOW_TEXT);
                this.printInlineResourceList(resourceList);

                int padding = 9 - 3 * ResourceOperator.getTypeCounter(resourceList);
                this.spacer(padding);
                this.spacer(2);
            }
            else
            {
                this.spacer(21);
            }
            this.out.print("║");
        }
        System.out.println("");
        System.out.print("╚");
        for(LeaderCard card:cards)
        {
            for(int i=0;i<21;i++) System.out.print("═");
            System.out.print("╩╩");
        }

        System.out.println("");
        this.out.print("║");
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

    public void printResourceTypeSelection(ResourceType[] resourceTypes)
    {
        int i=1;
        for(ResourceType t:resourceTypes)
        {
            if(t == COIN) out.printColored("  "+i+" - SHIELD\n",      CliColors.BLUE_TEXT);
            if(t == COIN) out.printColored("  "+i+" - ROCK\n",         CliColors.WHITE_TEXT);
            if(t == COIN) out.printColored("  "+i+" - COIN\n",         CliColors.YELLOW_TEXT);
            if(t == COIN) out.printColored("  "+i+" - SERVANT\n",      CliColors.MAGENTA_TEXT);
            i++;
        }
    }
    public void printResourceTypeSelection()
    {

        System.out.println("\nRESOURCE TYPES:");
        out.printColored("  1 - SHIELD\n",    CliColors.BLUE_TEXT);
        out.printColored("  2 - ROCK\n",      CliColors.WHITE_TEXT);
        out.printColored("  3 - COIN\n",      CliColors.YELLOW_TEXT);
        out.printColored("  4 - SERVANT\n\n", CliColors.MAGENTA_TEXT);

        //printGoodMessages("1 - SHIELD\n2 - ROCK\n3 - COIN\n4 - SERVANT ");
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
        this.out.println(" \"-swapdeposit\" to enter the deposit swapping function");
        this.out.println(" \"-shop\" to show the cards that can be bought");
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

        this.out.print(res.getCliRappresentation(true));

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
