package it.polimi.ingsw.view.scenes;

import it.polimi.ingsw.enumeration.ResourceType;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceList;
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

public class InitialResources extends BasicSceneUpdater{


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

    public Button btOk;

    List<Resource> out;
    int numOfChoices;
    int numOfInserted;

    public InitialResources(int numOfChoices)
    {
        this.numOfChoices = numOfChoices;
    }


    @Override
    public void init() {
        super.init();

        this.btOk = (Button) dialog.lookupButton(ButtonType.OK);

        this.out = new ResourceList();
        for(ResourceType resourceType : ResourceType.values())
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


        destination.setOnDragOver(event -> {

            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);

            event.consume();
        });

        destination.setOnDragDropped(event -> {

            if(numOfInserted>=numOfChoices)
            {
                btOk.setCancelButton(true);
                return;
            }
            //Load a resource corresponding to image
            numOfInserted ++ ;
            ResourceType res = ResourceType.valueOf(event.getDragboard().getString());

            this.out.add(new Resource(res,1));

            System.out.println("dropped " + event.getDragboard().getString());
            ImageView img = new ImageView(event.getDragboard().getImage());
            img.setFitHeight(100);
            img.setFitWidth(100);
            resContainer.getChildren().add(img);
        });

        //Avoid user to exit with no selected resources
        btOk.addEventFilter(
                ActionEvent.ACTION,
                event -> {
                    if (!(this.numOfInserted >= this.numOfChoices)) {
                        System.out.println("You have to select "+ (this.numOfChoices-this.numOfInserted)+" Resources");
                        event.consume();
                    }
                }
        );
    }

    public List<Resource> getResources()
    {
        return this.out;
    }
}
