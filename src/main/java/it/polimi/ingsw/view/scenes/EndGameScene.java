package it.polimi.ingsw.view.scenes;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;


public class EndGameScene extends BasicSceneUpdater{
    @FXML
    public AnchorPane paneMain;

    String [] nick;

    public EndGameScene(String[] nick) {
        this.nick = nick;
    }

    @Override
    public void init() {
        super.init();

        createCharts();
    }

    private void createCharts(){
        Button []b=new Button[nick.length];
        int initialBorder = 300;
        for (int i = 0; i < nick.length; i++) {

            b[i]=new Button((i+1)+" "+nick[i]);
            b[i].setId("player");

            b[i].setMinHeight(60);
            b[i].setMinWidth(150);

            b[i].setDisable(true);
            b[i].setLayoutX(300);
            b[i].setLayoutY(((initialBorder -(paneMain.getLayoutY())+paneMain.getMaxHeight())/nick.length)*i+(initialBorder/2));
            b[i].setOpacity(100);


            paneMain.getChildren().add(b[i]);
        }
    }
}
