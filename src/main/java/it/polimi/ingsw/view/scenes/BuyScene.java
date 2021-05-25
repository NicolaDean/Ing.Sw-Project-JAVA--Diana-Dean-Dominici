package it.polimi.ingsw.view.scenes;

import it.polimi.ingsw.model.cards.ProductionCard;
import it.polimi.ingsw.utils.DebugMessages;
import it.polimi.ingsw.view.GuiHelper;
import it.polimi.ingsw.view.events.DecksEvent;
import it.polimi.ingsw.viewtest.Appp;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;


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

    private ImageView[][] cards;
    private int col;
    private int row;

    @Override
    public void init()
    {
        super.init();
        this.cards = new ImageView[3][4];

        click.setOpacity(0);

        GuiHelper.getStage().setWidth(2000);
        GuiHelper.getStage().setWidth(800);
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
        System.out.println("CARDS " + cards.length);

        for(int i=0;i<3;i++)
        {
            for(int j=0;j<4;j++)
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
        //Creating an image
        Image image = loadImage("/images/cards/productions/" +card.getId()+".jpg");

        this.cards[y][x] = new ImageView(image);

        //setting the fit height and width of the image view
        this.cards[y][x].setFitHeight(200);
        this.cards[y][x].setFitWidth(130);

        this.cards[y][x].setOnMouseClicked(event -> {
            this.clickFunction(x,y);
        });

        if(y==0) Row1.getChildren().add(this.cards[y][x]);
        if(y==1) Row2.getChildren().add(this.cards[y][x]);
        if(y==2) Row3.getChildren().add(this.cards[y][x]);
    }

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

}
