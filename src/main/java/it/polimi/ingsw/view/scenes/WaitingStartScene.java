package it.polimi.ingsw.view.scenes;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.view.GuiHelper;
import it.polimi.ingsw.view.scenes.BasicSceneUpdater;
import it.polimi.ingsw.view.utils.FXMLpaths;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Stage;


//TODO TROVARE UN MODO DI RENDERE I CONTROLLORI DELLE SCENE VISIBILI DALLE FUNZIONI DELLA GUI
public class WaitingStartScene extends BasicSceneUpdater {

    int userMsgPadding = 0;

    @FXML
    Button start;

    @FXML
    Label first_user;
    @FXML
    Label second_user;
    @FXML
    Label third_user;
    @FXML
    Label fourth_user;

    public WaitingStartScene()
    {

    }

    @Override
    public void init() {
        super.init();
        first_user  .setOpacity(0);
        second_user .setOpacity(0);
        third_user  .setOpacity(0);
        fourth_user .setOpacity(0);
        GuiHelper.resize(800,600);
    }

    public void startGame()
    {
        this.notifyObserver(ClientController::sendStartCommand);
        //this.notifyObserver(ClientController::askBuy);
    }

    public void setUser(Label label,String name)
    {
        label.setOpacity(1);
        label.setText(name);
    }

    @Override
    public void reciveMessage(String msg)
    {
        if(userMsgPadding == 0) setUser(first_user,msg);
        if(userMsgPadding == 1) setUser(second_user,msg);
        if(userMsgPadding == 2) setUser(third_user,msg);
        if(userMsgPadding == 3) setUser(fourth_user,msg);

        userMsgPadding++;
    }


}
