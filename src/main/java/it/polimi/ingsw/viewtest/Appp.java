package it.polimi.ingsw.viewtest;

import it.polimi.ingsw.App;
import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.view.GUI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import java.io.IOException;

/**
 * JavaFX App
 */
public class Appp extends Application {

    private static Scene scene;
    private static GUI   gui;

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("JAVAFX TEST");

        Canvas canvas = new Canvas(1000, 500);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        //Creating a Group object
        Group root = new Group(); //METTI ROOT in scene al posto di loadFXML

        int k = 1;
        for(int i=0;i<4;i++)
        {
            for(int j=0;j<4;j++)
            {
                drawLeadere(k,25 + i*210,root,j);
                k++;
            }

        }

        Button btn = new Button("Move leader to right");
        btn.setLayoutX(800);
        btn.setLayoutY(400);



        try {
            scene = new Scene(loadFXML("home"));
            //scene = new Scene(root); //DECOMMENTA PER IMMAGINE
        } catch (IOException e) {
            e.printStackTrace();
        }


        primaryStage.setScene(scene);
        primaryStage.show();


        gui.askNickname();
        gui.notifyObserver(ClientController::endGame);
    }


    static void drawLeadere(int id,int height,Group group,int padding)
    {
        //Creating an image
        Image image = new Image(Appp.class.getResourceAsStream("/images/cards/leader/" +id+".jpg"));

        //Setting the image view
        ImageView imageView = new ImageView(image);
        imageView.setId("Leader."+id);
        //Setting the position of the image
        imageView.setX(50 + padding*110);
        imageView.setY(height);

        //setting the fit height and width of the image view
        imageView.setFitHeight(200);
        imageView.setFitWidth(100);

        group.getChildren().add(imageView);
        //canvas.drawImage(image,50 + padding*210,25,200,400);
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