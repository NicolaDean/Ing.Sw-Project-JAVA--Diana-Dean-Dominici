package it.polimi.ingsw.view.scenes;

import it.polimi.ingsw.model.cards.ProductionCard;
import javafx.fxml.FXML;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;


public class DialogProductionScene extends BasicSceneUpdater{

    @FXML
    public FlowPane productions;

    ImageView [] cards = new ImageView[3];
    int pos;

    @Override
    public void init() {
        super.init();

        this.notifyObserver(controller ->{
            drawCards(controller.getMiniModel().getPersonalPlayer().getDecks());
        });
    }

    public int getPos()
    {
        return pos;
    }

    public void drawCards(ProductionCard[] cards)
    {
        int i=1;
        for(ProductionCard card:cards)
        {
            //if(card[i-1] !=null) //TODO ADD EMPTY CARD DRAWING (print back of a card) and put card.getId instead of i
            this.cards[i-1] = new ImageView(loadImage("/images/cards/productions/"+i+".jpg"));
            this.cards[i-1].setOnMouseClicked(event -> {
                setPos(i-1);
                //TODO ADD CSS STYLE FOR CARD SELECTED
            });
            this.cards[i-1].setFitHeight(200);
            this.cards[i-1].setFitWidth(130);
            this.productions.getChildren().add(this.cards[i-1]);
        }
    }

    private void setPos(int i) {
        this.pos = i;
    }

}
