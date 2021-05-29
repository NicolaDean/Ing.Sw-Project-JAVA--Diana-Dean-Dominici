package it.polimi.ingsw.view.scenes;

import it.polimi.ingsw.model.cards.ProductionCard;
import it.polimi.ingsw.utils.ConstantValues;
import it.polimi.ingsw.utils.DebugMessages;
import it.polimi.ingsw.view.GuiHelper;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.Shadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class DashboardScene extends BasicSceneUpdater{

    @FXML
    public AnchorPane root;

    @FXML
    public GridPane grid;

    @FXML
    public Text chestshieldq;
    @FXML
    public Text chestcoinq;
    @FXML
    public Text chestrockq;
    @FXML
    public Text chestservantq;

    @FXML
    public GridPane chestgrid;

    @FXML
    public ImageView marketbutton;

    @FXML
    public ImageView shopbutton;

    @FXML
    public ImageView endturn;

    @Override
    public void init()
    {
        super.init();

        //GuiHelper.resize(1280,720);

        marketbutton.setOnMouseClicked(event -> {
            this.notifyObserver(controller -> controller.showmarket());
        });

        shopbutton.setOnMouseClicked(event -> {
            this.notifyObserver(controller -> controller.showshop());
        });

        endturn.setOnMouseClicked(event -> {
            this.notifyObserver(controller -> controller.askEndTurn());
        });


        GuiHelper.getStage().show();
        //DRAW DECK
        this.notifyObserver(controller ->{
            DebugMessages.printError("Dashboard Scene initialized");
            ProductionCard[] carte = controller.getMiniModel().getPersonalPlayer().getDecks();
            chestcoinq.setText(Integer.toString(controller.getMiniModel().getPersonalPlayer().getChest().get(2).getQuantity())) ;
            chestrockq.setText(Integer.toString(controller.getMiniModel().getPersonalPlayer().getChest().get(1).getQuantity())) ;
            chestservantq.setText(Integer.toString(controller.getMiniModel().getPersonalPlayer().getChest().get(3).getQuantity())) ;
            chestshieldq.setText(Integer.toString(controller.getMiniModel().getPersonalPlayer().getChest().get(0).getQuantity())) ;
        });

        for (int j=1;j<4;j++) {
            //ImageView immage = loadImage("/images/cards/productions/"+c.getId()+".jpg",130,200);
            ImageView immage = null;

            immage = loadImage("/images/cards/productions/"+j+".jpg",150,250);

            //hadow shadow = new Shadow();

            //immage.setEffect(shadow);

            immage.setId("production_card");
            marketbutton.setId("production_card");

            grid.add(immage,j-1,0);
            immage.setOnMouseClicked(event -> {
                System.out.println("bella ziii");
            });


        }

        int x=0;
        int y=0;

        for(int i=0; i<4; i++)
        {
            int k= i+1;
            ImageView immage = loadImage("/images/resources/"+k+".png",34,34);
            chestgrid.add(immage,x,y );

            if(x==1)
                x=0;
            else
                x++;

            if(i==1)
                y=1;

        }

        chestshieldq.setId("fancytext");
        chestcoinq.setId("fancytext");
        chestrockq.setId("fancytext");
        chestservantq.setId("fancytext");







    }






}
