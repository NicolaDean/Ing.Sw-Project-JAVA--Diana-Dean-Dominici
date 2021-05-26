package it.polimi.ingsw.view.scenes;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.utils.ConstantValues;
import it.polimi.ingsw.view.GuiHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MarketScene extends BasicSceneUpdater{





    @Override
    public void init() {
        super.init();

        //GuiHelper.getStage().setWidth(195.1*3);
        //GuiHelper.getStage().setHeight(252.2*3);
        //deve essere 16:9
        GuiHelper.getStage().setWidth(ConstantValues.resoluctionX);
        GuiHelper.getStage().setHeight(ConstantValues.resoluctionY);
        GuiHelper.getStage().show();
        createButtonRow();
    }

    @Override
    public void updateMarket() {

    }

    public void createButtonRow(){
        for(int i)
    }

    public void exstractionRow(){
        //this.notifyObserver(ClientController::exstractRow());
    }
}
