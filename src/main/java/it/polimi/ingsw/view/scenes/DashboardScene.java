package it.polimi.ingsw.view.scenes;

import it.polimi.ingsw.utils.ConstantValues;
import it.polimi.ingsw.utils.DebugMessages;
import it.polimi.ingsw.view.GuiHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;

public class DashboardScene extends BasicSceneUpdater{

    @FXML
    public StackPane card0stack;
    @FXML
    public StackPane card1stack;
    @FXML
    public StackPane card2stack;
    @FXML
    public AnchorPane root;
    @FXML
    public Label click;
    @FXML
    public ImageView card0;
    @FXML
    public ImageView card1;
    @FXML
    public ImageView card2;

    @Override
    public void init()
    {
        super.init();

        //GuiHelper.resize(1280,720);


        GuiHelper.getStage().show();
        //DRAW DECK
        this.notifyObserver(controller ->{
            DebugMessages.printError("Dashboard Scene initialized");

            card0=loadImage("/images/cards/productions/1.jpg",130,200);
            card1=loadImage("/images/cards/productions/2.jpg",130,200);
            card2=loadImage("/images/cards/productions/3.jpg",130,200);

        });
    }




}
