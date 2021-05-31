package it.polimi.ingsw.view.scenes;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.ProductionCard;
import it.polimi.ingsw.model.dashboard.Deposit;
import it.polimi.ingsw.model.minimodel.MiniModel;
import it.polimi.ingsw.model.minimodel.MiniPlayer;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.utils.DebugMessages;
import it.polimi.ingsw.view.GuiHelper;
import it.polimi.ingsw.view.utils.FXMLpaths;
import it.polimi.ingsw.view.utils.Logger;
import it.polimi.ingsw.view.utils.ToastMessage;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class DashboardScene extends BasicSceneUpdater {

    @FXML
    public AnchorPane root;
    //SWAP
    @FXML
    public CheckBox swap1;
    @FXML
    public CheckBox swap2;
    @FXML
    public CheckBox swap3;
    @FXML
    public GridPane grid;
    //CHEST
    @FXML
    public Text chestshieldq;
    @FXML
    public Text chestcoinq;
    @FXML
    public Text chestrockq;
    @FXML
    public Text chestservantq;

    //STORAGE
    @FXML
    public GridPane deposit3;
    @FXML
    public GridPane deposit2;
    @FXML
    public GridPane deposit1;
    @FXML
    public GridPane chestgrid;

    //BUTTONS
    @FXML
    public ImageView marketbutton;
    @FXML
    public ImageView shopbutton;
    @FXML
    public ImageView swapbutton;
    @FXML
    public ImageView endturn;
    @FXML
    public Button showButton;
    @FXML
    public Button you;
    //FAITH TRACK
    @FXML
    public List<Pane> faith;

    //LEADER CARD
    @FXML
    public FlowPane leaderCards;
    @FXML
    public Label nickname;

    @FXML
    public AnchorPane toastForMarketInsersion;

    @FXML
    public GridPane nicknames;

    CheckBox lastchecked;

    CheckBox[] boxes;

    boolean showLeaders, imHereAfterMarketExstraction;

    public DashboardScene(List<Resource> resourceList) {
        imHereAfterMarketExstraction =true;
    }

    public DashboardScene(){
        imHereAfterMarketExstraction =false;
    }


    int index = -1;
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

        marketbutton.setOnMouseClicked(event -> {
            this.notifyObserver(controller -> controller.showmarket());
        });

        shopbutton.setOnMouseClicked(event -> {
            this.notifyObserver(controller -> controller.showshop());
        });

        you.setOnMouseClicked(event -> {
            Platform.runLater(()->{
                try {
                    GuiHelper.setRoot(FXMLpaths.dashboard,new DashboardScene());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
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

        showButton.setOnMouseClicked(this::showLeader);
        endturn.setOnMouseClicked(event -> {
            this.notifyObserver(controller -> controller.askEndTurn());
        });

        //DRAW ALL DASHBOARD COMPONENTS
        this.notifyObserver(controller -> {
            DebugMessages.printError("Dashboard Scene initialized");
            if(this.index == -1)
            {
                this.setIndex(controller.getMiniModel().getPersanalIndex());
            }

            MiniPlayer p = controller.getMiniModel().getPlayers()[index];

            drawStorage     (p.getStorage());
            drawChest       (p.getChest());
            drawLeaders     (p.getLeaderCards());
            drawProductions (p.getDecks());
            drawPosition    (p.getPosition());
            drawNicknames();
            //DRAW FAITH TOKEN POSITION

        });


    }

    /**
     * disable swap button (used by SpyScene)
     */
    public void disableSwap()
    {
        this.swapbutton.setDisable(true);
    }

    /**
     * Draw Nicknames
     */
    public void drawNicknames()
    {

        this.notifyObserver(controller -> {

            this.nickname .setText(controller.getMiniModel().getPlayers()[this.index].getNickname());

            int i=0;
            this.nicknames.setAlignment(Pos.CENTER);
            for(MiniPlayer player:controller.getMiniModel().getPlayers())
            {
                System.out.println(player.getNickname());
                Pane p = new Pane();

                p.prefWidthProperty().bind(this.nicknames.widthProperty());
                p.setPrefHeight(40);
                p.setId("nick");
                Label l = new Label(player.getNickname());
                l.setTextAlignment(TextAlignment.CENTER);
                l.setId("font");
                l.setAlignment(Pos.CENTER);
                p.getChildren().add(l);


                int finalI = i;
                p.setOnMouseClicked(event -> {
                    Platform.runLater(()->{
                        try {
                            if(finalI != controller.getMiniModel().getPersanalIndex())
                            {
                                //If click on others nickname
                                GuiHelper.setRoot(FXMLpaths.dashboard,new SpyScene(finalI,player.getNickname()));
                            }
                            else
                            {
                                //If click on his nickname
                                //GuiHelper.setRoot(FXMLpaths.dashboard,new DashboardScene());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                });
                this.nicknames.add(p,0,i);
                i++;
            }

        });
    }
    public int getIndex()
    {
        return this.index;
    }
    public void setIndex(int index)
    {
        this.index = index;
    }
    /**
     * draw user position
     * @param pos position of user in faith track
     */
    public void drawPosition(int pos)
    {
        Platform.runLater(()->{
            this.faith.get(pos).getChildren().add(loadImage("/images/resources/tokenPosition.png", 50, 50));
        });
    }

    @Override
    public void updatePlayerPosition(int player, int newPos) {
        if(player != this.index) return;

        System.out.println("Position update");
        this.drawPosition(newPos);
    }

    /**
     * draw chest of client player
     * @param chest chest of this client
     */
    public void drawChest(List<Resource> chest)
    {
        chestshieldq  .setText(Integer.toString(chest.get(0).getQuantity()));
        chestrockq    .setText(Integer.toString(chest.get(1).getQuantity()));
        chestcoinq    .setText(Integer.toString(chest.get(2).getQuantity()));
        chestservantq .setText(Integer.toString(chest.get(3).getQuantity()));


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
    public void updateChest(int player, List<Resource> chest) {
        if(player != this.index) return;

        System.out.println("Chest update");
        this.drawChest(chest);
    }

    @Override
    public void updateStorage(int player, Deposit[] storage)
    {
        if(player != this.index) return;

        System.out.println("Storage update");
        //Check if update is of client player or other players
        this.notifyObserver(controller -> {
            if(player== controller.getMiniModel().getPersanalIndex())  Platform.runLater(()-> this.drawStorage(storage));
        });

    }


    public void removeElementFromGridPane(GridPane pane)
    {
        int s = pane.getChildren().size();
        for(int i=0;i<s;i++)
        {
            pane.getChildren().remove(0);
        }
    }
    /**
     * Draw storage of player
     * @param storage  storage to print
     */
    public void drawStorage (Deposit[] storage)
    {

        (new Logger()).printStorage(storage,null,false);

        //System.out.println("la risorsa in d2 vale "+d1.getResource().getQuantity());
        if (storage[1].getResource() != null) {
            removeElementFromGridPane(deposit2);
            for (int i = 0; i < storage[1].getResource().getQuantity(); i++) {
                //System.out.println("stampo la risorsa");
                ImageView immage = null;
                immage = loadImage("/images/resources/" + storage[1].getResource().getNumericType() + ".png", 40, 40);
                deposit2.add(immage, i, 0);

            }
        }

        if (storage[2].getResource() != null) {
            removeElementFromGridPane(deposit3);
            for (int i = 0; i < storage[2].getResource().getQuantity(); i++) {
                System.out.println("stampo la risorsa");
                ImageView immage = null;
                immage = loadImage("/images/resources/" + storage[2].getResource().getNumericType() + ".png", 40, 40);
                deposit3.add(immage, i, 0);

            }
        }

        if (storage[0].getResource() != null) {
            removeElementFromGridPane(deposit1);
            System.out.println("stampo la risorsa");
            ImageView immage = null;
            immage = loadImage("/images/resources/" + storage[0].getResource().getNumericType() + ".png", 40, 40);
            deposit1.add(immage, 0, 0);

        }

    }

    /**
     * draw production decks of this user
     */
    public void drawProductions(ProductionCard[] cards)
    {
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

        int s = this.leaderCards.getChildren().size();
        for(int i=0;i<s;i++)
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
     * @param mouseEvent
     */
    public void showLeader(MouseEvent mouseEvent) {
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

        if(player != this.index) return;
        System.out.println("Leader update");
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
