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

    /**
     * allow to load an image view
     * @param path    image path
     * @param width   x dimension
     * @param height  y dimension
     * @return
     */
    public static ImageView loadImage(String path,int width,int height)
    {
        ImageView img = new ImageView(loadImage(path));
        img.setFitWidth(width);
        img.setFitHeight(height);

        return img;
    }

    /**
     * allow to load an image with simple path
     * @param path
     * @return
     */
    public static Image loadImage(String path)
    {
        return new Image(Objects.requireNonNull(BasicSceneUpdater.class.getResourceAsStream(path)));
    }

    /**
     * same of send message but with error content (ag NACK) (called from GUIHELPER.sendError())
     * @param msg mesage sended
     */
    public void reciveError(String msg)
    {

    }

    /**
     * recive a message from the GUIHELPER (gui manager) -> this message is recived from Guihelper.sendMessage()
     * @param msg mesage sended
     */
    public void reciveMessage(String msg)
    {

    }

    /**
     *
     * @param player
     * @param storage
     */
    public void updateStorage(int player,Deposit[] storage)
    {

    }

    /**
     * Update cards inside shop
     * @param card new card to update
     * @param x    deck col
     * @param y    deck row
     */
    public void updateDeckCard(ProductionCard card, int x,int y)
    {

    }

    /**
     * Update scene dashboard cards
     * @param card   new card to update
     * @param pos    dashboard pos to update
     * @param player player minimodel index (used in spyScene to detect if the update correspond to a spyed player
     */
    public void updateDashCard(ProductionCard card,int pos,int player)
    {

    }
    /**
     * @param player player that has some updates in miniplayer
     * @param chest new player chest
     */
    public void updateChest(int player,List<Resource> chest)
    {

    }

    public void updateMarket()
    {

    }


    /**
     * method used by observable to update scene player position
     * @param player player that has some updates in miniplayer
     * @param newPos new player position
     */
    public void updatePlayerPosition(int player,int newPos)
    {

    }
}
