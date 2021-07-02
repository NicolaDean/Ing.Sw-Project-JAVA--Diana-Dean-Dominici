package it.polimi.ingsw.view.scenes;

import it.polimi.ingsw.view.GuiHelper;
import it.polimi.ingsw.view.utils.FXMLpaths;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;

/**
 * Scene used to request server informartion
 */
public class EndGameScene extends BasicSceneUpdater{

    @FXML
    public AnchorPane paneMain;
    @FXML
    public Text text;
    @FXML
    public Button goHome;

    String [] nick;
    int [] score;
    int VP;
    Boolean isLorenzo,lorenzoWin;

    public EndGameScene(String[] nick,int [] score) {
        this.nick = nick;
        this.score=score;
        isLorenzo=false;
    }

    public EndGameScene(Boolean lorenzoWin,int VP) {
        this.isLorenzo=true;
        this.lorenzoWin=lorenzoWin;
        this.VP=VP;
    }

    @Override
    public void init() {
        super.init();

        goHome.setOnMouseClicked(mouseEvent -> {
            try {
                GuiHelper.setRoot(FXMLpaths.home);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        if(isLorenzo) printWinLostScore();
        else createCharts();


    }

    private void printWinLostScore(){
        Button b=new Button("VP: "+VP);
        b.setId("player");

        b.setMinHeight(60);
        b.setMinWidth(150);

        b.setDisable(true);
        b.setLayoutX(300);
        b.setLayoutY(400);
        b.setOpacity(100);
        text.setText(lorenzoWin?"YOU LOST":"YOU WIN");
        paneMain.getChildren().add(b);
    }

    private void createCharts(){
        Button []b=new Button[nick.length];
        int initialBorder = 300;
        for (int i = 0; i < nick.length; i++) {

            b[i]=new Button(nick[i]+" VP:"+score[i]);
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
