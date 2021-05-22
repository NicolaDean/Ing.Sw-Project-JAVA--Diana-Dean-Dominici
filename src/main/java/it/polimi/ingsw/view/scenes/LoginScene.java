package it.polimi.ingsw.view.scenes;

import it.polimi.ingsw.view.GuiHelper;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoginScene {

    @FXML
    TextField nickname;

    public void login()
    {
        if(!nickname.getText().equals("")) GuiHelper.getGui().notifyObserver(controller -> controller.setNickname(nickname.getText(),false));

            //TODO SPOSTARE IL GENERATORE DI NICKNAME IN UNA CLASSE A PARTE COSI DA POTERLO USARE ANCHER NELLA GUI
    }
}
