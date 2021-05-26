package it.polimi.ingsw.view.events;


import it.polimi.ingsw.view.scenes.BasicSceneUpdater;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;


public class DashboardScene extends BasicSceneUpdater {


    @FXML
    GridPane productionContainer;

    ImageView[] productions = new ImageView[3];


    @Override
    public void init() {
        super.init();

        this.notifyObserver(controller -> {
            controller.getMiniModel().getPersonalPlayer().getChest();
        });

        productions[0] = new ImageView(loadImage("/images/cards/productions/"+1+".jpg"));
        productions[1] = new ImageView(loadImage("/images/cards/productions/"+2+".jpg"));
        productions[2] = new ImageView(loadImage("/images/cards/productions/"+3+".jpg"));

        productions[0].setFitHeight(200);
        productions[0].setFitWidth(130);

        productions[2].setFitHeight(200);
        productions[2].setFitWidth(130);

        productions[1].setFitHeight(200);
        productions[1].setFitWidth(130);

        productionContainer.add(productions[0],1,0);
        productionContainer.add(productions[1],1,0);
        productionContainer.add(productions[2],1,0);
    }



}
