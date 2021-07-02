package it.polimi.ingsw.view.scenes;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;

/**
 * Scene used to dialog
 */
public class BasicDialog extends BasicSceneUpdater{

    @FXML
    public DialogPane dialog;

    boolean isReady = false;

    public boolean isReady() {
        return isReady;
    }

    public Button getControlButton(ButtonType type)
    {
        return (Button) dialog.lookupButton(type);
    }
}
