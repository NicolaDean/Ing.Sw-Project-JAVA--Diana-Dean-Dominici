package it.polimi.ingsw.view.scenes;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.ProductionCard;
import it.polimi.ingsw.model.dashboard.Deposit;
import it.polimi.ingsw.model.minimodel.MiniModel;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.utils.DebugMessages;
import it.polimi.ingsw.view.GuiHelper;
import it.polimi.ingsw.view.utils.ToastMessage;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class DashboardScene extends BasicSceneUpdater {

    @FXML
    public AnchorPane root;

    @FXML
    public CheckBox swap1;

    @FXML
    public CheckBox swap2;

    @FXML
    public CheckBox swap3;

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
    public ImageView swapbutton;

    @FXML
    public ImageView endturn;

    @FXML
    public List<Pane> faith;

    @FXML
    public FlowPane leaderCards;

    @FXML
    public Button showButton;

    @FXML
    public AnchorPane toastForMarketInsersion;

    CheckBox lastchecked;

    CheckBox[] boxes;

    boolean showLeaders, imHereAfterMarketExstraction;

    public DashboardScene(List<Resource> resourceList) {
        imHereAfterMarketExstraction =true;
    }

    public DashboardScene(){
        imHereAfterMarketExstraction =false;
    }


    @Override
    public void init() {

        super.init();
        boxes = new CheckBox[3];
        boxes[0]=swap1;
        boxes[1]=swap2;
        boxes[2]=swap3;

        showLeaders = false;
        leaderCards.setVisible(false);
        marketbutton.setId("production_card");



        toastForMarketInsersion.setVisible(false);
        doThisJustIfIsHereFromMarketExtraction(imHereAfterMarketExstraction);
        this.notifyObserver(controller -> {
            LeaderCard[] cards = controller.getMiniModel().getPersonalPlayer().getLeaderCards();

            Platform.runLater(() -> {
                this.drawLeaders(cards);
            });
        });

        marketbutton.setOnMouseClicked(event -> {
            this.notifyObserver(controller -> controller.showmarket());
        });

        shopbutton.setOnMouseClicked(event -> {
            this.notifyObserver(controller -> controller.showshop());
        });

        swapbutton.setOnMouseClicked(event -> {
            int count = 0;
            List<Integer> d = new ArrayList<Integer>();
            for(int i=0; i<boxes.length;i++) {
                if (boxes[i].isSelected() == true) {
                    d.add(i);
                    count++;
                }
            }

            if(count==2)
                this.notifyObserver(controller -> controller.askSwap(d.get(0)+1,d.get(1)+1));

            for(int i=0; i<boxes.length;i++) {
            boxes[i].setSelected(false);
            }

        });

        endturn.setOnMouseClicked(event -> {
            this.notifyObserver(controller -> controller.askEndTurn());
        });

        //DRAW FAITH TOKEN POSITION
        this.notifyObserver(controller -> {
            int pos = controller.getMiniModel().getPersonalPlayer().getPosition();
            this.faith.get(pos).getChildren().add(loadImage("/images/resources/tokenPosition.png", 50, 50));
        });

        //DRAW STORAGE
        this.notifyObserver(controller -> {
            DebugMessages.printError("Dashboard Scene initialized");

            ProductionCard[] carte = controller.getMiniModel().getPersonalPlayer().getDecks();
            chestcoinq.setText(Integer.toString(controller.getMiniModel().getPersonalPlayer().getChest().get(2).getQuantity()));
            chestrockq.setText(Integer.toString(controller.getMiniModel().getPersonalPlayer().getChest().get(1).getQuantity()));
            chestservantq.setText(Integer.toString(controller.getMiniModel().getPersonalPlayer().getChest().get(3).getQuantity()));
            chestshieldq.setText(Integer.toString(controller.getMiniModel().getPersonalPlayer().getChest().get(0).getQuantity()));
            MiniModel model = controller.getMiniModel();
            Deposit d1 = controller.getMiniModel().getStorage()[0];
            Deposit d2 = controller.getMiniModel().getStorage()[1];
            Deposit d3 = controller.getMiniModel().getStorage()[2];
            Deposit[] ddd = new Deposit[3];
            ddd[0]=d1;
            ddd[1]=d2;
            ddd[2]=d3;



            updateStorage(controller.getIndex(), ddd);




        });



        drawProductions();
        int x = 0;
        int y = 0;

        for (int i = 0; i < 4; i++) {
            int k = i + 1;
            ImageView immage = loadImage("/images/resources/" + k + ".png", 34, 34);
            chestgrid.add(immage, x, y);

            if (x == 1)
                x = 0;
            else
                x++;

            if (i == 1)
                y = 1;
        }


        chestshieldq.setId("fancytext");
        chestcoinq.setId("fancytext");
        chestrockq.setId("fancytext");
        chestservantq.setId("fancytext");
    }


    @Override
    public void updateStorage(int player, Deposit[] storage)
    {
        Platform.runLater(()-> this.drawStorage(player,storage));
    }

    public void drawStorage (int player, Deposit[] storage)
    {
       /* try{
        for(int i=0;i<2;i++)
            deposit2.getChildren().remove(deposit2.getChildren().get(i));
        for(int i=0;i<3;i++)
            deposit3.getChildren().remove(deposit3.getChildren().get(i));
        deposit1.getChildren().remove(deposit1.getChildren().get(0));}
        catch (Exception e)
        {

        }

        */


        //System.out.println("la risorsa in d2 vale "+d1.getResource().getQuantity());
        if (storage[1].getResource() != null) {
            for (int i = 0; i < storage[2].getResource().getQuantity(); i++) {
                //System.out.println("stampo la risorsa");
                ImageView immage = null;
                immage = loadImage("/images/resources/" + storage[1].getResource().getNumericType() + ".png", 40, 40);
                deposit2.add(immage, i, 0);
                //deposit2.getChildren().remove(immage);

            }
        }

        if (storage[2].getResource() != null) {
            for (int i = 0; i < storage[2].getResource().getQuantity(); i++) {
                System.out.println("stampo la risorsa");
                ImageView immage = null;
                immage = loadImage("/images/resources/" + storage[2].getResource().getNumericType() + ".png", 40, 40);
                deposit3.add(immage, i, 0);

            }
        }

        if (storage[0].getResource() != null) {

            System.out.println("stampo la risorsa");
            ImageView immage = null;
            immage = loadImage("/images/resources/" + storage[0].getResource().getNumericType() + ".png", 40, 40);
            deposit1.add(immage, 0, 0);

        }

    }

    /**
     * draw production decks of this user
     */
    public void drawProductions()
    {
        this.notifyObserver(controller -> {
            ProductionCard[] cards = controller.getMiniModel().getPersonalPlayer().getDecks();
            int j = 1;
            for (ProductionCard card : cards) {

                if (card != null) {
                    ImageView immage = null;

                    immage = loadImage("/images/cards/productions/" + card.getId() + ".jpg", 130, 200);
                    immage.setId("production_card");
                    grid.add(immage, j - 1, 0);

                    int finalJ = j;
                    immage.setOnMouseClicked(event -> {
                        System.out.println("bella ziii");

                        boolean res = GuiHelper.YesNoDialog("Production Card Activation", "Do you want to produce with this card?");


                        if (res) {
                            GuiHelper.setBuyType(false);
                            this.notifyObserver(ctrl -> ctrl.sendProduction(finalJ - 1));
                        }
                    });
                }
                j++;

            }
        });

    }

    /**
     * disable buttons only if we are in "resources insertion" mode
     * @param b
     */
    public void doThisJustIfIsHereFromMarketExtraction(Boolean b){
        if(!b) return;
        toastForMarketInsersion.setVisible(true);
        marketbutton.setDisable(true);
        endturn.setDisable(true);
        shopbutton.setDisable(true);
    }
    /**
     * draw leaders of this player
     * @param cards
     */
    public void drawLeaders(LeaderCard[] cards) {

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
                boolean out = GuiHelper.YesNoDialog("Leader actviation","Do you want to activate this leader?");

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

    /**
     * this function allow to hide and show leaders deck owned
     * @param actionEvent
     */
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


    public void select1()
    {
        if(swap1.isSelected() && swap2.isSelected() && swap3.isSelected())
            if(lastchecked != swap2)
                swap2.setSelected(false);
            else
                swap3.setSelected(false);
        lastchecked = swap1;
    }

    public void select2()
    {
        if(swap1.isSelected() && swap2.isSelected() && swap3.isSelected())
            if(lastchecked != swap1)
                swap1.setSelected(false);
            else
                swap3.setSelected(false);
        lastchecked = swap2;
    }

    public void select3()
    {
        if(swap1.isSelected() && swap2.isSelected() && swap3.isSelected())
            if(lastchecked != swap1)
                swap1.setSelected(false);
            else
                swap2.setSelected(false);
        lastchecked = swap3;
    }


}
