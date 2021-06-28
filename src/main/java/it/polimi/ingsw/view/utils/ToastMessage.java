package it.polimi.ingsw.view.utils;

import it.polimi.ingsw.view.GuiHelper;
import it.polimi.ingsw.view.scenes.BasicSceneUpdater;
import it.polimi.ingsw.view.scenes.SpyScene;
import it.polimi.ingsw.view.scenes.ToastController;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;

public class ToastMessage {

    Pane root;
    String msg;
    int    expiringTime;
    static int fadeInTime;
    static int fadeOutTime;
    AnchorPane toast = null;

    Stage container;

    public ToastMessage(String msg, Pane root,int time)
    {
        this.msg = msg;
        this.root =root;
        this.expiringTime = time;
    }

    public ToastMessage(String msg,int time)
    {
        this.msg = msg;
        this.expiringTime = time;
    }

    /**
     * Create a message stage that disappear after expiring time
     */
    public void show()
    {
        try {
            container = new Stage();
            container.initOwner(GuiHelper.getStage());
            container.initStyle(StageStyle.TRANSPARENT);
            Scene scene = new Scene(GuiHelper.loadFXML(FXMLpaths.toast, new ToastController(msg)));
            scene.setFill(Color.TRANSPARENT);

            container.setScene(scene);

            container.show();

            Timeline fadeInTimeline = new Timeline();
            KeyFrame fadeInKey1 = new KeyFrame(Duration.millis(expiringTime), new KeyValue(container.getScene().getRoot().opacityProperty(), 0));
            fadeInTimeline.getKeyFrames().add(fadeInKey1);
            fadeInTimeline.setOnFinished((ae) -> container.close());
            fadeInTimeline.play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
