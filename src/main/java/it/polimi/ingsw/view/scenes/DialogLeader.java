package it.polimi.ingsw.view.scenes;

import it.polimi.ingsw.model.cards.LeaderCard;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.FlowPane;


public class DialogLeader extends BasicDialog{

    @FXML
    public ImageView source;
    @FXML
    public FlowPane destination;

    LeaderCard[] cards;
    public DialogLeader(LeaderCard[] cards)
    {
        this.cards = cards;
    }


    @Override
    public void init() {
        super.init();

        source.setOnDragDetected(event -> {
            Dragboard db = source.startDragAndDrop(TransferMode.ANY);
            //ADD TO DRAGBOARD CIO CEH SERVE (stringa o immagine)

            ClipboardContent content = new ClipboardContent();
            content.putString(source.getId());
            content.putImage(source.getImage());
            db.setContent(content);

            event.consume();
        });

        destination.setOnDragOver(event -> {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            event.consume();
        });

        destination.setOnDragDropped(event -> {

            ImageView img = new ImageView( event.getDragboard().getImage());
            destination.getChildren().add(img);

            isReady = true;
            //DISEGNA IMMAGINE NEL NUOVO PANE
        });
    }
}
