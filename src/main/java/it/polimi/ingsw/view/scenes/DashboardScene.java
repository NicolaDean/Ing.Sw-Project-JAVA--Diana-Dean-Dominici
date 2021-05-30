package it.polimi.ingsw.view.scenes;

import it.polimi.ingsw.controller.packets.InsertionInstruction;
import it.polimi.ingsw.enumeration.ResourceType;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.ProductionCard;
import it.polimi.ingsw.model.dashboard.Deposit;
import it.polimi.ingsw.model.minimodel.MiniModel;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceList;
import it.polimi.ingsw.model.resources.ResourceOperator;
import it.polimi.ingsw.utils.DebugMessages;
import it.polimi.ingsw.view.GuiHelper;
import it.polimi.ingsw.view.utils.ToastMessage;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DashboardScene extends BasicSceneUpdater {

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

    @FXML
    public AnchorPane toastForMarketInsersion;

    @FXML
    public FlowPane marketInsersion;

    @FXML
    public ImageView bin;

    boolean showLeaders, imHereAfterMarketExstraction;
    List<Resource> resourceExtracted;
    List<InsertionInstruction> resourceInserted;
    List<Resource> resourceDiscarted;

    public DashboardScene(List<Resource> resourceList) {
        imHereAfterMarketExstraction =true;
        this.resourceExtracted =resourceList;
        this.resourceInserted = new ArrayList<>();
        this.resourceDiscarted = new ResourceList();
    }

    public DashboardScene(){
        imHereAfterMarketExstraction =false;
    }


    @Override
    public void init() {
        super.init();
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

            //System.out.println("la risorsa in d2 vale "+d1.getResource().getQuantity());
            if (d2.getResource() != null) {
                for (int i = 0; i < d2.getResource().getQuantity(); i++) {
                    //System.out.println("stampo la risorsa");
                    ImageView immage = null;
                    immage = loadImage("/images/resources/" + d2.getResource().getNumericType() + ".png", 40, 40);
                    deposit2.add(immage, i, 0);

                }
            }

            if (d3.getResource() != null) {
                for (int i = 0; i < d3.getResource().getQuantity(); i++) {
                    System.out.println("stampo la risorsa");
                    ImageView immage = null;
                    immage = loadImage("/images/resources/" + d3.getResource().getNumericType() + ".png", 40, 40);
                    deposit3.add(immage, i, 0);

                }
            }

            if (d1.getResource() != null) {

                System.out.println("stampo la risorsa");
                ImageView immage = null;
                immage = loadImage("/images/resources/" + d1.getResource().getNumericType() + ".png", 40, 40);
                deposit1.add(immage, 0, 0);

            }

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

        boolean visible=true;
        toastForMarketInsersion.setVisible(visible);
        marketbutton.setDisable(visible);
        endturn.setDisable(visible);
        shopbutton.setDisable(visible);
        bin.setVisible(visible);

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
            this.notifyObserver(controller -> {controller.sendResourceInsertion(resourceInserted);});
        }

    }

    /**
     * function called when a drag event is over an object
     * @param event drag evnet
     */
    public void onOver(DragEvent event)
    {
        System.out.println(event.getDragboard().getString());
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
        int position=Integer.parseInt( ((GridPane)event.getGestureTarget()).getId().charAt(((GridPane)event.getGestureTarget()).getId().length()-1)+"" ) ;
        String s = event.getDragboard().getString();
        /*
        this.notifyObserver(controller -> {
            if(controller.getMiniModel().getPersonalPlayer().getStorage()[position].getResource().toString().toUpperCase(Locale.ROOT).equals(s))

        });

         */


        int i;
        for (i = 0; i < resourceExtracted.size(); i++) {
            if((resourceExtracted.get(i).getType().toString().toUpperCase(Locale.ROOT).equals(s))&&(resourceExtracted.get(i).getQuantity()>0)) {
                break;
            }
        }

        if(event.getDragboard().getString().contains("-d-"))
        {
            System.out.println("Now allowed drag ( dest -> dest");
            return;
        }

        //Load a resource corresponding to image
        ResourceType res = ResourceType.valueOf(event.getDragboard().getString());

        System.out.println("Messo nello storage numero: "+((GridPane)event.getGestureTarget()).getId().charAt(((GridPane)event.getGestureTarget()).getId().length()-1));


        //TODO da modificare il fatto che 2 risorse dello stesso tipo vanno messe insieme
        if(ResourceOperator.extractQuantityOf(res,resourceExtracted)>1){
            resourceExtracted.remove(new Resource(res,ResourceOperator.extractQuantityOf(res,resourceExtracted)));
            this.resourceInserted.add(new InsertionInstruction(new Resource(res,  ResourceOperator.extractQuantityOf(res,resourceExtracted))  , Integer.parseInt( ((GridPane)event.getGestureTarget()).getId().charAt(((GridPane)event.getGestureTarget()).getId().length()-1)+"" ) ));
        }else {
            if(ResourceOperator.extractQuantityOf(res,resourceExtracted)==1)
                this.resourceExtracted.remove(new Resource(res, 1));
        }


        for (int j = 0; j < this.marketInsersion.getChildren().size(); j++) {
            if(this.marketInsersion.getChildren().get(j).getId().equals( resourceExtracted.get(i).getType().toString().toUpperCase(Locale.ROOT) )) {
                this.marketInsersion.getChildren().remove(j);
            }
        }


        System.out.println("dropped " + event.getDragboard().getString());
        ImageView img = new ImageView(event.getDragboard().getImage());
        img.setId(res.toString());
        img.setFitHeight(60);
        img.setFitWidth(60);


        img.setOnDragDetected(eventBin -> {
            /* allow any transfer mode */
            Dragboard db = img.startDragAndDrop(TransferMode.ANY);

            /* put a string on dragboard */
            ClipboardContent content = new ClipboardContent();
            content.putString(img.getId());
            content.putImage(img.getImage());
            db.setContent(content);

            eventBin.consume();
        });

        if(this.resourceExtracted.isEmpty()){
            this.notifyObserver(controller -> {controller.sendResourceInsertion(resourceInserted);});
        }

        ((GridPane)event.getGestureTarget()).getChildren().add(img);
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

        Platform.runLater(()->{
            this.drawLeaders(leaders);
        });
    }
}
