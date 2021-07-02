package it.polimi.ingsw.view.scenes;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Fading message that disappear in some seconds
 */
public class ToastController extends BasicSceneUpdater{

    @FXML
    Label msgToast;

    String msg;
    public ToastController(String msg)
    {
        this.msg = msg;
    }

    @Override
    public void init() {
        super.init();
        msgToast.setText(msg);
    }
}
