package it.polimi.ingsw.view.scenes;

import it.polimi.ingsw.model.cards.LeaderCard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.FlowPane;


public class DialogLeader extends BasicDialog{



    @FXML
    public ImageView bin;
    @FXML
    public FlowPane source;
    @FXML
    public FlowPane destination;

    public Button   btOk;

    LeaderCard[] cards;

    int [] out = new int[2];

    int numOfInserted =0;
    public DialogLeader(LeaderCard[] cards)
    {
        this.cards = cards;
    }


    @Override
    public void init() {
        super.init();
        this.btOk = getControlButton(ButtonType.OK);


        this.out[0] = -1;
        this.out[1] = -1;

        int i=0;
        for(LeaderCard leaderCard: this.cards)
        {
            System.out.println("/images/cards/leaders/"+leaderCard.getId()+".jpg");
            ImageView imgSource = loadImage("/images/cards/leaders/"+leaderCard.getId()+".jpg",130,200);
            source.getChildren().add(imgSource);
            imgSource.setId(String.valueOf(i));
            //Set image on drag event
            imgSource.setOnDragDetected(event -> {
                Dragboard db = source.startDragAndDrop(TransferMode.ANY);
                //ADD TO DRAGBOARD CIO CEH SERVE (stringa o immagine)

                ClipboardContent content = new ClipboardContent();
                content.putString(imgSource.getId());
                content.putImage(imgSource.getImage());
                db.setContent(content);

                event.consume();
            });
            i++;
        }

        //Drag event on destination flowpane
        destination .setOnDragOver      (this::onOver);
        destination .setOnDragDropped   (this::destinationOnDragDropped);
        //Drag event to remove leaders from selection list
        //Drag event to remove leaders from selection list
        bin         .setOnDragOver      (this::onOver);
        bin         .setOnDragDropped   (this::binOnDragDropped);

        //Avoid user to exit with no selected resources
        btOk.addEventFilter(
                ActionEvent.ACTION,
                event -> {
                    if (!(this.numOfInserted >= 2)) {
                        System.out.println("You have to select "+ (2-this.numOfInserted)+" Leader");
                        event.consume();
                    }
                    else isReady = false;
                }
        );



    }


    /**
     * function called when an image is dropped from source to destination
     * (this function check if source and destination coincide) to avoid inserting card from this box inside this box
     * check also if card is already present (avoid inserting same card 2 time)
     *
     * @param event drag event
     */
    public void destinationOnDragDropped(DragEvent event)
    {
        if(event.getDragboard().getString().contains("-d"))
        {
            System.out.println("source and destination coincide");
            return;
        }
        int j=0;
        if(numOfInserted>=2)
        {
            //Show error
            System.out.println("full");
        }
        else
        {
            ImageView img = new ImageView( event.getDragboard().getImage());
            img.setFitHeight(200);
            img.setFitWidth(130);
            img.setId(numOfInserted + "-d");
            int leaderIndex = Integer.parseInt(event.getDragboard().getString());

            //CHECK IF ALREADY INSERTED
            for(int x : out)
            {
                if(leaderIndex == x)
                {
                    System.out.println("Already selected");
                    return;
                }
            }
            out[numOfInserted] = leaderIndex;
            System.out.println("Inserted:" + leaderIndex);
            numOfInserted ++;


            img.setOnDragDetected(discardableEvent -> {

                Dragboard db = source.startDragAndDrop(TransferMode.ANY);
                //ADD TO DRAGBOARD CIO CEH SERVE (stringa o immagine)

                ClipboardContent content = new ClipboardContent();
                content.putString(img.getId());
                content.putImage(img.getImage());
                db.setContent(content);
                discardableEvent.consume();
            });
            img.setFitWidth(130);
            img.setFitHeight(200);
            destination.getChildren().add(img);
        }
    }
    /**
     * simply consume drag event if present when mouse is over the destination/bin
     * @param event drag event
     */
    public void onOver(DragEvent event)
    {
        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        event.consume();
    }
    /**
     * function called if an imageView is dropped on the bin image
     * if the image was inside destination container this function remove it from view and decrese "numOfInserted"
     * @param event drag event
     */
    public void binOnDragDropped(DragEvent event)
    {
        String id = event.getDragboard().getString();
        if(id.contains("-d"))
        {
            int index = Integer.parseInt(id.substring(0,1));
            System.out.println("Cancelled:" + index);

            this.destination.getChildren().remove(index);
            this.out[index] = -1;

            if(index == 0 && this.destination.getChildren().size() >0)
            {
                this.destination.getChildren().get(0).setId(0 + "-d");
                this.out[0] = this.out[1];
                this.out[1] = -1;
            }

            //this.destination.getChildren().get(0).setId(0+ "-d");
            numOfInserted --;
        }
        event.consume();
    }
}
