package it.polimi.ingsw.view.scenes;

import it.polimi.ingsw.controller.packets.ExtractionInstruction;
import it.polimi.ingsw.model.dashboard.Deposit;
import it.polimi.ingsw.model.resources.Resource;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class PaymentDialog extends BasicDialog{

    @FXML
    public FlowPane source;
    @FXML
    public FlowPane destination;
    @FXML
    public FlowPane deposit1;
    @FXML
    public FlowPane deposit2;
    @FXML
    public FlowPane deposit3;
    @FXML
    public FlowPane chest;

    List<Pane>pendingCost;
    List<Resource> cost;

    List<ExtractionInstruction> packetInfo;

    public PaymentDialog(List<Resource> resourceList)
    {
        this.cost = resourceList;
    }

    @Override
    public void init() {
        super.init();

        this.pendingCost = new ArrayList<>();
        this.packetInfo  = new ArrayList<>();

        deposit1.setId("-d1");
        deposit2.setId("-d2");
        deposit3.setId("-d3");


        drawCost();
        drawStorage();
        drawChest();
    }



    private void dragDeposit(Pane pane)
    {
       /* pane.setOnDragDetected(event -> {
            //On drag dropped pane.getChildren().remove(0);


            Dragboard db = pane.startDragAndDrop(TransferMode.ANY);
            //ADD TO DRAGBOARD CIO CEH SERVE (stringa o immagine)

            Image img = ((ImageView)pane.getChildren().get(0)).getImage();

            ClipboardContent content = new ClipboardContent();
            content.putString(pane.getId());
            content.putImage(img);
            db.setContent(content);

            event.consume();
            ((ImageView)pane.getChildren().get(0)).getImage();
        });*/
    }

    private void onDragDetected(MouseEvent mouseEvent) {

    }


    /**
     * draw user storage and add drag event to resources
     */
    public void drawStorage()
    {
        this.notifyObserver(controller -> {
            Deposit[] storage = controller.getMiniModel().getPersonalPlayer().getStorage();

            drawDeposit(deposit1,storage[0]);
            drawDeposit(deposit2,storage[1]);
            drawDeposit(deposit3,storage[2]);
        });
    }

    public boolean resIsContained(Resource res)
    {
        for(Resource r:this.cost)
        {
            if(r.getType() == res.getType()) return true;
        }

        return false;
    }

    /**
     * add drag event only if this resource is in the cost list
     * @param container deposit/chest pane
     * @param resource  resource inside
     */
    public void addDragEvent(Pane container,Resource resource)
    {
        if(resIsContained(resource))
        {
            container.setOnDragDetected(this::onDragDetected);
        }
    }
    /**
     * Draw user owned resources inside a specific deposit and add to them the DRAG event
     * @param container deposit container (FlowPane or Gridpane... whatever pane used)
     * @param deposit   deposit to draw
     */
    public void drawDeposit(FlowPane container,Deposit deposit)
    {
        addDragEvent(container,deposit.getResource());
        for(int i=0;i<deposit.getResource().getQuantity();i++)
        {
            container.getChildren().add(loadImage("/images/resources/"+deposit.getResource().getNumericType()+".png",40,40));
        }
    }

    /**
     * draw bonus deposits of user
     */
    public void drawBonusDeposits()
    {
        //TODO
    }

    /**
     * draw user resources contained inside chest and add drag event to resources
     */
    public void drawChest()
    {
        this.notifyObserver(controller -> {
            List<Resource> resources = controller.getMiniModel().getPersonalPlayer().getChest();

            for(Resource res:resources)
            {
                chest.getChildren().add(resourcePane(res,50));
            }
        });
    }

    /**
     * draw a list of resources corresponding to the pending cost user have
     */
    public void drawCost()
    {
        for(Resource res: this.cost)
        {
            if(res.getQuantity()>0)
            {
                source.getChildren().add(resourcePane(res,100));
            }

        }
    }

    /**
     * draw a resource with Quantity label
     * @param res  resource to print
     * @param size image size (square size*size)
     * @return
     */
    public Pane resourcePane(Resource res,int size)
    {
        Pane p = new Pane();
        p.getChildren().add(loadImage("/images/resources/"+res.getNumericType()+".png",size,size));
        p.getChildren().add(new Label("Q:" + res.getQuantity()));
        p.setId(res.getType().toString());//per cercare e cambiare quantity

        return p;
    }
}
