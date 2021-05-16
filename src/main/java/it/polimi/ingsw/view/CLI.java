package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.utils.ConstantValues;
import it.polimi.ingsw.view.observer.Observable;
import it.polimi.ingsw.view.utils.CliColors;
import it.polimi.ingsw.view.utils.InputReaderValidation;
import it.polimi.ingsw.view.utils.Logger;

import java.util.List;

public class CLI extends Observable<ClientController> implements View {

    Logger                  terminal; //print formatted and colored text on the cli
    InputReaderValidation   input;

    public CLI()
    {
        input = new InputReaderValidation();
        terminal = new Logger();
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
        this.terminal.out.printColored("Click enter to continue",CliColors.RED_TEXT,CliColors.BLACK_BACKGROUND);
        this.input.enter();
        this.terminal.out.clear();
        this.terminal.out.print("\033[H\033[2J");
    }

    @Override
    public void askNickname() {

        terminal.printRequest("Type here your nickname:");

        String nickname = ".";
        do {
            nickname = input.readLine(3);
            if(nickname.length() == 0) terminal.printWarning("Nickname too short, minimum 3 letters");
        }while(nickname.length() == 0);


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
        //this.notifyObserver(controller -> controller.buyCard());

        //per evitare loop bisognera inserire nell'input reader un comando
        // che verifichi se lutente ha scritto cancel o qualcosa del genere per tprnare al menu con i comandi
    }

    @Override
    public void askProduction() {

    }

    @Override
    public void askBonusProduction() {

    }

    @Override
    public void askBasicProduction() {

    }

    @Override
    public void askMarketExtraction() {
        this.notifyObserver(controller -> {
            controller.sendMarketExtraction(false, 1);
        });
    }

    @Override
    public void askDiscardResource(List<Resource> resourceList) {

    }

    @Override
    public void askResourceInsertion(List<Resource> resourceList) {

    }

    @Override
    public void askResourceExtraction(List<Resource> resourceList) {

    }

    @Override
    public void askSwapDeposit() {

    }

    @Override
    public void askTurnType() {

        this.terminal.printTurnTypesHelp();
        String cmd = this.input.readLine();
        turnTypeInterpreter(cmd);
    }

    @Override
    public void askCommand() {
        this.terminal.printHelp();
        String cmd = this.input.readLine();

        commandInterpreter(cmd);
    }

    @Override
    public void showGameStarted() {
        this.terminal.printGoodMessages("GAME HAS STARTED");
        this.terminal.printRequest("Click enter to continue");
    }

    @Override
    public void showMarketExtraction(List<Resource> resourceList, int whiteballs) {
        terminal.printGoodMessages("You extracted the following resources from market");
        for(Resource r : resourceList)
        {
            if(r.getQuantity()!=0)
            {
                terminal.printWarning(r.getQuantity() + " " +r.getType().toString() );
            }
        }
        //TODO CHIEDERE DOVE INSERIRE LE RISORSE O SE NE VUOLE SCARTARE
        //TODO(eg insert -c -cancel to discard this resource else the deposit to select)
    }

    @Override
    public void playerLogged(String nickname) {
        this.terminal.printGoodMessages(nickname + " joined the game");
    }



    public void turnTypeInterpreter(String cmd)
    {
        switch (cmd) {
            case "market":
            case "2":
                this.askMarketExtraction();
                break;
            case "3":
                this.askProduction();
                break;
            default:
                this.askBuy();
                break;
        }
    }
    public void commandInterpreter(String cmd)
    {

        switch (cmd)
        {
            case "q":
                break;
            case "cancel":
            case "0":
                //Do nothing
                break;
            case "1":
                this.notifyObserver(ClientController::sendStartCommand);
                break;
            case "2":
                //Show dashboard
                this.notifyObserver(controller -> {controller.sendMarketExtraction(false,1);});
                break;
            case "3":
                //SwapDeposit
                break;
            case "4":
                //SpyPlayer
                break;
            default:
                break;
        }
    }


}
