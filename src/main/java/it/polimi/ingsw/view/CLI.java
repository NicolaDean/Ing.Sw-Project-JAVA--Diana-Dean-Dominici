package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.controller.packets.EndTurn;
import it.polimi.ingsw.controller.packets.ExtractionInstruction;
import it.polimi.ingsw.controller.packets.InsertionInstruction;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.MiniModel;
import it.polimi.ingsw.model.cards.ProductionCard;
import it.polimi.ingsw.model.dashboard.Deposit;
import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.market.balls.BasicBall;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceList;
import it.polimi.ingsw.model.resources.ResourceOperator;
import it.polimi.ingsw.utils.ConstantValues;
import it.polimi.ingsw.utils.DebugMessages;
import it.polimi.ingsw.view.observer.Observable;
import it.polimi.ingsw.view.utils.CliColors;
import it.polimi.ingsw.view.utils.InputReaderValidation;
import it.polimi.ingsw.view.utils.Logger;


import static it.polimi.ingsw.model.resources.ResourceOperator.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;



public class CLI extends Observable<ClientController> implements View {

    Logger                  terminal; //print formatted and colored text on the cli
    InputReaderValidation   input;
    Thread                  helpThread;
    boolean                 waiting;
    int                     turnSelected;
    private BasicBall[][]   miniMarketBalls;
    private BasicBall       miniMarketDiscardedResouce;
    boolean canEndTurn;
    boolean actionDone;

    public CLI()
    {
        waiting = true;
        input = new InputReaderValidation();
        terminal = new Logger();
        turnSelected = -1;
        canEndTurn = false;
    }

    public void setMarket(BasicBall[][] balls, BasicBall discarted){
        miniMarketBalls=balls;
        miniMarketDiscardedResouce=discarted;
    }

    public BasicBall[][] getMiniMarketBalls() {
        return miniMarketBalls;
    }

    public BasicBall getMiniMarketDiscardedResouce() {
        return miniMarketDiscardedResouce;
    }

    public String helpCommands(String cmd, String message)
    {
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

            case "-exit": //cancel case
                this.notifyObserver(controller -> {controller.sendMessage(new EndTurn());});
                return customRead(message);

            case "-startgame": //cancel case
                this.notifyObserver(ClientController::sendStartCommand);
                return cmd;
            case "-dashboard": //cancel case
                this.notifyObserver(ClientController::showDashboard);
                return customRead(message);
            case "-shop":
                this.notifyObserver(ClientController::showDecks);
                return customRead(message);
            case "-swapdeposits": //cancel case
                customRead(message);
                return customRead(message);

            case "-spy": //cancel case
                customRead(message);
                return customRead(message);

            default:
                //System.out.println("opzione di default, cmd vale "+cmd);
                return cmd;

        }
    }
    public synchronized String customRead()
    {
        //System.out.println("!!!!!!!!!!!!!!!!!!!");
        String s = this.input.readLine();
        //System.out.println("???????????????????");
       // System.out.println("s vale: "+s);
        s = helpCommands(s,"");
        return s;
    }

    public String waitRead()
    {
        String s = this.input.readLine();
        s = helpCommands(s,"");
        waitRead();
        return s;
    }

    public String customRead(String message)
    {
        //System.out.println("entrato nella custom con messaggio");
        terminal.printRequest(message);
        String s = this.input.readLine();
        //System.out.println("cazo culo "+s);
        s = helpCommands(s,message);
        //System.out.println("tette: "+s);
        return s;
    }

    @Override
    public void printWelcomeScreen() {
        this.terminal.printLogo();
        this.terminal.out.setBackgroundColor(CliColors.BLACK_BACKGROUND);
        this.clickEnter();
    }

    @Override
    public void showError() {

    }

    public void clickEnter() {
        this.terminal.out.printlnColored("Click enter to continue",CliColors.RED_TEXT,CliColors.BLACK_BACKGROUND);
        this.input.enter();
        this.terminal.out.clear();
        this.terminal.out.print("\033[H\033[2J");
    }

    @Override
    public void askNickname() {

        terminal.printRequest("Type here your nickname:");

        String nickname = "";
        do {
            nickname = input.readLine();
            //System.out.println("length: "+nickname.length());
            if(nickname.length() < 3) terminal.printWarning("Nickname too short, minimum 3 letters");
        }while(nickname.length() < 3 );


        String validNickname = nickname;
        this.notifyObserver(controller -> controller.setNickname(validNickname,true));

        //Example set action to do in case of NACK on Login command
        //this.notifyObserver(controller -> controller.setAckManagmentAction(View::askNickname));
    }

    @Override
    public void askServerData()
    {
        terminal.printRequest("Insert a valid server IP: ( empty for default: localhost ) ");

        String ip =".";
        int port = -1;

        do {
            ip= this.input.readLine();
            if(ip.length()==0) ip = ConstantValues.defaultIP;
            if(ip.equals(".")) terminal.printWarning("please, insert a valid IP address");
        }
        while(!this.input.validateIP(ip));

        terminal.printRequest("Insert server port: ( 0 for default: 1234 ) ");

        do {
            port = this.input.readInt();
            if(port == 0) port = 1234;
            if(!this.input.validatePortNumber(port)) terminal.printWarning(port + " is not valid a valid port number, insert a value between 1 and 65535");
        }
        while (!this.input.validatePortNumber(port));

        String validIp = ip;
        int validPort = port;

        this.terminal.printGoodMessages("Trying to connect to "+ ip + " : "+ port +"\n");
        this.notifyObserver(controller -> controller.connectToServer(validIp, validPort));
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

        this.notifyObserver(ClientController::showDecks);
        this.terminal.printRequest("Buy a card");

        int col = askInt("Insert Column coordinate (1-4) of the card you want to buy","wrong index",1,ConstantValues.colDeck) -1;
        int row = askInt("Insert Row    coordinate (1-3) of the card you want to buy","wrong index",1,ConstantValues.rowDeck) -1;
        int pos = askInt("Where you want to put it in your dashboard (1-3)","wrong index",1,ConstantValues.productionSpaces)  -1;

        this.notifyObserver(controller -> controller.sendBuyCard(row,col,pos));
        actionDone = true;
    }

    @Override
    public void askProduction() {

        //SHOW DASHBOARD
        this.terminal.printRequest("Activate a production card on your dashboard");

        int pos = askInt("Select a card to activate (1-3)","wrong index",1,ConstantValues.productionSpaces)  -1;

        this.notifyObserver(controller -> controller.sendProduction(pos));
        actionDone = true;
    }

    @Override
    public void askBonusProduction() {

    }

    @Override
    public void askBasicProduction() {

    }

    @Override
    public void askMarketExtraction() {
        String msg = "\nInsert \"col\" or \"row\" to select the extraction mode";
        boolean direction = false;
        String in = "";
        int max = 0;
        boolean cond = true;
        showMarket();
        do {
            in = this.customRead(msg);
            //System.out.println("in ora vale: "+in);

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


        boolean finalDirection = direction;
        this.notifyObserver(controller -> {controller.sendMarketExtraction(finalDirection,num);});
        actionDone = true;
    }

    @Override
    public void showDecks(ProductionCard[][] productionCards) {
        terminal.printDeks(productionCards);
    }

    public int askInt(String msg,String error,int min,int max)
    {
        int num =0;
        boolean cond = true;
        do{
            String in = this.customRead(msg);
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

        }while(!isEmpty(resourceList));

        this.notifyObserver(controller -> {controller.sendResourceInsertion(insertions);});
    }

    @Override
    public void askResourceExtraction(List<Resource> resourceList) {
        canEndTurn = false;
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
                if(res.getQuantity()!=0)
                {
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
                                payed.add(tmp);
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

    }

    @Override
    public void askSwapDeposit() {

    }

    @Override
    public void askTurnType() {
      //  System.out.println("eccomi nell'askturntype");
        boolean valid = false;
        String cmd = null;
       // System.out.println("valid vale "+ valid);
        this.terminal.printTurnTypesHelp();
       // System.out.println("valid vale "+ valid);
        while(!valid) {
            //System.out.println("about to call customread");
            cmd = customRead("select what type of turn you want to perform!\n\"1\" to buy a card\n\"2\" to extract from market\n\"3\" to activate production\n\"4\" to skip the turn");
            //System.out.println("cmd is now "+cmd);
            try{
                //tem.out.println("cmd vale:"+cmd);
            valid = input.validateInt(Integer.parseInt(cmd), 1, 4);}
            catch (Exception e)
            {

            }
            if(!valid)
                terminal.printWarning("you have to type a number between 1 and 4!");
        }
        turnTypeInterpreter(cmd);
    }

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

                this.helpCommands(this.input.readLine(),"");

            }
        }catch (InterruptedException | IOException e)
        {
            //DebugMessages.printError("OPSS");
        }

        //DebugMessages.printError("Waiting thread help aborted");
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

    @Override
    public void abortHelp() {
        if(helpThread!=null)
        {
            DebugMessages.printError("HELP ABORTED");
            helpThread.interrupt();
            helpThread = null;
            waiting   =false;
        }
    }

    @Override
    public void showMarketExtraction(List<Resource> resourceList, int whiteballs) {
        terminal.printGoodMessages("You extracted the following resources from market");
        terminal.printResourceList(resourceList);

        //TODO CHIEDERE PRIMA COME CONVERTIRE LE PALLINE BIANCHE, AGGIUNGERE LE NUOVE PALLINE ALLA RESOURCE LIST
        //this.askWhiteBalls();
        this.askResourceInsertion(resourceList);

    }

    @Override
    public void showStorage(Deposit[] deposits) {
        this.terminal.printStorage(deposits,null,false);
    }

    @Override
    public void showDashboard(Deposit[] deposits, List<Resource> chest, ProductionCard[] cards) {
        this.terminal.printDashboard(deposits,chest,cards);
    }

    public void changeTurnType()
    {
        if(!actionDone)
        {
            //askTurnType
            DebugMessages.printError("This function will COMING SOON,non ready yet");
        }
    }
    public void helpEndTurn()
    {
        if(canEndTurn)
        {
            this.askEndTurn();
        }
        else
        {
            terminal.printError("You cant end turn now, you probably have pending cost to pay or resources to insert");
        }
    }

    /**
     * Called ONLY by operationCompleted packet (if want to call in help use helpEndTurn())
     */
    @Override
    public void askEndTurn() {
        canEndTurn = true;
        actionDone = true;
        terminal.printGoodMessages("Your last action has been successfully completed");
        //terminal.printRequest("Do you want to end turn? (yes or no)");

        String in = this.customRead("Do you want to end the turn? (yes or no)");
        in = in.toLowerCase(Locale.ROOT);
        if(in.equals("yes") || in.equals("y")) {
            this.notifyObserver(controller -> controller.sendMessage(new EndTurn()));
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
                this.notifyObserver(controller -> controller.sendMessage(new EndTurn()));
            }
        }
    }

    public void waitturn(){
        //terminal.printSeparator();
        //terminal.printGoodMessages("sto aspettando il mio turno");
        //terminal.printSeparator();
        waiting = true;
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
                //System.out.println("sono entrato con cmd= "+cmd );
                turnSelected =1;
                this.askBuy();
                break;
        }
    }

}
