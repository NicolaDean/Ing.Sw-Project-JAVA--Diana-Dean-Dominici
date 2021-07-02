package it.polimi.ingsw.view.scenes;

import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.cards.ProductionCard;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.utils.ConstantValues;
import it.polimi.ingsw.utils.DebugMessages;
import it.polimi.ingsw.view.GuiHelper;
import it.polimi.ingsw.view.utils.FXMLpaths;
import it.polimi.ingsw.view.utils.Logger;
import it.polimi.ingsw.view.utils.ToastMessage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.List;


/**
 * basicly the shop of cards inside game, allow to buy card and position them inside Dashboard
 */
public class BuyScene extends BasicSceneUpdater{


    @FXML
    public FlowPane Row1;
    @FXML
    public FlowPane Row2;
    @FXML
    public FlowPane Row3;
    @FXML
    public AnchorPane root;

    @FXML
    public Label click;
    @FXML
    public ImageView selectedCard;
    @FXML
    public ImageView buyButton;
    @FXML
    public ImageView backButton;


    private ImageView[][] cards;

    //Server request data
    private int col;
    private int row;
    private int pos;


    @Override
    public void init()
    {
        super.init();

        GuiHelper.setCurrentPage(ConstantValues.buyTurn);
        this.col = -1;
        this.row = -1;

        Row1.setHgap(10);
        Row2.setHgap(10);
        Row3.setHgap(10);

        buyButton.setOnMouseClicked(this::buyButton);
        backButton.setOnMouseClicked(this::goBack);
        //GuiHelper.resize(1280,720);
        this.cards = new ImageView[ConstantValues.rowDeck][ConstantValues.colDeck];

        click.setOpacity(0);

        GuiHelper.getStage().show();
        //DRAW DECK
        this.notifyObserver(controller ->{
            DebugMessages.printError("Buy Scene initialized");
            this.deckUpdate(controller.getMiniModel().getDecks());
        });
    }

    /**
     * when a new card is buyed this function is called by minimodel observable to update cards inside the decks
     * @param card new card
     * @param x    x pos of card buyed
     * @param y    y pos of card buyed
     */
    @Override
    public void updateDeckCard(ProductionCard card,int x,int y)
    {
        Image image = loadImage("/images/cards/productions/" +card.getId()+".jpg");;
        this.cards[y][x].setImage(image);
    }

    /**
     * print all deck (called only during initlialize then update single card)
     * @param deck 12 decks of shop
     */
    public void deckUpdate(ProductionCard [][] deck)
    {
        for(int i=0;i<ConstantValues.rowDeck;i++)
        {
            for(int j=0;j<ConstantValues.colDeck;j++)
            {
                drawCard(deck[i][j],j,i);
            }
        }
    }

    /**
     * print cards on screen and add them click event to select them
     * @param card
     * @param x
     * @param y
     */
    public void drawCard(ProductionCard card,int x,int y)
    {

        if(card== null)
        {
            //TODO DRAW THE BACK OF CARD INSTEAD OF LEADER
            //Loading image
            this.cards[y][x] = loadImage(ConstantValues.backCard,130,200);
        }
        else
        {
            //Loading image
            this.cards[y][x] = loadImage(ConstantValues.prodCardImagesPath +card.getId()+".jpg",130,200);

            this.cards[y][x].setId("production_card");

            this.cards[y][x].setOnMouseClicked(event -> {
                this.clickFunction(x,y);
            });
        }



        if(y==0) Row1.getChildren().add(this.cards[y][x]);
        if(y==1) Row2.getChildren().add(this.cards[y][x]);
        if(y==2) Row3.getChildren().add(this.cards[y][x]);
    }

    /**
     * the function that tells whoch card the user clicked
     * @param x
     * @param y
     */
    public void clickFunction(int x,int y)
    {
        click.setOpacity(1);
        DebugMessages.printError("Clicked card -> " + x + " - " + y);

        click.setText((x + 1) + " - " + (y + 1));
        setSelectedCard(x,y);
    }
    public void setCol(int x)
    {
        this.col = x;
    }

    public void setRow(int y)
    {
        this.row = y;
    }

    /**
     * save inside col,row variable the selected card
     * @param x col
     * @param y row
     */
    public void setSelectedCard(int x,int y)
    {
        setCol(x);
        setRow(y);
        Image currCard = this.cards[y][x].getImage();

        DebugMessages.printError("Image setted");
        selectedCard.setImage(currCard);
    }


    @Override
    public void reciveMessage(String msg) {
        super.reciveMessage(msg);
        ToastMessage t = new ToastMessage(msg,this.root,5000);
        t.show();
        this.resetObserverAfterDialog();
    }


    /**
     * the button to buy the card
     * @param mouseEvent
     */
    public void buyButton(MouseEvent mouseEvent) {

        if(GuiHelper.getCurrentPage()!=-1 && GuiHelper.getCurrentPage()!=ConstantValues.buyTurn)
        {
          reciveMessage("You cant buy this turn");
          return;
        }

        if(this.row == -1 && this.col==-1)
        {
            System.out.println("Error no card selected");
            return;
        }
        DialogProductionScene dialog = new DialogProductionScene();
        ButtonType result = loadDialog(FXMLpaths.prodDialog,"Chose dashboard position",dialog);

        if(result.equals(ButtonType.OK) && dialog.getPos()!= -1)
        {
            GuiHelper.setCurrentPage(ConstantValues.buyTurn);
            GuiHelper.setBuyType(true);
            this.pos = dialog.getPos();
            click.setText((this.col + 1) + " - " + (this.row + 1) + "-> in pos: "+ dialog.pos);
            this.notifyObserver(controller -> controller.sendBuyCard(this.row,this.col,this.pos));

            this.goBack(null);
        }
        else
        {
        }
    }

    /**
     * go back to the dashboard
     * @param mouseEvent
     */
    public void goBack(MouseEvent mouseEvent)
    {
        try {
            GuiHelper.setRoot(FXMLpaths.dashboard,new DashboardScene());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
