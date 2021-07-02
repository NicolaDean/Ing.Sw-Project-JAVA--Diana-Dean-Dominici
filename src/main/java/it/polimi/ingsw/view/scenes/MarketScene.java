package it.polimi.ingsw.view.scenes;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.market.balls.BasicBall;
import it.polimi.ingsw.utils.ConstantValues;
import it.polimi.ingsw.view.CLI;
import it.polimi.ingsw.view.GuiHelper;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.utils.Logger;
import it.polimi.ingsw.view.utils.ToastMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class MarketScene extends BasicSceneUpdater{


    private ImageView[][] balls;
    private ImageView discartedBall;

    private boolean isMyTurn;
    boolean isAlreadySetted=false;

    @FXML
    GridPane gpaneBalls;
    @FXML
    GridPane gpaneDiscardedBall;
    @FXML
    Button back;
    @FXML
    Button row1,row2,row3;
    @FXML
    Button col1,col2,col3,col4;

    @Override
    public void init() {
        super.init();

        GuiHelper.setCurrentPage(ConstantValues.markTurn);

        this.balls = new ImageView[ConstantValues.marketRow][ConstantValues.marketCol];
        this.discartedBall = new ImageView();
        updateMarket();
        isAlreadySetted=true;
        back.setVisible(true);
        setExstracionButtonVisible(true);
    }

    /**
     * update market scene
     */
    @Override
    public void updateMarket() {
        this.notifyObserver(clientController -> { fillMarket(clientController.getView().getMiniMarketBalls(),clientController.getView().getMiniMarketDiscardedResouce()); });
    }

    public void fillMarket(BasicBall[][] balls, BasicBall discarted){
        //c'è qualcosa che non va con la drawBall
        for(int i=0;i<ConstantValues.marketRow;i++)
            for(int j=0;j<ConstantValues.marketCol;j++)
                drawBall(balls[i][j], i, j, gpaneBalls);
            
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

    public void setExstracionButtonVisible(boolean visible){
        row1.setVisible(visible);
        row2.setVisible(visible);
        row3.setVisible(visible);
        col1.setVisible(visible);
        col2.setVisible(visible);
        col3.setVisible(visible);
        col4.setVisible(visible);
    }

    @FXML
    public void exstractionRow(ActionEvent event){
        this.notifyObserver(clientController -> this.isMyTurn=clientController.isMyTurn());
        if(isMyTurn && GuiHelper.getCurrentPage()== -1) {
            GuiHelper.setFixedTurnType();
            int pos = Integer.parseInt((String) ((Node) event.getSource()).getUserData());
            this.notifyObserver(clientController -> {
                clientController.sendMarketExtraction(false, pos);
            });
            setExstracionButtonVisible(false);
            back.setVisible(false);
        }
        else
        {
            ToastMessage t = new ToastMessage("You cant extract from Market",5000);
            t.show();
            this.resetObserverAfterDialog();
        }
    }

    @FXML
    public void exstractionCol(ActionEvent event){
        this.notifyObserver(clientController -> this.isMyTurn=clientController.isMyTurn());
        if(isMyTurn && GuiHelper.getCurrentPage()==-1) {
            GuiHelper.setFixedTurnType();
            int pos = Integer.parseInt((String) ((Node) event.getSource()).getUserData());
            this.notifyObserver(clientController -> {
                clientController.sendMarketExtraction(true, pos);
            });
            setExstracionButtonVisible(false);
            back.setVisible(false);
        }
        else
        {
            ToastMessage t = new ToastMessage("You cant extract from Market",5000);
            t.show();
            this.resetObserverAfterDialog();
        }
    }

    @FXML
    public void comeBack(ActionEvent event){
        this.notifyObserver(ClientController::showDashboard);
    }

}