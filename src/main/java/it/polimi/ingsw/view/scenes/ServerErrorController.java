package it.polimi.ingsw.view.scenes;

import it.polimi.ingsw.view.GuiHelper;
import it.polimi.ingsw.view.utils.FXMLpaths;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class ServerErrorController extends BasicSceneUpdater{

    @FXML
    Button goHome;

    @Override
    public void init() {
        super.init();

        goHome.setOnMouseClicked(mouseEvent -> {
            try {
                GuiHelper.setRoot(FXMLpaths.home);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
