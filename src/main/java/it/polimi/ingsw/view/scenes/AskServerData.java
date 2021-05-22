package it.polimi.ingsw.view.scenes;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.utils.ConstantValues;
import it.polimi.ingsw.view.GuiHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AskServerData {

    @FXML
    TextField ip;

    @FXML
    TextField port;

    public void tryConnection()
    {
        String ip   = this.ip.getText();
        String prt  = this.port.getText();
        int    port = ConstantValues.defaultServerPort;


        if(ip.equals(""))    ip   = ConstantValues.defaultIP;
        if(!prt.equals(""))  port = Integer.parseInt(prt);

        int finalPort = port;
        String finalIp = ip;

        GuiHelper.getGui().notifyObserver(controller -> controller.connectToServer(finalIp, finalPort));
    }
}
