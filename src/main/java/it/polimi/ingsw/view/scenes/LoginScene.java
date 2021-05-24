package it.polimi.ingsw.view.scenes;

import it.polimi.ingsw.view.GuiHelper;
import it.polimi.ingsw.view.utils.FXMLpaths;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginScene extends BasicSceneUpdater{

    @FXML
    TextField nickname;


    public LoginScene()
    {

    }

    public void login()
    {
        if(!nickname.getText().equals(""))
        {
            this.notifyObserver(controller -> controller.setNickname(nickname.getText(),false));
            try {
                GuiHelper.setRoot(FXMLpaths.waitingStart);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
        {
            //TODO show error "nickname to short"
        }

    }
}
