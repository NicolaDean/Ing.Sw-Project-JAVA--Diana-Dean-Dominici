package it.polimi.ingsw.view.scenes;

import it.polimi.ingsw.view.GuiHelper;
import it.polimi.ingsw.view.scenes.BasicSceneUpdater;
import it.polimi.ingsw.view.utils.FXMLpaths;
import it.polimi.ingsw.view.utils.RandomNicks;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

/**
 * ask user nickname
 */
public class LoginScene extends BasicSceneUpdater {

    @FXML
    TextField nickname;


    public LoginScene()
    {

    }

    @Override
    public void init() {
        super.init();
        //GuiHelper.resize(800,600);
    }

    public void login()
    {
        if(!nickname.getText().equals(""))
        {
            this.notifyObserver(controller -> controller.setNickname(nickname.getText(),GuiHelper.getGui().isSingleplayer()));
            try {
                GuiHelper.setRoot(FXMLpaths.waitingStart);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
        {
            this.notifyObserver(controller -> controller.setNickname(RandomNicks.getRandomNickname(),GuiHelper.getGui().isSingleplayer()));
            try {
                GuiHelper.setRoot(FXMLpaths.waitingStart);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
