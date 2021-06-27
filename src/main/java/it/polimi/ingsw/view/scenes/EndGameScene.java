package it.polimi.ingsw.view.scenes;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;


public class EndGameScene extends BasicSceneUpdater{

    @FXML
    public AnchorPane paneMain;
    @FXML
    public Text text;

    String [] nick;
    int [] score;
    Boolean isLorenzo,lorenzoWin;

    public EndGameScene(String[] nick,int [] score) {
        this.nick = nick;
        this.score=score;
        isLorenzo=false;
    }

    public EndGameScene(Boolean lorenzoWin) {
        this.isLorenzo=true;
        this.lorenzoWin=lorenzoWin;
    }

    @Override
    public void init() {
        super.init();
        if(isLorenzo) printWinLost();
        else createCharts();
    }

    private void printWinLost(){
        text.setText(lorenzoWin?"YOU LOST":"YOU WIN");
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
