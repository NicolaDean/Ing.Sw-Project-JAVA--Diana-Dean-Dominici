package it.polimi.ingsw.view.scenes;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.model.market.balls.BasicBall;
import it.polimi.ingsw.utils.ConstantValues;
import it.polimi.ingsw.utils.DebugMessages;
import it.polimi.ingsw.view.GuiHelper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class MarketScene extends BasicSceneUpdater{


    private ImageView[][] balls;
    private ImageView discartedBall;

    boolean isAlreadySetted=false;

    @FXML
    GridPane gpaneBalls;
    @FXML
    GridPane gpaneDiscardedBall;
    @FXML
    Button back;

    @Override
    public void init() {
        super.init();
        this.balls = new ImageView[ConstantValues.marketRow][ConstantValues.marketCol];
        this.discartedBall = new ImageView();
        updateMarket();
        isAlreadySetted=true;
        back.setStyle("-fx-background-image: url('../images/interface/Button.png') no-repeat top left");
    }

    @Override
    public void updateMarket() {
        this.notifyObserver(clientController -> {
            fillMarket(clientController.getView().getMiniMarketBalls(),clientController.getView().getMiniMarketDiscardedResouce());
        });
    }

    public void fillMarket(BasicBall[][] balls, BasicBall discarted){
            for(int i=0;i<ConstantValues.marketRow;i++)
                for(int j=0;j<ConstantValues.marketCol;j++)
                    drawBall(balls[i][j],i,j,gpaneBalls);

            drawBall(discarted,0,0,gpaneDiscardedBall);
    }


    public void drawBall(BasicBall ball,int row,int col,GridPane gpaneBalls){
        if(!isAlreadySetted) {
            this.balls[row][col] = loadImage("/images/balls/" + ball.getColor() + ".png", 50, 50);
            gpaneBalls.add(balls[row][col], ConstantValues.marketCol - col, ConstantValues.marketRow - row);

        }else{
            this.balls[row][col].setImage(loadImage("/images/balls/" + ball.getColor() + ".png"));
        }
    }

    @FXML
    public void exstractionRow(ActionEvent event){
        int pos =Integer.parseInt((String) ((Node)event.getSource()).getUserData());
        this.notifyObserver(clientController -> { clientController.sendMarketExtraction(false,pos); });
    }

    @FXML
    public void exstractionCol(ActionEvent event){
        int pos =Integer.parseInt((String) ((Node)event.getSource()).getUserData());
        this.notifyObserver(clientController -> { clientController.sendMarketExtraction(true,pos); });
    }

    @FXML
    public void comeBack(ActionEvent event){
        this.notifyObserver(ClientController::showDashboard);
    }

}