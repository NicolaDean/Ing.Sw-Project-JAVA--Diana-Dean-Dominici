package it.polimi.ingsw.view;

import com.google.gson.Gson;
import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.controller.packets.EndTurn;
import it.polimi.ingsw.controller.packets.ExtractionInstruction;
import it.polimi.ingsw.controller.packets.InsertionInstruction;
import it.polimi.ingsw.enumeration.ResourceType;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.ProductionCard;
import it.polimi.ingsw.model.cards.leaders.BonusProductionInterface;
import it.polimi.ingsw.model.dashboard.Deposit;
import it.polimi.ingsw.model.market.balls.BasicBall;
import it.polimi.ingsw.model.minimodel.MiniPlayer;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceList;
import it.polimi.ingsw.model.resources.ResourceOperator;
import it.polimi.ingsw.utils.ConstantValues;
import it.polimi.ingsw.utils.DebugMessages;
import it.polimi.ingsw.view.observer.Observable;
import it.polimi.ingsw.view.utils.*;


import static it.polimi.ingsw.model.resources.ResourceOperator.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class CLI extends Observable<ClientController> implements View {

    Logger                  terminal; //print formatted and colored text on the cli
    InputReaderValidation   input;
    Thread                  helpThread;
    Thread                  turnThread;
    boolean                 waiting;
    int                     turnSelected;
    private BasicBall[][]   miniMarketBalls;
    private BasicBall       miniMarketDiscardedResouce;
    boolean canEndTurn;
    boolean actionDone;
    boolean singlePlayer= false;
    boolean Avoidable   = true;  //TO DO EXIT COMMAND
    boolean firstTurn   = true;  //LEADER SELECTION AND INITIAL RESOURCE
    boolean helpAborted = false;

    Object lockHelp     = new Object();
    int index;
    public CLI(int index)
    {
        this.index=index;
        waiting = true;
        input = new InputReaderValidation();
        terminal = new Logger();
        turnSelected = -1;
        canEndTurn = false;
        actionDone = false;
    }

    /**
     * set mini model of market in the view
     * @param balls balls
     * @param discarted ball discarted
     */
    public void setMarket(BasicBall[][] balls, BasicBall discarted){
        if((balls != null)&&(discarted != null)) {
            miniMarketBalls = balls;
            miniMarketDiscardedResouce = discarted;
        }
    }

    @Override
    public void showMessage(String msg) {
        this.terminal.printGoodMessages(msg);
    }

    /**
     *
     * @return the matrix of balls in the market
     */
    public BasicBall[][] getMiniMarketBalls() {
        return miniMarketBalls;
    }

    /**
     *
     * @return discarded ball
     */
    public BasicBall getMiniMarketDiscardedResouce() {
        return miniMarketDiscardedResouce;
    }

    public void turnSabotage()
    {
        this.turnThread.interrupt();
    }

    public void showPapalCell(MiniPlayer[] p){
        terminal.printPapalPosition(p);
    }

    /**
     * list of possible action during the turn
     * @param cmd     a string potentialy containing a command to parse
     * @param message a message to show after the execution of a command
     * @return a string containing the input the user asked before helpCommand was called
     * @throws InterruptedException if a thread is interrupted while helpCommand this function "crush"
     */
    public String helpCommands(String cmd, String message){
        //
        cmd = cmd.toLowerCase();
        switch (cmd) {
            case "h":
            case "-h":
            case "help":
                terminal.printHelp();
                return customRead(message);

            case "-quit": //quit case
                //this.quit();
                return customRead(message);

                //Idea creare un thread per i 3 tipi di turni e se lutente non ha fatto azioni questo thread permette di killarlo
            case "-exit": //cancel case
                if(Avoidable)
                {
                    if(actionDone)
                    {
                        this.notifyObserver(ClientController::askEndTurn);
                    }
                    else
                    {
                        this.askTurnType();
                    }
                    return InputReaderValidation.exitCodeString;
                }
                return "";
            case "-startgame": //cancel case
                this.notifyObserver(ClientController::sendStartCommand);
                return cmd;
            case "-dashboard": //cancel case
                this.notifyObserver(ClientController::showDashboard);
                return customRead(message);
            case "-shop":
                this.notifyObserver(ClientController::showDecks);
                return customRead(message);
            case "-swap":
            case "-swapdeposit": //cancel case
                //System.out.println("index di questa cli: "+this.index);
                this.askSwapDeposit(this.index);
                return customRead(message);
            case "-moveresources": //cancel case
                //System.out.println("index di questa cli: "+this.index);
                this.askMoveResources();
                return customRead(message);
            case "-spy": //cancel case
                this.askSpyPlayer();
                return customRead(message);
            case "-activateleader":
                this.askLeaderActivation();
                return customRead(message);
            case "-discardleader":
                this.askDiscardLeader();
                return customRead(message);
            default:
                //System.out.println("opzione di default, cmd vale "+cmd);
                return cmd;

        }
    }

    /**
     * This function allow to ask for inputs while parsing eventual other commands (help list)
     * @return wanted input
     */
    public synchronized String customRead()  {
        if(this.input.isResetted()) return InputReaderValidation.cancellString;
        String s = this.input.readLine();
        s = helpCommands(s,"");
        return s;
    }

    public boolean isExit(int num)
    {
        if(num == InputReaderValidation.exitCode) DebugMessages.printError("EXIT FROM TURN INT");
        return num == InputReaderValidation.exitCode;
    }
    public boolean isExit(String str)
    {
        if(str.toLowerCase(Locale.ROOT).equals(InputReaderValidation.exitCodeString.toLowerCase(Locale.ROOT)))
            DebugMessages.printError("EXIT FROM TURN");
        return str.toLowerCase(Locale.ROOT).equals(InputReaderValidation.exitCodeString.toLowerCase(Locale.ROOT));
    }
    /**
     * return true if this string contain a "cancell" code (this code is caused when interruptableInput is interrupted)
     * @param str string to check
     * @return true if this string contain a "cancell" code
     */
    public boolean isInputCancelled(String str)
    {
        return str.toLowerCase(Locale.ROOT).equals(InputReaderValidation.cancellString.toLowerCase(Locale.ROOT)) || this.input.isResetted();
    }

    /**
     * same of isInputCancelled(string) but with integer code
     * @param num integer to check for exit codee
     * @return true if input aborted
     */
    public boolean isInputCancelled(int num)
    {
        return num == InputReaderValidation.cancellInt || this.input.isResetted();
    }


    /**
     * same of custom read but show a message after the command parsing
     * @param message message to show
     * @return input wanted
     */
    public String customRead(String message) {
        if(this.input.isResetted()) return InputReaderValidation.cancellString;
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            return InputReaderValidation.cancellString;
        }
        terminal.printRequest(message);
        String s = this.input.interruptableInput();
        s = helpCommands(s,message);
        return s;
    }

    /**
     * equal to custom read but interruptable
     * @param message message toshow
     * @return wanted input
     * @throws InterruptedException interrupt exeption (if thread.interrupt is called)
     */
    public String interruptableCustomRead(String message) throws InterruptedException {

        String s = this.input.interruptableInput();

        s = helpCommands(s,message);


        return s;
    }

    @Override
    public void printWelcomeScreen() {
        this.terminal.printLogo();
        this.terminal.out.setBackgroundColor(CliColors.BLACK_BACKGROUND);
        this.clickEnter();

        this.askServerData();
    }

    @Override
    public void showError(String error) {

    }

    public void clickEnter() {
        this.terminal.out.printlnColored("Click enter to continue",CliColors.RED_TEXT,CliColors.BLACK_BACKGROUND);
        this.input.enter();
        this.terminal.out.clear();
        this.terminal.out.print("\033[H\033[2J");
    }


    @Override
    public void askNickname() {

         singlePlayer = false;
        terminal.printRequest("Type here your nickname (empty for default: random nickname):");

        String nickname = "";
        do {
            nickname = input.readLine();
            //System.out.println("length: "+nickname.length());
            if(nickname.length() == 0)
            {
                /*byte[] array = new byte[7]; // length is bounded by 7
                new Random().nextBytes(array);
                nickname  = new String(array, Charset.forName("UTF-8"));*/
                nickname= RandomNicks.getRandomNickname();
                terminal.printGoodMessages("Your random nickname is "+nickname+".\n");

            }
            if(nickname.length() < 3) terminal.printWarning("Nickname too short, minimum 3 letters");
        }while(nickname.length() < 3 );

        terminal.printRequest("If you want single player type \"single\", for the multiplayer type anything else.");

        String single = input.readLine();

        if(single.equals("single"))
        {
            System.out.println("try single login");
            singlePlayer = true;
        }

        String validNickname = nickname;
        boolean finalSinglePlayer = singlePlayer;
        this.notifyObserver(controller -> controller.setNickname(validNickname, finalSinglePlayer));

        this.askCommand();
    }

    @Override
    public void askServerData()
    {
        terminal.printRequest("Insert a valid server IP: ( empty for default: localhost ) ");

        String ip =".";
        String port = "-1";
        int realport = -1;

        do {
            ip= this.input.readLine();
            if(ip.length()==0) ip = ConstantValues.defaultIP;
            if(ip.equals(".")) terminal.printWarning("please, insert a valid IP address");
        }
        while(!this.input.validateIP(ip));

        terminal.printRequest("Insert server port: ( empty for default: 1234 ) ");

        do {
            port = this.input.readLine();

            boolean check = false;
            try{
            realport=Integer.parseInt(port);}
            catch (Exception e)
            {
                terminal.printWarning(port + " is not valid a valid port number, insert a value between 1 and 65535");
                check = true;
            }

            if(port == "0" || port.length()==0)
                realport = 1234;
            if(!check)
                if(!this.input.validatePortNumber(realport)) terminal.printWarning(realport + " is not valid a valid port number, insert a value between 1 and 65535");
        }
        while (!this.input.validatePortNumber(realport));

        String validIp = ip;
        int validPort = realport;

        this.terminal.printGoodMessages("Trying to connect to "+ ip + " : "+ realport +"\n");
        this.notifyObserver(controller -> controller.connectToServer(validIp, validPort));


    }

    @Override
    public void connectionfailed()
    {
        terminal.printError("connection failed, try again.");
        this.askServerData();
    }

    @Override
    public void askServerData(String error) {
        this.terminal.printWarning(error);
        this.input.console.nextLine();
        this.clickEnter();
        askServerData();
    }


    @Override
    public void askBuy() {

        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {

        }
        this.notifyObserver(ClientController::showDecks);
        this.notifyObserver(ClientController::showDiscount);

        this.terminal.printRequest("Buy a card");

        int col = askInt("Insert Column coordinate (1-4) of the card you want to buy","wrong index",1,ConstantValues.colDeck) -1;
        if(isExit(col+1)) return;

        int row = askInt("Insert Row    coordinate (1-3) of the card you want to buy","wrong index",1,ConstantValues.rowDeck) -1;
        if(isExit(row+1)) return;

        int pos = askInt("Where you want to put it in your dashboard (1-3)","wrong index",1,ConstantValues.productionSpaces)  -1;
        if(isExit(pos+1)) return;

        this.notifyObserver(controller -> controller.sendBuyCard(row,col,pos));

    }

    @Override
    public void askProduction() {

        notifyObserver(ClientController::showDashboard);

        //SHOW DASHBOARD
        this.terminal.printRequest("Activate a production card on your dashboard");

        int pos = askInt("Select a card to activate (1-3) or type 4 for the basic " + "production","wrong index",1,ConstantValues.productionSpaces + 3)  -1;
        if(isExit(pos)) return;

        if (pos == 3) {
            this.askBasicProduction();
            return;
        }

        if(pos ==4 || pos == 5)
        {
            this.notifyObserver(ClientController::askBonusProductions);
            return;
        }

        this.notifyObserver(controller -> controller.sendProduction(pos));
    }

    @Override
    public void askBonusProduction(BonusProductionInterface[] bonus) {

        if(bonus != null)
        {
            this.terminal.printRequest("This is your bonus productions");
            this.terminal.printBonusCards(bonus);
        }
        else
        {
            this.terminal.printError("You dont have bonus cards");
        }



    }

    @Override
    public void showDiscount(List<Resource> resourceList) {

        if(resourceList!=null && !ResourceOperator.isEmpty(resourceList))
        {
            this.terminal.printWarning("You have the following discount on cards");
            this.terminal.printResourceList(resourceList);
        }

    }

    @Override
    public void askBasicProduction() {
        notifyObserver(ClientController::showDashboard);

        this.terminal.printResourceTypeSelection();
        int type = askInt("Select the first resource to discard.","wrong index",1,4)  -1;
        if(isExit(type)) return;

        int type2 = askInt("Select the second resource to discard.","wrong index",1,4)  -1;
        if(isExit(type2)) return;

        int type3 = askInt("Select the resource you want to obtain.","wrong index",1,4)  -1;
        if(isExit(type3)) return;

        ResourceType res1 = ResourceInterpreter(type);
        ResourceType res2 = ResourceInterpreter(type2);
        ResourceType res3 = ResourceInterpreter(type3);

        this.notifyObserver(controller -> controller.sendBasicProduction(res1, res2, res3));


    }

    public ResourceType ResourceInterpreter(int r1)
    {
        switch (r1) {
            case 0:
                return ResourceType.SHIELD;
            case 1:
                return  ResourceType.ROCK;
            case 2:
                return  ResourceType.COIN;
            case 3:
                return  ResourceType.SERVANT;
        }
        return null;
    }


    @Override
    public void askMarketExtraction()  {
        String msg = "\nInsert \"col\" or \"row\" to select the extraction mode";
        boolean direction = false;
        String in = "";
        int max = 0;
        boolean cond = true;
        showMarket();
        do {
            in = this.customRead(msg);

            if(isExit(in))return; //-EXIT COMMAND

            if(in.equals("col"))
            {
                direction = true;
                max = ConstantValues.marketCol;
                cond = false;
            }else if( in.equals("row"))
            {
                direction = false;
                max = ConstantValues.marketRow;
                cond = false;
            }
            else
            {
                this.terminal.printWarning("Wrong command");
                //this.terminal.printRequest(msg);
            }
        }while(cond);

        msg = "Insert the row/col to extract";
        //this.terminal.printRequest(msg);
        int num = askInt(msg,"wrong market row/col number",1,max);

        if(isExit(num))return; //-EXIT COMMAND

        boolean finalDirection = direction;
        this.notifyObserver(controller -> {controller.sendMarketExtraction(finalDirection,num);});
        actionDone = true;
    }

    @Override
    public void showDecks(ProductionCard[][] productionCards) {
        terminal.printDeks(productionCards);
    }


    /**
     * same of ask Int but with an input i dont want
     * @param except input i dont want
     * @param msg    message to describe input
     * @param error  error to show if user do something wrong
     * @param min    minimum value acceptable
     * @param max    amximum value acceptable
     * @return       an valid integer  in range (min,max) typed by user
     */
    public int askIntExept(String msg,String error,String errorExept,int min,int max,int except)
    {
        int in=0;
        do {
            in = this.askInt(msg,error,min,max);

            if(isExit(in)) return in;
            if(isInputCancelled(in)) return in;

            if(in == except) this.terminal.printError(errorExept);
        }while(in == except);
        return in;
    }
    /**
     * ask for an integer in loop until user insert a valid one
     * @param msg    message to describe input
     * @param error  error to show if user do something wrong
     * @param min    minimum value acceptable
     * @param max    amximum value acceptable
     * @return       an valid integer  in range (min,max) typed by user
     */
    public int askInt(String msg,String error,int min,int max)
    {
        int num =0;
        boolean cond = true;
        do{
            //String in = this.customRead(msg); //Check here
            this.terminal.printRequest(msg);
            String in = this.input.interruptableInput();
            in = helpCommands(in,msg);

            if(isExit(in))
                return InputReaderValidation.exitCode;
            if(isInputCancelled(in))
                return InputReaderValidation.cancellInt;
            try
            {
                num = Integer.parseInt(in);
            }
            catch (Exception e)
            {
                this.terminal.printError("Not an integer");
            }
            cond = !input.validateInt(num,min,max);

            if(cond) this.terminal.printWarning(error);
        }while(cond );

        return num;
    }

    /**
     * show mini market
     *
     */
    @Override
    public void showMarket(){
       terminal.printMarket(this);
    }


    @Override
    public Resource askDiscardResource(Resource res) {

        if(res.getQuantity() > 1)
        {
            String msg = "How much of this resources you want to discard";
            int qty = askInt(msg,"thers not that much quantity",1,res.getQuantity());
            Resource tmp = new Resource(res.getType(),qty);
            return tmp;
        }
        else return res;
    }

    /**
     * Ask user in which deposit he want to insert recived resources
     * @param resourceList ask user where to put recived resources (eventualy call discard resources)
     */
    @Override
    public void askResourceInsertion(List<Resource> resourceList) {
        actionDone = true;
        Avoidable = false;
        this.notifyObserver(ClientController::showStorage);

        canEndTurn = false;
        List<Resource> resDiscarded = new ResourceList();
        List<InsertionInstruction> insertions = new ArrayList<>();

        do
        {

            List<Resource> removed = new ResourceList();
            for(Resource res:resourceList)
            {
                int pos = 0;
                int qty = 0;
                boolean discarded = false;
                if(res.getQuantity()!= 0)
                {
                    do
                    {
                        this.terminal.printSeparator();
                        this.terminal.printResource(res);
                        this.terminal.printRequest("If you want to discard this resource type \"discard\"");
                        this.terminal.printRequest("If you want to keep it type the deposit number (1-3) for normale (4-5) to bonus");
                        this.terminal.printSeparator();

                        String in = this.customRead();
                        if(in.equals("discard"))
                        {
                            //ASK QUANTITY TO DISCARD
                            Resource discRes = this.askDiscardResource(res);
                            discarded = true;
                            removed.add(discRes);

                            //SEND TO SERVER DISCARD MESSAGE
                            this.notifyObserver(controller->{controller.sendResourceDiscard(discRes.getQuantity());});

                            //TODO SI PTOREBBE INVIARE TUTTE LE RISORSE SCARTATE IN BLOCCO (ora lo fa per i singoli tipi di risorsa)
                            resDiscarded.add(discRes);
                        }
                        else
                        {
                            //TRY CONVERTING INPUT TO INT
                            try
                            {
                                pos = Integer.parseInt(in);
                            }
                            catch (Exception exception)
                            {
                                this.terminal.printWarning("Not an integer");
                                pos = -1;
                            }
                            if(!input.validateInt(pos,1,5)) this.terminal.printWarning("Pos not valid");
                        }

                    }while((!input.validateInt(pos,1,5)) && !discarded);

                    if(!discarded)
                    {
                        pos = pos-1;
                        if(res.getQuantity()>1)
                        {
                            String msg = "How much of this resources you want to insert in this deposit";
                            qty = askInt(msg,"thers not that much quantity",1,res.getQuantity());
                            Resource tmp = new Resource(res.getType(),qty);
                            insertions.add(new InsertionInstruction(tmp,pos));
                            removed.add(tmp);
                        }
                        else
                        {
                            removed.add(res);
                            insertions.add(new InsertionInstruction(res,pos));
                        }

                    }

                }
            }
            resourceList = listSubtraction(resourceList,removed);
        DebugMessages.printError("aaaaaa");
        }while(!isEmpty(resourceList));

        this.notifyObserver(controller -> {controller.sendResourceInsertion(insertions);});

        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.notifyObserver(ClientController::showStorage);
        Avoidable = true;
    }

    @Override
    public void askResourceExtraction(List<Resource> resourceList) {
        actionDone = true;
        Avoidable = false;
        this.notifyObserver(ClientController::showStorage);

        this.terminal.printSeparator();
        this.terminal.printRequest("You have the following pending costs to pay");
        this.terminal.printResourceList(resourceList);
        this.terminal.printSeparator();

        boolean flag = false;
        List<Resource> payed = new ResourceList();
        List<ExtractionInstruction> extractions = new ArrayList<>();

        do {
            for(Resource res:resourceList)
            {
                String in="";
                if(res.getQuantity()>0)
                {
                    flag = false;
                    do {
                        this.terminal.printResource(res);
                        in = customRead("Where you want to pick this resources? options ->(storage-chest)");

                        int pos = -1;
                        int quantity = 1;
                        //TODO CERCA POSIZIONE STORAGE IN AUTOMATICO SENZA CHIEDERA ALLUTENTE (storage-chest-bonus)

                        if((in.equals("storage")|| in.equals("chest"))) flag = true;

                        if(flag)
                        {
                            if(in.equals("storage")) {
                                pos = askInt("Which deposit you want to use? normal(1-3) bonus(4-5)", "Wrong deposit number", 1, ConstantValues.maxDepositsNumber) - 1;
                            }
                            Resource tmp = null;

                            if(res.getQuantity()!=1)
                            {
                                quantity = askInt("How much of this resources you want to pay here?","Excessive quantity",1,res.getQuantity());
                                tmp = new Resource(res.getType(),quantity);
                                payed.add(tmp);
                            }

                            if(tmp == null)
                            {
                                tmp = res;
                                payed.add(res);
                            }

                            if(pos == -1) extractions.add(new ExtractionInstruction(tmp));
                            else          extractions.add(new ExtractionInstruction(tmp,pos));
                        }
                    }while(!(in.equals("storage")|| in.equals("chest")));
                }

            }

            resourceList = ResourceOperator.listSubtraction(resourceList,payed);
            payed = new ResourceList();
        }while(!ResourceOperator.isEmpty(resourceList));

        if(turnSelected == 1)
        {
            this.notifyObserver(controller -> controller.sendResourceExtraction(true,extractions));
        }
        else
        {
            this.notifyObserver(controller -> controller.sendResourceExtraction(false,extractions));
        }
        Avoidable = false;
    }

    @Override
    public void askSwapDeposit(int index) {
        int d1 = 0;
        int d2;
        String cmd;
        boolean valid = false;
        //this.terminal.printRequest("\nthis is your storage:");
        this.notifyObserver(ClientController::showStorage);

        //ASK FIRST DEPOSIT (while insert a number in range)
        d1 = askInt("\nselect the first deposit you want to swap. (1-3) for normal (4-5) for bonus (6) to quit the action","invalid input! retry please. \n",1,6);
        if(isInputCancelled(d1)) return; //cancelled input
        if(d1 == 6 ) return;             //6 mean exit

        //ASK SECOND DEPOSIT (while insert a number in range different from d1)
        d2 = askIntExept("\nselect the first deposit you want to swap. (1-3) for normal (4-5) for bonus (6) to quit the action","invalid input! retry please. \n","You cant swap a deposit with itself",1,6,d1);
        if(isInputCancelled(d1)) return; //cancelled input
        if(d2 == 6 ) return;             //6 mean exit

        //Send swap
        int finalD1 = d1;
        int finalD2 = d2;
        this.notifyObserver(controller -> controller.askSwap(finalD1, finalD2, index));

        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.notifyObserver(ClientController::showStorage);


    }

    @Override
    public void askMoveResources()
    {
        this.notifyObserver(ClientController::showStorage);

        int d1 = askInt("\nselect the deposit from where you want to take the resources. (1-3) for normal (4-5) for bonus (6) to quit the action","invalid input! retry please. \n",1,6);
        if(isInputCancelled(d1)) return; //cancelled input
        if(d1 == 6 ) return;             //6 mean exit

        int d2 = askIntExept("\nselect the deposit from where you want to take the resources. (1-3) for normal (4-5) for bonus (6) to quit the action","invalid input! retry please. \n","You selected the same deposit two times!",1,6,d1);
        if(isInputCancelled(d1)) return; //cancelled input
        if(d2 == 6 ) return;             //6 mean exit

        int q = askInt("\nselect the quantity you want to move. Type 4 to quit the action","invalid input! retry please. \n",1,4);
        if(isInputCancelled(q)) return; //cancelled input
        if(q == 6 ) return;             //6 mean exit

        this.notifyObserver(controller -> controller.askMove(d1-1, d2-1, q));

        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.notifyObserver(ClientController::showStorage);

    }

    public void askLeaders(LeaderCard[] cards)
    {
        this.terminal.out.clear();
        this.terminal.printLeaders(cards);

        LeaderCard[] leaderCards = new LeaderCard[2];

        int l1 = this.askInt("Which of those leaders you want to draw? (1-4)","wrong input range",1,ConstantValues.leaderCardsToDraw) -1;

        if(isInputCancelled(l1+1))
        {
            this.askLeaders(cards);
            return;
        }
        leaderCards[0] = cards[l1];

        int l2 = this.askIntExept("Which of those leaders you want to draw? (1-4)","wrong input range","already selected leader",1,ConstantValues.leaderCardsToDraw,l1+1) -1;
        leaderCards[1] = cards[l2];

        this.notifyObserver(controller -> {controller.sendLeader(l1,l2);});
    }

    @Override
    public void askLeaderActivation() {

        int pos = askInt("Which leader you want to activate? (1-2)","wrong input range",1,2)-1;
        if(isInputCancelled(pos))return;

        this.notifyObserver(controller -> controller.activateLeader(pos));

    }

    @Override
    public void askDiscardLeader() {
        int pos = askInt("Which leader you want to discard? (1-2)","wrong input range",1,2)-1;
        if(isInputCancelled(pos))return;

        this.notifyObserver(controller -> controller.discardLeader(pos));
    }

    @Override
    public List<Resource> askWhiteBalls(ResourceType[] resourceTypes,int num) {


        this.terminal.printRequest("You extracted "+num+" white balls and you can choose beetween one of those resources");
        this.terminal.printResourceTypeSelection(resourceTypes);
        List<Resource> chosen = new ResourceList();
        for(int i=0;i<num;i++)
        {
            int res = this.askInt("You have "+num+" to choose","wrong input range",1,resourceTypes.length) -1;
            int qty =1;

            if(num>1) qty = this.askInt("How many balls you want to convert with this type?","wrong input range",1,num) -1;
            chosen.add(new Resource(resourceTypes[res],qty));
            num=num-(qty-1);
        }

        return chosen;

    }

    @Override
    public void askInitialResoruce(int number) {

        if(number == 0)
        {
            firstTurn = false;
            this.askTurnType();
            return;
        }

        boolean flag = number == 2;
        List<Resource> wantedRes = new ResourceList();
        terminal.out.clear();
        this.terminal.printRequest("This is your first turn and you have the right to choose "+number+" resources of your choice");
        for(int i=0;i<number;i++)
        {
            this.terminal.printRequest("Resource types:");
            int j=0;
            terminal.printResourceTypeSelection();
            /*for(ResourceType resourceType:ResourceType.values())
            {
                j++;
                String color = ConstantValues.resourceRappresentation.getColorRappresentation(resourceType);
                this.terminal.out.printlnColored(j + " - " + resourceType.toString(),color);
            }*/
            int num = this.askInt("Insert a number rappresenting the resource you want:","Input not in range",1,ResourceType.values().length);

            ResourceType type = null;
            j=0;
            //FIND RESOURCE TYPE
            for(ResourceType resourceType:ResourceType.values())
            {
                if(resourceType.ordinal() == num-1) type = resourceType;
            }

            num = 1;
            //ONLY FOR FIRST RESOURCE (avoid asking again type if user want 2 equal resource
            if(flag)
            {
                num = this.askInt("How much of this resource you want? 1 or 2?:","Input not in range",1,2);
                flag = false;
                if(num == 2) number = -1;//EXIT CICLE;
            }

            wantedRes.add(new Resource(type,num));
        }

        //Ask user where to insert them
        this.askResourceInsertion(wantedRes);
    }

    @Override
    public void askTurnType() {



        /*//Wait help commands to be completed (eg. if user was already in a input blocking operation as swap,spy,activateleader)
        while(!getHelpStatus())
        {
            try {
                Thread.sleep(200);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }*/
        setHelpKill(false);
        this.input.restart();
        DebugMessages.printError("Input resetted");


        if(firstTurn)
        {
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.input.restart();
            this.notifyObserver(ClientController::askLeaders);
            this.notifyObserver(ClientController::askInitialResoruce);

            return;
        }
        boolean valid = false;
        String cmd = null;
        this.terminal.printTurnTypesHelp();

        while(!valid) {
            //System.out.println("about to call customread");
            cmd = customRead("select what type of turn you want to perform!\n\"1\" to buy a card\n\"2\" to extract from market\n\"3\" to activate production\n\"4\" to skip the turn");
            try
            {
                valid = input.validateInt(Integer.parseInt(cmd), 1, 4);
            }
            catch (Exception e)
            {

            }
            if(!valid)
                terminal.printWarning("you have to type a number between 1 and 4!");
        }
        turnTypeInterpreter(cmd);
    }

    /**
     * ask player which player he want to spy
     */
    public void askSpyPlayer()
    {
        this.terminal.printRequest("Which player you want to spy?");
        this.notifyObserver(ClientController::showAvailableNickname);

        int index = this.askInt("select a player","wrong input range",1,ConstantValues.numberOfPlayer);
        if(isInputCancelled(index)) return;

        index = index-1;
        int finalIndex = index;
        this.notifyObserver(controller -> {controller.spyPlayer(finalIndex);});
    }
    @Override
    public void showPlayer(Deposit[]deposits, List<Resource> chest, ProductionCard[] cards,LeaderCard[] leaderCards,String name) {

        this.terminal.printSeparator();
        this.terminal.out.printColored("THIS IS "+ name+ " Dashboard",CliColors.GREEN_TEXT);
        this.showDashboard(deposits,chest,cards,leaderCards);
    }

    /**
     * ask for help in loop, aborted when turnNotify is recived
     */
    public void waitingHelpLoop()
    {
        try
        {
            this.terminal.printHelp();
            while(waiting)
            {

                while(!this.input.bufferReady())
                {
                    Thread.sleep(100);
                }
                //System.out.println("TREAD VIVO ");

                String exit = this.helpCommands(this.input.interruptableInput(),"");

                if(isInputCancelled(exit))
                {
                    this.input.restart();
                    setHelpKill(true);
                    DebugMessages.printError("Funziona finalmente");
                    return;
                }

            }
        }catch (InterruptedException | IOException e)
        {
            setHelpKill(true);
            DebugMessages.printError("OPSS");
            this.input.restart();

        }

        DebugMessages.printError("Waiting thread help aborted");
    }
    @Override
    public void askCommand() {
        helpThread = new Thread(this::waitingHelpLoop);
        helpThread.start();
    }

    @Override
    public void showGameStarted() {
        this.terminal.printGoodMessages("GAME HAS STARTED");
        //this.terminal.printRequest("Click enter to continue");
    }

    public Logger getTerminal() {
        return terminal;
    }

    public synchronized void setHelpKill(boolean value)
    {
        synchronized (this.lockHelp)
        {
            this.helpAborted = value;
        }

    }

    public synchronized boolean getHelpStatus()
    {
        synchronized (this.lockHelp)
        {
            return this.helpAborted;
        }
    }
    @Override
    public void abortHelp() {
        if(helpThread!=null)
        {

            setHelpKill(false);
            //DebugMessages.printError("HELP ABORTED");
            helpThread.interrupt();

            //I close input stream to avoid Input blocking issue of help thread (stream arent reopened until thread help is dead)

            this.input.interrupt();


            helpThread = null;
            waiting   =false;
        }
    }

    @Override
    public void showMarketExtraction(List<Resource> resourceList, int whiteballs, ResourceType[] types) {
        terminal.printGoodMessages("You extracted the following resources from market");
        terminal.printResourceList(resourceList);

        List<Resource> out = new ResourceList();

        out.addAll(resourceList);
        if(whiteballs>0)
        {
            out.addAll(this.askWhiteBalls(types,whiteballs));
        }

        this.askResourceInsertion(out);

    }

    @Override
    public void setMiniMarketDiscardedResouce(BasicBall miniMarketDiscardedResouce) {
        this.miniMarketDiscardedResouce = miniMarketDiscardedResouce;
    }

    @Override
    public void showStorage(Deposit[] deposits,List<Resource>chest) {
        this.terminal.printStorage(deposits,chest,true);
    }

    @Override
    public void showDashboard(Deposit[] deposits, List<Resource> chest, ProductionCard[] cards,LeaderCard[] leaderCards) {
        this.terminal.printDashboard(deposits,chest,cards);
        this.terminal.printLeaders(leaderCards);
    }

    /**
     * Called ONLY by operationCompleted packet (if want to call in help use helpEndTurn())
     */
    @Override
    public void askEndTurn() {

        if(firstTurn)
        {
            firstTurn = false;
            actionDone = false;
            this.askTurnType();
            return;
        }

        canEndTurn = true;
        terminal.printGoodMessages("Your last action has been successfully completed");
        //terminal.printRequest("Do you want to end turn? (yes or no)");
        String in = null;
        if(turnSelected == 2) {
            this.terminal.printWarning("you completed the action and the turn automatically ended.");
            in = "y";
            actionDone = false;
        }
        else
            in = this.customRead("\nDo you want to end the turn? (yes or no)");
        in = in.toLowerCase(Locale.ROOT);
        if(in.equals("yes") || in.equals("y")) {
            actionDone = false;
            this.notifyObserver(controller -> controller.sendMessage(new EndTurn()));
            if(!this.singlePlayer)
                this.waitturn();
        }
        else
        {
            if(turnSelected == 1)
            {
                this.askBuy();
            }
            else if(turnSelected == 2)
            {
                actionDone = false;
                this.notifyObserver(controller -> controller.sendMessage(new EndTurn()));
            }
            else if(turnSelected == 3)
            {
                this.askProduction();
            }
            else if(turnSelected == 4)
            {
                this.askEndTurn();
            }
            else
            {
                actionDone = false;
                this.notifyObserver(controller -> controller.sendMessage(new EndTurn()));
            }
        }
    }


    /**
     *  open a thread to wait turn (ask help in loop)
     *  the thread is "aborted" when turn notify is recived
     */
    public void waitturn(){
        //terminal.printSeparator();
        //terminal.printGoodMessages("sto aspettando il mio turno");
        //terminal.printSeparator();
        waiting = true;
        try {
            TimeUnit.MILLISECONDS.sleep(110);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        helpThread = new Thread(this::waitingHelpLoop);
        helpThread.start();

    }

    @Override
    public void playerLogged(String nickname) {
        this.terminal.printGoodMessages(nickname + " joined the game");
    }



    public void turnTypeInterpreter(String cmd)
    {
            switch (cmd) {
                case "1":
                    turnSelected =1;
                    this.askBuy();
                    break;
                case "market":
                case "2":
                    turnSelected =2;
                    this.askMarketExtraction();
                    break;
                case "3":
                    turnSelected =3;
                    this.askProduction();
                    break;
                case "4":
                    turnSelected =4;
                    this.askEndTurn();
                    break;
                default:
                    break;
            }
            Avoidable  = true;
            actionDone = false;
            DebugMessages.printWarning("Turn executed");
    }

}
