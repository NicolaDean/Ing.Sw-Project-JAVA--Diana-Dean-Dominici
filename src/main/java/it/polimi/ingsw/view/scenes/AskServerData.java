package it.polimi.ingsw.view.scenes;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.utils.ConstantValues;
import it.polimi.ingsw.view.GuiHelper;
import it.polimi.ingsw.view.scenes.BasicSceneUpdater;
import it.polimi.ingsw.view.utils.ToastMessage;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class AskServerData extends BasicSceneUpdater {

    @FXML
    public StackPane root;

    @FXML
    TextField ip;

    @FXML
    TextField port;

    @FXML
    Pane errorMsg;

    public AskServerData()
    {

    }

    @Override
    public void init() {
        super.init();

       // errorMsg.setOpacity(0);
        //GuiHelper.resize(800,600);



    }

    public void tryConnection()
    {
        String ip   = this.ip.getText();
        String prt  = this.port.getText();
        int    port = ConstantValues.defaultServerPort;


        if(ip.equals(""))    ip   = ConstantValues.defaultIP;
        if(!prt.equals(""))  port = Integer.parseInt(prt);

        int finalPort = port;
        String finalIp = ip;

        this.notifyObserver(controller -> {
            controller.connectToServer(finalIp, finalPort);

            if(controller.isConnected()) return;
            errorMsg("ERRORRRRRRRR TRY AGAIN");

        });
    }

    //TODO Create a custom Toad message class to use in every scene
    public void errorMsg(String msg)
    {
        double x =  (GuiHelper.getStage().getHeight()/2);
        double y =  (GuiHelper.getStage().getWidth()/2);

        ToastMessage t = new ToastMessage(msg,this.root,2000);
        t.show();
    }
}
