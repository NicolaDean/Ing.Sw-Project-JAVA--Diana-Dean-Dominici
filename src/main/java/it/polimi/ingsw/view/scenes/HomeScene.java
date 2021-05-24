package it.polimi.ingsw.view.scenes;

import it.polimi.ingsw.view.GUI;
import it.polimi.ingsw.view.GuiHelper;
import it.polimi.ingsw.view.utils.FXMLpaths;

import java.io.IOException;

public class HomeScene  extends BasicSceneUpdater{


    public HomeScene()
    {

    }
    public void singlePlayerCall()
    {
        gui.setSingleplayer();
        gui.askServerData();
    }

    public void multiplayerCall()
    {
        gui.askServerData();
    }

    public void reconnectCall()
    {

    }
}
