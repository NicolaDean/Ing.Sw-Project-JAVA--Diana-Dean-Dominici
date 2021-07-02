package it.polimi.ingsw.view;

import it.polimi.ingsw.model.cards.ProductionCard;
import it.polimi.ingsw.model.dashboard.Deposit;
import it.polimi.ingsw.model.resources.Resource;

import it.polimi.ingsw.utils.ConstantValues;
import it.polimi.ingsw.view.scenes.*;
import it.polimi.ingsw.view.utils.FXMLpaths;
import it.polimi.ingsw.view.utils.ToastMessage;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Java fx manager class, change scene and save GUI state
 */
public class GuiHelper extends Application {

    private static Scene                scene;
    private static GUI                  gui;
    private static GuiHelper            guiHelper;
    private static Stage                stage;
    private static BasicSceneUpdater    currentScene;
    private static boolean              buyType = false;

    private static int                  currentPage     = -1;
    private static boolean              fixedTurnType  = false;
    public GuiHelper()
    {
        guiHelper = this;
    }



    /**
     * load the first scene and initialize variable usefull during GuiHelper life (and other scenes life)
     * @param primaryStage stage generated from JavaFx at "launch" function
     */
    @Override
    public void start(Stage primaryStage) {

        stage = primaryStage;
        stage.getIcons().add(BasicSceneUpdater.loadImage("/images/dashboard/calamaio.png"));
        primaryStage.setTitle("Lorenzo The Game");

        try {
            scene = new Scene(loadFXML(FXMLpaths.home));
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setScene(scene);
        primaryStage.setWidth(ConstantValues.guiWidth);
        primaryStage.setHeight(ConstantValues.guiHeight);


        primaryStage.setWidth(1280d);
        primaryStage.setHeight(720d);

        primaryStage.setMaximized(true);
        primaryStage.setFullScreen(false);
        //GuiHelper.resize(800,600);
        primaryStage.show();

        primaryStage.setOnCloseRequest(this::onExit);

    }

    /**
     * called when user close windows (voluntary or for error)
     * @param event windows closing event
     */
    public void onExit(WindowEvent event){
        System.out.println("Stage is closing");
        if(YesNoDialog("EXIT","Are you sure to exit the game? "))
        {
            System.out.println("CLOSED");
            System.exit(0);
            return;
        }
        else
        {
            event.consume();
            System.out.println("REOPEN");
        }
    }

    /**
     * set that the payment that will arive is of buy type or not
     * @param b
     */
    public static void setBuyType(boolean b)
    {
        buyType = b;
    }

    /**
     *
     * @return true if payment is of buy type
     */
    public static boolean getBuyType()
    {
        return buyType;
    }

    /**
     * set true a boolean that indicate that from now the turn type cant be setted
     */
    public static void setFixedTurnType()
    {
        if(currentPage!=-1)
        {
            System.out.println("Page setted to -> " + currentPage);
            fixedTurnType = true;
        }
    }

    /**
     * reset boolean that dosnt allow to do certain action based on current turn type
     */
    public static void resetFixedTurnType()
    {
        currentPage   = -1;
        fixedTurnType = false;
    }

    /**
     *
     * @return the integer corresponding to current turn type
     */
    public static int getCurrentPage()
    {
        if(fixedTurnType)
        {
            return currentPage;
        }
        else
        {
            return -1;
        }

    }

    /**
     * allow to set which turn type is active inside gui
     * @param currPage active turn type
     */
    public static void setCurrentPage(int currPage)
    {
        if(!fixedTurnType)
        {
            System.out.println("change page -> " +  currPage);
            currentPage = currPage;
        }

    }

    /**
     * save the current active scene
     * @param scene scene tos et
     */
    public static void setCurrentScene(BasicSceneUpdater scene)
    {
        currentScene =  scene;
    }


    /**
     * Show a YES or NO dialog generic
     * @param title  title of dialog
     * @param msg  msg to show
     * @return true if clicked yes, false either
     */
    public static boolean YesNoDialog(String title,String msg)
    {
        return loadDialog(FXMLpaths.yesNo,title,new YesNoDialog(msg)).equals(ButtonType.YES);
    }


    /**
     *
     * @return the Gui class
     */
    public static GUI getGui()
    {
        return gui;
    }

    /**
     * get the windows (stage is the current windows JAVAFX created for us at GUI start)
     * @return
     */
    public static Stage getStage()
    {
        return stage;
    }

    /**
     * load new scene
     * @param fxml scene to load
     * @throws IOException file loading failed
     */
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    /**
     * set new scene with custom controller
     * (potentialy unsafe if controller dosnt contain eventual click function not present in the scene)
     * @param fxml     fxml to load
     * @param updater  controller to set instead of default
     * @throws IOException file loading failed
     */
    public static void setRoot(String fxml,BasicSceneUpdater updater) throws IOException {
        scene.setRoot(loadFXML(fxml,updater));
    }

    /**
     * Send an event containing miniStorage to the current Scene (if it has a Listener of this type it will update the view)
     * @param msg a message
     */
    public static void sendMessage(String msg)
    {

        currentScene.reciveMessage(msg);
    }

    /**
     * Update market
     */
    public static void updateMarket(){
        currentScene.updateMarket();
    }

    /**
     * notify scene that en error occure
     * @param msg error message
     */
    public static void sendError(String msg)
    {
        currentScene.reciveError(msg);
    }

    /**
     * Load an fxml by setting sceneUpdater as controller
     * @param fxml          scene to load
     * @param sceneUpdater  controller i want
     * @return              loaded Scene
     * @throws IOException  somting goes wrong in loading
     */
    public static Parent loadFXML(String fxml, BasicSceneUpdater sceneUpdater) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(GuiHelper.class.getResource("/fxml/"+fxml+".fxml"));

        loader.setController(sceneUpdater);
        return getParent(loader);
    }

    /**
     *
     * Load an fxml using default controller specified in fxml
     * @param fxml          scene to load
     * @return              loaded Scene
     * @throws IOException  somting goes wrong in loading
     */
    public static Parent loadFXML(String fxml) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(GuiHelper.class.getResource("/fxml/"+fxml+".fxml"));

        return getParent(loader);
    }

    /**
     *
     * Load an fxml using default controller specified in fxml
     * @param fxml          scene to load
     * @return              loaded Scene
     * @throws IOException  somting goes wrong in loading
     */
    public static Parent loadFXML(String fxml,BasicSceneUpdater sceneUpdater,boolean a) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(GuiHelper.class.getResource("/fxml/"+fxml+".fxml"));

        loader.setController(sceneUpdater);
        //Load fxml
        Parent out = loader.load();
        //Get current scene controller
        BasicSceneUpdater b = loader.getController();
        //initialize components (eg hide label... show card..)
        b.init();
        currentScene = b;

        return out;
    }
    /**
     * Generate and initialize the Scene to be setted by subscribing it to model observer
     * this function allow to load only scene that use "basicSceneUpdater" children as controller
     * since this method call BasicSceneUpdater.init() (a function containing initializzation of components of a scene)
     * @param loader fxml to load
     * @return loaded scene
     * @throws IOException  loading failed (not existing file)
     */
    public static Parent getParent(FXMLLoader loader) throws IOException {
        //Load fxml
        Parent out = loader.load();
        //Get current scene controller
        BasicSceneUpdater b = loader.getController();
        //initialize components (eg hide label... show card..)
        b.init();

        currentScene = b;
        //SET THIS SCENE AS THE CURRENT MINIMODEL OBSERVER
        gui.notifyObserver(controller -> controller.addModelObserver(b));
        return out;
    }

    /**
     * Load a dialof from FXML file
     * @param path        path of file
     * @param title       title of dialog
     * @param controller   controller to use
     * @return button clicked by user(YES,NO,CANCEL...)
     */
    public static ButtonType loadDialog(String path,String title,BasicSceneUpdater controller)
    {

        try {

            DialogPane pane = (DialogPane) GuiHelper.loadFXML(path,controller,false);
            Dialog<ButtonType> dialog  =  new Dialog<>();
            dialog.setDialogPane(pane);
            dialog.setTitle(title);

            Optional<ButtonType> options = dialog.showAndWait();


            return options.orElse(ButtonType.CANCEL);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return ButtonType.CANCEL;
    }

    /**
     * Main which launch Javafx application
     * @param g
     */
    public static void main(GUI g) {
        gui = g;
        launch();
    }

}
