package it.polimi.ingsw.view.scenes;

import it.polimi.ingsw.enumeration.ResourceType;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceList;
import it.polimi.ingsw.view.utils.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

import java.util.List;
import java.util.Optional;

/**
 * ask user which resources he want from a list (drag & drop)
 */
public class InitialResources extends BasicDialog{


    @FXML
    public DialogPane dialog;
    @FXML
    public FlowPane source;
    @FXML
    public ImageView servant;
    @FXML
    public Pane destination;
    @FXML
    public FlowPane resContainer;
    @FXML
    public ImageView bin;


    public Button btOk;

    ResourceType[] resourceTypes = null;
    List<Resource> out;
    int numOfChoices;
    int numOfInserted;

    public InitialResources(int numOfChoices)
    {
        this.numOfChoices = numOfChoices;
    }

    public InitialResources(ResourceType[] resourceTypes,int numOfChoices)
    {
        this.numOfChoices  = numOfChoices;
        this.resourceTypes = resourceTypes;
    }

    @Override
    public void init() {
        super.init();

        this.btOk = getControlButton(ButtonType.OK);
        this.out = new ResourceList();

        if(resourceTypes == null) resourceTypes = ResourceType.values();
        for(ResourceType resourceType : resourceTypes)
        {
            int name = resourceType.ordinal() + 1;
            ImageView img = loadImage("/images/resources/" +name  + ".png",100,100);
            img.setId(resourceType.toString());

            source.getChildren().add(img);

            //Set image as draggable
            img.setOnDragDetected(event -> {
                System.out.println("dragged");

                /* allow any transfer mode */
                Dragboard db = img.startDragAndDrop(TransferMode.ANY);

                /* put a string on dragboard */
                ClipboardContent content = new ClipboardContent();
                content.putString(img.getId());
                content.putImage(img.getImage());
                db.setContent(content);

                event.consume();
            });
        }

        //Set destination drag event
        destination.setOnDragOver(this::onOver);
        destination.setOnDragDropped(this::destinationOnDragDropped);

        //Set bin drag event
        bin.setOnDragOver(this::onOver);
        bin.setOnDragDropped(this::binOnDragDropper);

        //Avoid user to exit with no selected resources
        btOk.addEventFilter(
                ActionEvent.ACTION,
                event -> {
                    if (!(this.numOfInserted >= this.numOfChoices)) {
                        System.out.println("You have to select "+ (this.numOfChoices-this.numOfInserted)+" Resources");
                        event.consume();
                    }
                    {
                        (new Logger()).printInlineResourceList(this.out);
                        isReady = false;
                    }
                }
        );
    }


    /**
     * function called when something is dragged and dropped on the bin
     * @param event drag event
     */
    public void binOnDragDropper(DragEvent event)
    {
        String s = event.getDragboard().getString();

        if(s.contains("-d-"))
        {
            String [] split = s.split("-d-");
            s         = split[0];
            int index = Integer.parseInt(split[1]);
            System.out.println("Remove : " + index);
            ResourceType type = ResourceType.valueOf(s);

            this.out.remove(new Resource(type,1));

            this.resContainer.getChildren().remove(index);

            if(index==0 && !this.resContainer.getChildren().isEmpty())
            {
                split = this.resContainer.getChildren().get(0).getId().split("-d-");
                this.resContainer.getChildren().get(0).setId(split[0] + "-d-" + 0);
            }
            this.numOfInserted --;
        }
        else
        {
            System.out.println("not discardable");
        }
    }

    /**
     * when a resource is dropped from source to destination pane it will be added a new image
     * and resource type corresponding to it will be added to res list
     * @param event drag event
     */
    public void destinationOnDragDropped(DragEvent event)
    {
        if(event.getDragboard().getString().contains("-d-"))
        {
            System.out.println("Now allowed drag ( dest -> dest");
            return;
        }

        if(numOfInserted>=numOfChoices)
        {
            btOk.setCancelButton(true);
            return;
        }
        //Load a resource corresponding to image

        ResourceType res = ResourceType.valueOf(event.getDragboard().getString());

        this.out.add(new Resource(res,1));

        System.out.println("dropped " + event.getDragboard().getString() +" -> " + numOfInserted);
        ImageView img = new ImageView(event.getDragboard().getImage());
        img.setId(res.toString() + "-d-" + numOfInserted);
        img.setFitHeight(100);
        img.setFitWidth(100);

        numOfInserted ++ ;

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
        resContainer.getChildren().add(img);
        event.consume();
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

    public List<Resource> getResources()
    {
        return this.out;
    }
}
