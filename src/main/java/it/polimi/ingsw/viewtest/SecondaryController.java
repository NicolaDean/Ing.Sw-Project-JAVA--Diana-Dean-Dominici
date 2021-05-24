package it.polimi.ingsw.viewtest;

import javafx.fxml.FXML;

import java.io.IOException;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        Appp.setRoot("primary");
    }
}