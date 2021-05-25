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


    public void loadDialog(String path)
    {
        try {
            DialogPane         pane = (DialogPane) GuiHelper.loadFXML(path,new DialogProductionScene());
            Dialog<ButtonType> dialog  =  new Dialog<>();
            dialog.setDialogPane(pane);
            dialog.setTitle("lol");

            Optional<ButtonType> options = dialog.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
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
