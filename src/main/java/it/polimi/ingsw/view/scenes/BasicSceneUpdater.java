package it.polimi.ingsw.view.scenes;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.model.cards.ProductionCard;
import it.polimi.ingsw.model.dashboard.Deposit;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.view.GUI;
import it.polimi.ingsw.view.GuiHelper;
import it.polimi.ingsw.view.observer.Observable;
import it.polimi.ingsw.view.utils.FXMLpaths;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class BasicSceneUpdater extends Observable<ClientController> {


    public void init()
    {
        //Subscribe clientController as an observer to itselt
        this.setObserver(GuiHelper.getGui().getObserver());
    }


    public int loadDialog(String path)
    {
        try {

            DialogProductionScene result = new DialogProductionScene();
            DialogPane          pane = (DialogPane) GuiHelper.loadFXML(path,result);
            Dialog<ButtonType>  dialog  =  new Dialog<>();
            dialog.setDialogPane(pane);
            dialog.setTitle("Chose card positioning");

            Optional<ButtonType> options = dialog.showAndWait();
            if(options.get() == ButtonType.OK)
            {
                return result.getPos();
            }
            else return -1;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }
    public static Image loadImage(String path)
    {
        return new Image(Objects.requireNonNull(BasicSceneUpdater.class.getResourceAsStream(path)));
    }
    public void reciveError(String msg)
    {

    }
    public void reciveMessage(String msg)
    {

    }

    public void updateStorage(Deposit[] storage)
    {

    }

    public void updateDeckCard(ProductionCard card, int x,int y)
    {

    }

    public void updateChest(List<Resource> chest)
    {

    }

    public void updateMarket()
    {

    }


    public void updatePlayerPosition(int player,int newPos)
    {

    }
}
