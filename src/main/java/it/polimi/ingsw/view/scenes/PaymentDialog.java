package it.polimi.ingsw.view.scenes;

import it.polimi.ingsw.controller.packets.ExtractionInstruction;
import it.polimi.ingsw.enumeration.ResourceType;
import it.polimi.ingsw.model.dashboard.Deposit;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.view.GuiHelper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.enumeration.ResourceType.COIN;

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
    public FlowPane deposit4;
    @FXML
    public FlowPane deposit5;
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
        deposit4.setId("-d4");
        deposit5.setId("-d5");

        drawCost();
        drawStorage();
        drawChest();

        addOnExit();

        destination.setOnDragOver(this::onOver);
        destination.setOnDragDropped(this::onDropPayed);
    }


    private void onDropPayed(DragEvent event) {


        String id  = event.getDragboard().getString();
        Image  img = event.getDragboard().getImage();
        ResourceType type = ResourceType.valueOf(id.substring(3));

        if(!resIsContained(type)){
            System.out.println("Not necessary for payment");
            return;
        }

        System.out.println(id);
        switch (id.substring(0,3)) {
            case "-d1":
                packetInfo.add(new ExtractionInstruction(new Resource(type,1),0));
                deposit1.getChildren().remove(0);
                break;
            case "-d2":
                packetInfo.add(new ExtractionInstruction(new Resource(type,1),1));
                deposit2.getChildren().remove(0);
                break;
            case "-d3":
                packetInfo.add(new ExtractionInstruction(new Resource(type,1),2));
                deposit3.getChildren().remove(0);
                break;
            case "-d4":
                packetInfo.add(new ExtractionInstruction(new Resource(type,1),3));
                deposit4.getChildren().remove(0);
                break;
            case "-d5":
                packetInfo.add(new ExtractionInstruction(new Resource(type,1),4));
                deposit5.getChildren().remove(0);
                break;
            case "-c0":
                packetInfo.add(new ExtractionInstruction(new Resource(type,1)));
                removeChest(id);
                break;
        }
        ImageView a = new ImageView(img);

        destination.getChildren().add(resourcePane(new Resource(type,1),50));
        removeSourceQuantity(this.source,type);

    }


    public void removeChest(String id)
    {
        for(Node x: this.chest.getChildren())
        {
            Pane pane = (Pane) x;
            if(x.getId().equals(id))
            {
                Label l = ((Label)(pane.getChildren().get(1)));
                String q = l.getText().substring(2);
                int qty = Integer.parseInt(q)-1;

                if(qty==0)
                {
                    this.chest.getChildren().remove(x);
                }
                else
                {
                    l.setText("Q:" + (qty));
                }
            }

        }
    }
    public void removeSourceQuantity(Pane root,ResourceType type)
    {
        int cont=0;
        for(int i=0;i<root.getChildren().size();i++)
        {
            Pane pane = (Pane) root.getChildren().get(i);
            if(pane.getId().equals(type.toString()))
            {
                Label l = ((Label)(pane.getChildren().get(1)));
                String q = l.getText().substring(2);
                int qty = Integer.parseInt(q)-1;

                if(qty==0)
                {
                    this.source.getChildren().remove(root.getChildren().get(i));
                }
                else
                {
                    l.setText("Q:" + (qty));
                }
            }
        }
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
            drawDeposit(deposit4,storage[3]);
            drawDeposit(deposit5,storage[4]);
        });
    }

    public boolean resIsContained(ResourceType res)
    {
        for(Node x: this.source.getChildren()) {
            Pane pane = (Pane) x;
            if(x.getId().equals(res.toString())) {
                Label l = ((Label)(pane.getChildren().get(1)));
                String q = l.getText().substring(2);
                int qty = Integer.parseInt(q);

                if(qty==0) return false;

                return true;
            }
        }
        return false;
    }

    /**
     * add drag event only if this resource is in the cost list
     * @param container deposit/chest pane
     * @param resource  resource inside
     */
    public void addDragEvent(FlowPane container,Resource resource)
    {
        if(resIsContained(resource.getType()))
        {
            dragDeposit(container);
        }
    }
    /**
     * Draw user owned resources inside a specific deposit and add to them the DRAG event
     * @param container deposit container (FlowPane or Gridpane... whatever pane used)
     * @param deposit   deposit to draw
     */
    public void drawDeposit(FlowPane container,Deposit deposit)
    {

        if(deposit==null || deposit.getResource() == null)
        {
            System.out.println("Print empty dep");
            return;
        }
        dragDeposit(container);


        //addDragEvent(container,deposit.getResource());  //é brutto proprio impedire il drag, meglio impedire il drop
        for(int i=0;i<deposit.getResource().getQuantity();i++)
        {
            String id = container.getId().substring(0,3) + deposit.getResource().getType();
            System.out.println("ID: " + id);
            container.setId(id);
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
                Pane p = resourcePane(res,50);
                p.setId("-c0" + res.getType());
                dragDeposit(p);
                chest.getChildren().add(p);
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

    public void dragDeposit(Pane pane)
    {
        pane.setOnDragDetected(event -> {
            //On drag dropped pane.getChildren().remove(0);

            Dragboard db = pane.startDragAndDrop(TransferMode.ANY);
            //ADD TO DRAGBOARD CIO CEH SERVE (stringa o immagine)

            Image img = ((ImageView)pane.getChildren().get(0)).getImage();

            ClipboardContent content = new ClipboardContent();
            content.putString(pane.getId());
            content.putImage(img);
            db.setContent(content);

            event.consume();
        });
    }


    private void onOver(DragEvent event) {
        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        event.consume();
    }

    /**
     * funcion called with "OK" dialog button
     */
    private void addOnExit()
    {
        this.getControlButton(ButtonType.OK).addEventFilter(
                ActionEvent.ACTION,
                event -> {
                    if (this.source.getChildren().size() != 0) {
                        System.out.println("You must finish payment");
                        event.consume();
                    }
                    else{
                        this.notifyObserver(controller -> controller.sendResourceExtraction(GuiHelper.getBuyType(),packetInfo));
                    }
                }
        );
    }
}
