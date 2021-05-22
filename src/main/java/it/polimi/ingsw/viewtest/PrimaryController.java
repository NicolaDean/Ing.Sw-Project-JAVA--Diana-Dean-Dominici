package it.polimi.ingsw.viewtest;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class PrimaryController {

    int opacity = 1;
    @FXML
    Label labelbrutto;

    @FXML
    private void switchToSecondary() throws IOException {
        Appp.setRoot("secondary.fxml");
        if(opacity ==0) opacity = 1;
        else opacity =0;

        labelbrutto.setOpacity(opacity);
    }
}
