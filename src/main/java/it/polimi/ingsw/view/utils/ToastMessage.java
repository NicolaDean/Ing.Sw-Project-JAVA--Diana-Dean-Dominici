package it.polimi.ingsw.view.utils;

import it.polimi.ingsw.view.GuiHelper;
import it.polimi.ingsw.view.scenes.BasicSceneUpdater;
import it.polimi.ingsw.view.scenes.SpyScene;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;

public class ToastMessage {

    Pane root;
    String msg;
    int    expiringTime;
    AnchorPane toast = null;

    public ToastMessage(String msg, Pane root,int time)
    {
        this.msg = msg;
        this.root =root;
        this.expiringTime = time;
    }

    public void show()
    {
        try {

            toast = (AnchorPane)GuiHelper.loadFXML(FXMLpaths.toast, new BasicSceneUpdater());
            ((Label)toast.getChildren().get(1)).setText(this.msg);

            this.root.getChildren().add(toast);

            toast.setLayoutX(this.root.getWidth()/2);
            toast.setLayoutY(this.root.getHeight()/2);

            FadeTransition fade = new FadeTransition();
            //setting the duration for the Fade transition
            fade.setDuration(Duration.millis(this.expiringTime));

            //setting the initial and the target opacity value for the transition
            fade.setFromValue(10);
            fade.setToValue(0);

            //the transition will set to be auto reversed by setting this to true
            fade.setAutoReverse(true);

            //setting Circle as the node onto which the transition will be applied
            fade.setNode(toast);

            //playing the transition
            fade.play();

        } catch (IOException e) {
            e.printStackTrace();
        }

        autoKill();
    }

    public void autoKill()
    {
        new Thread(()->{
            try {
                Thread.sleep(this.expiringTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Platform.runLater(()->{
                this.root.getChildren().remove(toast);
            });
        }).start();
    }
}
