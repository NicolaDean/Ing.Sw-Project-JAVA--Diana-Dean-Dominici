package it.polimi.ingsw.view.scenes;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.utils.ConstantValues;
import it.polimi.ingsw.view.GuiHelper;
import it.polimi.ingsw.view.scenes.BasicSceneUpdater;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class AskServerData extends BasicSceneUpdater {

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

        errorMsg.setOpacity(0);
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
            errorMsg("ERRORRRRRRRR TRY AGAIN");

        });
    }

    //TODO Create a custom Toad message class to use in every scene
    public void errorMsg(String msg)
    {
        ((Label)this.errorMsg.getChildren().get(0)).setText(msg); //Instantiating FadeTransition class

        FadeTransition fade = new FadeTransition();
        //setting the duration for the Fade transition
        fade.setDuration(Duration.millis(4000));

        //setting the initial and the target opacity value for the transition
        fade.setFromValue(10);
        fade.setToValue(0);

        //the transition will set to be auto reversed by setting this to true
        fade.setAutoReverse(true);

        //setting Circle as the node onto which the transition will be applied
        fade.setNode(errorMsg);

        //playing the transition
        fade.play();
    }
}
