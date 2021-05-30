package it.polimi.ingsw.view.scenes;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.ProductionCard;
import it.polimi.ingsw.model.dashboard.Deposit;
import it.polimi.ingsw.model.minimodel.MiniModel;
import it.polimi.ingsw.utils.ConstantValues;
import it.polimi.ingsw.utils.DebugMessages;
import it.polimi.ingsw.view.GuiHelper;
import it.polimi.ingsw.view.utils.FXMLpaths;
import it.polimi.ingsw.view.utils.ToastMessage;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.Shadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.List;
import java.util.concurrent.TimeUnit;

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
    public GridPane deposit3;

    @FXML
    public GridPane deposit2;

    @FXML
    public GridPane deposit1;

    @FXML
    public GridPane chestgrid;

    @FXML
    public ImageView marketbutton;

    @FXML
    public ImageView shopbutton;

    @FXML
    public ImageView endturn;

    @FXML
    public List<Pane> faith;

    @FXML
    public FlowPane leaderCards;
    @FXML
    public Button showButton;
    boolean showLeaders;

    @Override
    public void init()
    {
        super.init();
        showLeaders = false;
        leaderCards.setVisible(false);

        this.notifyObserver(controller -> {
            LeaderCard[] cards = controller.getMiniModel().getPersonalPlayer().getLeaderCards();

            Platform.runLater(()->{
                this.drawLeaders(cards);
            });
        });
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

        //DRAW FAITH TOKEN POSITION
        this.notifyObserver(controller -> {
            int pos = controller.getMiniModel().getPersonalPlayer().getPosition();

            for(int i=0;i<25;i++)this.faith.get(i).getChildren().add(loadImage("/images/resources/tokenPosition.png",50,50));
            this.faith.get(pos).getChildren().add(loadImage("/images/resources/tokenPosition.png",50,50));
        });

        //DRAW STORAGE
        this.notifyObserver(controller ->{
            DebugMessages.printError("Dashboard Scene initialized");

            ProductionCard[] carte = controller.getMiniModel().getPersonalPlayer().getDecks();
            chestcoinq.setText(Integer.toString(controller.getMiniModel().getPersonalPlayer().getChest().get(2).getQuantity())) ;
            chestrockq.setText(Integer.toString(controller.getMiniModel().getPersonalPlayer().getChest().get(1).getQuantity())) ;
            chestservantq.setText(Integer.toString(controller.getMiniModel().getPersonalPlayer().getChest().get(3).getQuantity())) ;
            chestshieldq.setText(Integer.toString(controller.getMiniModel().getPersonalPlayer().getChest().get(0).getQuantity())) ;
            MiniModel model = controller.getMiniModel();
            Deposit d1 = controller.getMiniModel().getStorage()[0];
            Deposit d2 = controller.getMiniModel().getStorage()[1];
            Deposit d3 = controller.getMiniModel().getStorage()[2];

            //System.out.println("la risorsa in d2 vale "+d1.getResource().getQuantity());
            if(d2.getResource()!=null) {
                for (int i = 0; i < d2.getResource().getQuantity(); i++) {
                    //System.out.println("stampo la risorsa");
                    ImageView immage = null;
                    immage = loadImage("/images/resources/"+d2.getResource().getNumericType()+".png",40,40);
                    deposit2.add(immage,i,0);

                }
            }

            if(d3.getResource()!=null) {
                for (int i = 0; i < d3.getResource().getQuantity(); i++) {
                    System.out.println("stampo la risorsa");
                    ImageView immage = null;
                    immage = loadImage("/images/resources/"+d3.getResource().getNumericType()+".png",40,40);
                    deposit3.add(immage,i,0);

                }
            }

            if(d1.getResource()!=null) {

                    System.out.println("stampo la risorsa");
                    ImageView immage = null;
                    immage = loadImage("/images/resources/"+d1.getResource().getNumericType()+".png",40,40);
                    deposit1.add(immage,0,0);

            }

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
            int finalJ = j;
            immage.setOnMouseClicked(event -> {
                System.out.println("bella ziii");

                boolean res = GuiHelper.YesNoDialog("Production Card Activation","Do you want to produce with this card?");

                if(res)
                {
                    this.notifyObserver(controller -> controller.sendProduction(finalJ -1));
                }
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




    public void drawLeaders(LeaderCard[] cards)
    {

        for(int i=0;i<this.leaderCards.getChildren().size();i++)
        {
            this.leaderCards.getChildren().remove(0);
        }

        int i=0;
        for(LeaderCard c : cards)
        {
            ImageView card = loadImage("/images/cards/leaders/"+c.getId()+".jpg",130,200);
            int finalI = i;
            card.setOnMouseClicked(event -> {
                boolean out = GuiHelper.YesNoDialog("Leader actviation","Do you want to activate this leader");

                if(out) this.notifyObserver(controller -> controller.activateLeader(finalI));
            });
            i++;
            leaderCards.getChildren().add(card);
        }
    }
    @Override
    public void reciveMessage(String msg) {
        super.reciveMessage(msg);
        ToastMessage t = new ToastMessage(msg,this.root,2000);
        t.show();

    }

    public void showLeader(ActionEvent actionEvent) {
        if(!showLeaders)
        {
            showLeaders = true;
            showButton.setText("Hide leaders");
            leaderCards.setVisible(true);
        }
        else
        {
            showLeaders = false;
            showButton.setText("Show leaders");
            leaderCards.setVisible(false);
        }

    }

    @Override
    public void updateLeaders(int player, LeaderCard[] leaders) {

        Platform.runLater(()->{
            this.drawLeaders(leaders);
        });
    }
}
