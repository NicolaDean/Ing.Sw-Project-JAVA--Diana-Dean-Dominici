package it.polimi.ingsw.view.scenes;

import it.polimi.ingsw.utils.ConstantValues;
import it.polimi.ingsw.view.GuiHelper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class MarketScene extends BasicSceneUpdater{


    @FXML
    AnchorPane pane;

    @Override
    public void init() {
        super.init();

        GuiHelper.getStage().setWidth(ConstantValues.guiWidth);
        GuiHelper.getStage().setHeight(ConstantValues.guiHeight);
        //GuiHelper.resize(ConstantValues.guiWidth,ConstantValues.guiHeight);
        GuiHelper.getStage().show();


        //fillMiniMarket();
    }

    @Override
    public void updateMarket() {

    }

    public void exstractionCol(int pos){
        //da cambiare pos in maniera speculare
        System.out.println("estratta posizione "+pos);
        //this.notifyObserver(ClientController::exstractCol(pos));
        //updateMarket();
    }
}
