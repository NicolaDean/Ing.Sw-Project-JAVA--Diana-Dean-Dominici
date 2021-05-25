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
        //root.addEventFilter(DecksEvent.DECKS,this::deckUpdate);
        this.cards = new ImageView[3][4];

        click.setOpacity(0);

        GuiHelper.getStage().setWidth(2000);
        GuiHelper.getStage().setWidth(800);
        GuiHelper.getStage().show();
        //DRAW DECK
        this.notifyObserver(controller ->{

            while(controller.getMiniModel().getDecks() == null)
            {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            DebugMessages.printError("Initialized");
            this.deckUpdate(controller.getMiniModel().getDecks());
        });
    }

    @Override
    public void updateDeckCard(ProductionCard card,int x,int y)
    {
        Image image = new Image(BuyScene.class.getResourceAsStream("/images/cards/productions/" +card.getId()+".jpg"));
        this.cards[x][y].setImage(image);
    }

    public void deckUpdate(ProductionCard [][] deck)
    {
        System.out.println("CARDS " + cards.length);

        for(int i=0;i<3;i++)
        {
            for(int j=0;j<4;j++)
            {
                drawCard(deck[i][j],i,j);
            }
        }
    }

    public void drawCard(ProductionCard card,int x,int y)
    {
        //Creating an image
        Image image = new Image(BuyScene.class.getResourceAsStream("/images/cards/productions/" +card.getId()+".jpg"));

        this.cards[x][y] = new ImageView(image);

        //setting the fit height and width of the image view
        this.cards[x][y].setFitHeight(200);
        this.cards[x][y].setFitWidth(130);

        this.cards[x][y].setOnMouseClicked(event -> {
            click.setOpacity(1);
            setCol(x);
            setRow(y);
            DebugMessages.printError("Clicked card -> " + x + " - " + y);

            click.setText((x + 1) + " - " + (y + 1));
            setSelectedCard(x,y);
        });

        if(x==0) Row1.getChildren().add(this.cards[x][y]);
        if(x==1) Row2.getChildren().add(this.cards[x][y]);
        if(x==2) Row3.getChildren().add(this.cards[x][y]);
    }

    public void setCol(int x)
    {
        this.col = x;
    }

    public void setRow(int y)
    {
        this.col = y;
    }

    public void setSelectedCard(int x,int y)
    {
        Image currCard = this.cards[x][y].getImage();

        DebugMessages.printError("Image setted");
        selectedCard.setImage(currCard);
    }

}
