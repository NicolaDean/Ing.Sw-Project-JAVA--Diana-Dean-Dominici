package it.polimi.ingsw.view.scenes;

import it.polimi.ingsw.controller.packets.InsertionInstruction;
import it.polimi.ingsw.enumeration.ResourceType;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.ProductionCard;
import it.polimi.ingsw.model.dashboard.Deposit;
import it.polimi.ingsw.model.minimodel.MiniPlayer;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceList;
import it.polimi.ingsw.model.resources.ResourceOperator;
import it.polimi.ingsw.utils.ConstantValues;
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
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class DashboardScene extends BasicSceneUpdater {

    @FXML
    public AnchorPane root;

    @FXML
    public Text swaptext;
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


    @FXML
    public FlowPane marketInsersion;

    @FXML
    public ImageView bin;

    CheckBox lastchecked;

    CheckBox[] boxes;

    int index = -1;

    boolean showLeaders, imHereAfterMarketExstraction;
    List<Resource> resourceExtracted;
    List<Resource> resourceDiscarted;
    List<Resource> resourceInserted;
    List<InsertionInstruction> resourceInsertedInstraction;
    Deposit[]                   tmpStorage;


    public DashboardScene(List<Resource> resourceList) {
        imHereAfterMarketExstraction =true;
        this.resourceExtracted =resourceList;
        this.resourceInsertedInstraction = new ArrayList<>();
        this.resourceInserted = new ResourceList();
        this.resourceDiscarted = new ResourceList();
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
        bin.setVisible(false);
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
        this.swap1.setVisible(false);
        this.swap2.setVisible(false);
        this.swap3.setVisible(false);
        this.swapbutton.setVisible(false);
        this.swaptext.setVisible(false);
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
                //System.out.println("stampo la risorsa");
                ImageView immage = null;
                immage = loadImage("/images/resources/" + storage[2].getResource().getNumericType() + ".png", 40, 40);
                deposit3.add(immage, i, 0);

            }
        }

        if (storage[0].getResource() != null) {
            removeElementFromGridPane(deposit1);
            //System.out.println("stampo la risorsa");
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
        this.tmpStorage=new Deposit[ConstantValues.normalDepositNumber];
        boolean visible=true;
        toastForMarketInsersion.setVisible(visible);
        marketbutton.setDisable(visible);
        endturn.setDisable(visible);
        shopbutton.setDisable(visible);
        bin.setVisible(visible);

        this.notifyObserver(controller ->{
            for (int i = 0; i < ConstantValues.normalDepositNumber; i++) {
                this.tmpStorage[i]=controller.getMiniModel().getStorage()[i].clone();
            }
        });

        printResourceExtracted();

        deposit1.setOnDragOver(this::onOver);
        deposit1.setOnDragDropped(this::destinationOnDragDropped);
        deposit2.setOnDragOver(this::onOver);
        deposit2.setOnDragDropped(this::destinationOnDragDropped);
        deposit3.setOnDragOver(this::onOver);
        deposit3.setOnDragDropped(this::destinationOnDragDropped);

        bin.setOnDragOver(this::onOver);
        bin.setOnDragDropped(this::binOnDragDropper);

    }

    /**
     * function called when something is dragged and dropped on the bin
     * @param event drag event
     */
    public void binOnDragDropper(DragEvent event)
    {
        String s = event.getDragboard().getString();
        System.out.println(s);
        int i;
        boolean contains=false;
        for (i = 0; i < resourceExtracted.size(); i++) {
            if((resourceExtracted.get(i).getType().toString().toUpperCase(Locale.ROOT).equals(s))&&(resourceExtracted.get(i).getQuantity()>0)) {
                contains = true;
                break;
            }
        }
        if(contains)
        {

            System.out.println("Remove : " + i);
            ResourceType type = ResourceType.valueOf(s);


            this.resourceExtracted.remove(new Resource(type,1));
            this.resourceDiscarted.add(new Resource(type,1));

            for (int j = 0; j < this.marketInsersion.getChildren().size(); j++) {
                if(this.marketInsersion.getChildren()!=null)
                if(this.marketInsersion.getChildren().get(j).getId().equals(resourceExtracted.get(i).getType().toString().toUpperCase(Locale.ROOT)))
                    this.marketInsersion.getChildren().remove(j);
            }

            this.notifyObserver(controller->{controller.sendResourceDiscard(1);});

        }
        else
        {
            System.out.println("not discardable");
        }

        if(this.resourceExtracted.isEmpty()){
            this.notifyObserver(controller -> {
                controller.sendResourceInsertion(resourceInsertedInstraction);
                this.drawStorage(controller.getMiniModel().getStorage());
            });
        }

    }

    /**
     * function called when a drag event is over an object
     * @param event drag evnet
     */
    public void onOver(DragEvent event)
    {
        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        event.consume();
    }

    /**
     * when a resource is dropped from source to destination pane it will be added a new image
     * and resource type corresponding to it will be added to res list
     * @param event drag event
     */
    public void destinationOnDragDropped(DragEvent event)
    {
        int position=(Integer.parseInt( ((GridPane)event.getGestureTarget()).getId().charAt(((GridPane)event.getGestureTarget()).getId().length()-1)+"" ))-1 ; //posizione dello storage
        ResourceType res = ResourceType.valueOf(event.getDragboard().getString()); //risorda droppata


            try{

                tmpStorage[position].safeInsertion(new Resource(res,1));

                //rimuovo da resourceexstracted quello che ho droppato e lo aggiungo a resourceinsered
                this.resourceExtracted.remove( new Resource(res,1) );
                //TODO da modificare il fatto che 2 risorse dello stesso tipo vanno messe insieme
                this.resourceInsertedInstraction.add(new InsertionInstruction(new Resource(res,  ResourceOperator.extractQuantityOf(res,resourceExtracted))  , position ));
                this.resourceInserted.add(new Resource(res,1));

                //rimuovo dal toast quello che ho droppato
                for (int j = 0; j < this.marketInsersion.getChildren().size(); j++) {
                    if(this.marketInsersion.getChildren().get(j)!=null)
                        if(this.marketInsersion.getChildren().get(j).getId().equals( res.toString() )) {
                            this.marketInsersion.getChildren().remove(j);
                            break;
                        }
                }

                this.drawStorage(tmpStorage);

            }catch (Exception e){
                e.printStackTrace();
                System.out.println("non puoi metterla lì");
            }


        //se non c'è piu niente invia il pacchetto
        if(this.resourceExtracted.isEmpty()){
            this.notifyObserver(controller -> {
                System.out.println(resourceInsertedInstraction);
                controller.sendResourceInsertion(resourceInsertedInstraction);
                this.drawStorage(controller.getMiniModel().getStorage());
            });
        }

        event.consume();
    }

    private void printResourceExtracted(){
        for (int i = 0; i < resourceExtracted.size(); i++) {
            if(resourceExtracted.get(i).getQuantity()>0) {
                for(int j = 0; j < resourceExtracted.get(i).getQuantity(); j++) {

                    int name = resourceExtracted.get(i).getType().ordinal() + 1;
                    ImageView img = loadImage("/images/resources/" + name + ".png", 60, 60);
                    img.setId(resourceExtracted.get(i).getType().toString());

                    marketInsersion.getChildren().add(img);




                    //Set image as draggable
                    img.setOnDragDetected(event -> {
                        System.out.println("dragged");

                        /* allow any transfer mode */
                        Dragboard db = img.startDragAndDrop(TransferMode.COPY_OR_MOVE);
                        img.setFitHeight(60);
                        img.setFitWidth(60);


                        /* put a string on dragboard */
                        ClipboardContent content = new ClipboardContent();
                        content.putString(img.getId());
                        content.putImage(img.getImage());
                        db.setContent(content);

                        event.consume();

                    });
                }
            }
        }
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
