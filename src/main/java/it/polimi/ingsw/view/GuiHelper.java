package it.polimi.ingsw.view;

import it.polimi.ingsw.model.cards.ProductionCard;
import it.polimi.ingsw.model.dashboard.Deposit;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.view.events.ChestEvent;
import it.polimi.ingsw.view.events.DecksEvent;
import it.polimi.ingsw.view.events.GenericMessage;
import it.polimi.ingsw.view.events.StorageEvent;
import it.polimi.ingsw.view.scenes.BasicSceneUpdater;
import it.polimi.ingsw.view.utils.FXMLpaths;
import it.polimi.ingsw.viewtest.Appp;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;

public class GuiHelper extends Application {

    private static Scene        scene;
    private static GUI          gui;
    private static GuiHelper    guiHelper;
    private static Stage        stage;

    public GuiHelper()
    {
        guiHelper = this;
    }

    @Override
    public void start(Stage primaryStage) {

        stage = primaryStage;

        primaryStage.setTitle("Lorenzo The Game");

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

    public static void updateRoot()
    {

    }

    public static Stage getStage()
    {
        return stage;
    }
    //TODO CREARE DEI SETROOT PERSONALIZZATI PER I DIVERSI TIPI DI VISTA (esempio passare pezzi di minimodel) usare loader.getController() per passare i parametri tramite dei setter
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    /**
     * Send an event containing miniStorage to the current Scene (if it has a Listener of this type it will update the view)
     * @param msg a message
     */
    public static void sendMessage(String msg)
    {
        stage.fireEvent(new GenericMessage(GenericMessage.ANY,msg));
    }

    public static void sendError(String msg)
    {
        stage.fireEvent(new GenericMessage(GenericMessage.ERROR,msg));
    }

    public static void storageUpdate(Deposit[] deposits)
    {
        stage.fireEvent(new StorageEvent(StorageEvent.STORAGE,deposits));
    }

    public static void storageUpdate(List<Resource> chest)
    {
        stage.fireEvent(new ChestEvent(ChestEvent.CHEST,chest));
    }

    public static void decksUpdate(ProductionCard[][] cards)
    {
        stage.fireEvent(new DecksEvent(DecksEvent.DECKS,cards));
    }

    private static Parent loadFXML(String fxml) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Appp.class.getResource("/fxml/"+fxml+".fxml"));

        Parent out = loader.load();
        BasicSceneUpdater b = loader.getController();
        b.init();//initialize components (eg hide label... show card..)
        return out;
    }

    public static void main(GUI g) {
        gui = g;
        launch();

    }

}
