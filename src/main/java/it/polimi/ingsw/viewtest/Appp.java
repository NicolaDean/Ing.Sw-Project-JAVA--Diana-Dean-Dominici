package it.polimi.ingsw.viewtest;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import java.io.IOException;

/**
 * JavaFX App
 */
public class Appp extends Application {

    private static Scene scene;

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("JAVAFX TEST");

        Canvas canvas = new Canvas(1000, 500);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        //Creating a Group object
        Group root = new Group(); //METTI ROOT in scene al posto di loadFXML
        for(int i=1;i<4;i++) drawLeadere(i,root,i);
        try {
            scene = new Scene(loadFXML("primary.fxml"));
            scene = new Scene(root); //DECOMMENTA PER IMMAGINE
        } catch (IOException e) {
            e.printStackTrace();
        }


        primaryStage.setScene(scene);
        primaryStage.setWidth(1280d);
        primaryStage.setHeight(720d);
        primaryStage.show();
    }


    static void drawLeadere(int id,Group group,int padding)
    {
        //Creating an image
        Image image = new Image(Appp.class.getResourceAsStream("/cards/leader/"+id+".jpg"));

        //Setting the image view
        ImageView imageView = new ImageView(image);

        //Setting the position of the image
        imageView.setX(50 + padding*160);
        imageView.setY(25);

        //setting the fit height and width of the image view
        imageView.setFitHeight(300);
        imageView.setFitWidth(150);

        group.getChildren().add(imageView);
        //canvas.drawImage(image,50 + padding*210,25,200,400);
    }
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Appp.class.getResource("/fxml/"+fxml));
        return loader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}