package it.polimi.ingsw.view.scenes;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.model.cards.ProductionCard;
import it.polimi.ingsw.model.dashboard.Deposit;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.view.GUI;
import it.polimi.ingsw.view.GuiHelper;
import it.polimi.ingsw.view.observer.Observable;

import java.util.List;

public class BasicSceneUpdater extends Observable<ClientController> {

    protected GUI gui= GuiHelper.getGui();

    public void init()
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
