package it.polimi.ingsw.view.scenes;

import it.polimi.ingsw.model.cards.ProductionCard;
import javafx.fxml.FXML;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import it.polimi.ingsw.utils.ConstantValues;

/**
 * Ask user where he want to put a buyed card
 */
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
            if(card !=null)
                this.cards[i-1] = new ImageView(loadImage(ConstantValues.prodCardImagesPath+card.getId()+".jpg"));
            else
                this.cards[i-1] = new ImageView(loadImage(ConstantValues.backCard));
            int finalI = i;
            this.cards[i-1].setOnMouseClicked(event -> {
                setPos(finalI -1);
                //TODO ADD CSS STYLE FOR CARD SELECTED
            });
            this.cards[i-1].setFitHeight(200);
            this.cards[i-1].setFitWidth(130);
            this.productions.getChildren().add(this.cards[i-1]);
            this.cards[i-1].setId("production_card");
            i++;
        }
    }

    private void setPos(int i) {
        this.pos = i;
    }

}
