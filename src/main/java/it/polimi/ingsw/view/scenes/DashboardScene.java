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
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

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

    @FXML
    public FlowPane nicknames;


    @FXML
    public FlowPane marketInsersion;

    @FXML
    public ImageView bin;

    CheckBox lastchecked;

    CheckBox[] boxes;

    int index;

    boolean showLeaders, imHereAfterMarketExstraction;
    List<Resource> resourceExtracted;
    List<Resource> resourceDiscarted;
    List<Resource> resourceInserted;
    List<InsertionInstruction> resourceInsertedInstraction;
    Deposit[]                   oldStorage;


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

        //DRAW ALL DASHBOARD COMPONENTS
        this.notifyObserver(controller -> {
            DebugMessages.printError("Dashboard Scene initialized");

            MiniPlayer p = controller.getMiniModel().getPersonalPlayer();

            drawStorage     (p.getStorage());
            drawChest       (p.getChest());
            drawLeaders     (p.getLeaderCards());
            drawProductions (p.getDecks());
            drawPosition    (p.getPosition());
            drawNicknames();
            //DRAW FAITH TOKEN POSITION
            this.index =  controller.getMiniModel().getPersanalIndex();
        });
    }

    public void drawNicknames()
    {

        this.notifyObserver(controller -> {

            int i=0;
            for(MiniPlayer player:controller.getMiniModel().getPlayers())
            {
                System.out.println(player.getNickname());
                Pane p = new Pane();
                Label l = new Label(player.getNickname());
                l.setId("fancytext");
                p.getChildren().add(l);

                p.setOnMouseClicked(event -> {
                    Platform.runLater(()->{
                        try {
                            GuiHelper.setRoot(FXMLpaths.dashboard,new SpyScene(i,player.getNickname()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                });
                this.nicknames.getChildren().add(p);
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
        this.faith.get(pos).getChildren().add(loadImage("/images/resources/tokenPosition.png", 50, 50));
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
        for(int i=0;i<pane.getChildren().size();i++)
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
        this.oldStorage=new Deposit[ConstantValues.normalDepositNumber];
        boolean visible=true;
        toastForMarketInsersion.setVisible(visible);
        marketbutton.setDisable(visible);
        endturn.setDisable(visible);
        shopbutton.setDisable(visible);
        bin.setVisible(visible);

        this.notifyObserver(clientController -> {
                this.oldStorage = clientController.getMiniModel().getStorage().clone();
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
            //ripristina vecchio storage
            this.notifyObserver(controller -> {
                try {
                    controller.getMiniModel().getPersonalPlayer().setStorage(this.oldStorage);
                } catch (Exception e) { e.printStackTrace(); }
            });

            this.notifyObserver(controller -> {controller.sendResourceInsertion(resourceInsertedInstraction);});
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
        String s = event.getDragboard().getString(); //risorsa che ci ha droppato
        ResourceType res = ResourceType.valueOf(event.getDragboard().getString()); //risorda droppata

        this.notifyObserver(controller -> {
            try{
                controller.getMiniModel().getPersonalPlayer().getStorage()[position].safeInsertion(new Resource(res,ResourceOperator.extractQuantityOf(res,resourceExtracted)));

                //TODO da modificare il fatto che 2 risorse dello stesso tipo vanno messe insieme
                //rimuovo da resourceexstracted quello che ho droppato e lo aggiungo a resourceinsered

                this.resourceExtracted.remove( new Resource(res,ResourceOperator.extractQuantityOf(res,resourceExtracted)) );
                this.resourceInsertedInstraction.add(new InsertionInstruction(new Resource(res,  ResourceOperator.extractQuantityOf(res,resourceExtracted))  , position ));
                this.resourceInserted.add(new Resource(res,ResourceOperator.extractQuantityOf(res,resourceExtracted)));

                //rimiovo dal toast quello che ho droppato
                for (int j = 0; j < this.marketInsersion.getChildren().size(); j++) {
                    if(this.marketInsersion.getChildren().get(j)!=null)
                        if(this.marketInsersion.getChildren().get(j).getId().equals( res.toString() )) {
                            this.marketInsersion.getChildren().remove(j);
                        }
                }

                //TODO da cambaire con drawStorage
                updateStorage(controller.getMiniModel().getPersanalIndex(),controller.getMiniModel().getStorage());

            }catch (Exception e){
                e.printStackTrace();
                System.out.println("non puoi metterla lì");
            }
        });

        //se non c'è piu niente invia il pacchetto
        if(this.resourceExtracted.isEmpty()){
                //ripristina vecchio storage
                this.notifyObserver(controller -> {
                    try {
                        controller.getMiniModel().getPersonalPlayer().setStorage(this.oldStorage);
                    } catch (Exception e) { e.printStackTrace(); }
                });

            this.notifyObserver(controller -> {controller.sendResourceInsertion(resourceInsertedInstraction);});
        }

        event.consume();
    }

    private void printResourceExtracted(){
        for (int i = 0; i < resourceExtracted.size(); i++) {
            if(resourceExtracted.get(i).getQuantity()>0) {
                //for(int j = 0; j < resourceExtracted.get(i).getQuantity(); j++) {

                    int name = resourceExtracted.get(i).getType().ordinal() + 1;
                    ImageView img = loadImage("/images/resources/" + name + ".png", 60, 60);
                    img.setId(resourceExtracted.get(i).getType().toString());

                    marketInsersion.getChildren().add(img);

                    //if temporaneo
                    Text x2=new Text("x"+resourceExtracted.get(i).getQuantity());
                    if(resourceExtracted.get(i).getQuantity()>1){
                        x2.setFill(Color.WHITE);
                        marketInsersion.getChildren().add(x2);
                    }
                    //-----------------------

                    //Set image as draggable
                    img.setOnDragDetected(event -> {
                        System.out.println("dragged");

                        /* allow any transfer mode */
                        Dragboard db = img.startDragAndDrop(TransferMode.ANY);

                        x2.setVisible(false);  //da togliere

                        /* put a string on dragboard */
                        ClipboardContent content = new ClipboardContent();
                        content.putString(img.getId());
                        content.putImage(img.getImage());
                        db.setContent(content);

                        event.consume();

                    });
               // }
            }
        }
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
