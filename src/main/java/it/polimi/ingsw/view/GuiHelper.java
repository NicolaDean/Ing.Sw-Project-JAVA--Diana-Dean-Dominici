package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.view.utils.FXMLpaths;
import it.polimi.ingsw.viewtest.Appp;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class GuiHelper extends Application {

    private static Scene scene;
    private static GUI   gui;

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("JAVAFX TEST");

        try {
            scene = new Scene(loadFXML(FXMLpaths.home));
        } catch (IOException e) {
            e.printStackTrace();
        }

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static GUI getGui()
    {
        return gui;
    }


    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Appp.class.getResource("/fxml/"+fxml+".fxml"));
        return loader.load();
    }

    public static void main(GUI g) {
        gui = g;
        launch();

    }

}
