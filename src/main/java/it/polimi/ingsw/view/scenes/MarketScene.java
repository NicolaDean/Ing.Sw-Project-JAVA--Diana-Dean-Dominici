package it.polimi.ingsw.view.scenes;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MarketScene extends BasicSceneUpdater{


    private ImageView marketImage;
    @FXML
    Button ree;


    @Override
    public void init() {
        Image image = new Image(BuyScene.class.getResourceAsStream("/images/dashboard/market.png"));
        marketImage=new ImageView(image);
        marketImage.setPreserveRatio(true);


    }

    @Override
    public void updateMarket() {

    }
}
