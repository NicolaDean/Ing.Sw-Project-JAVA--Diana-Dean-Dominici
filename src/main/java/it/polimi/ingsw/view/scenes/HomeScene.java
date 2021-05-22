package it.polimi.ingsw.view.scenes;

import it.polimi.ingsw.view.GuiHelper;
import it.polimi.ingsw.view.utils.FXMLpaths;

import java.io.IOException;

public class HomeScene {


    public void singlePlayerCall()
    {
        GuiHelper.getGui().setSingleplayer();
        try {
            GuiHelper.setRoot(FXMLpaths.askServerData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void multiplayerCall()
    {
        try {
            GuiHelper.setRoot(FXMLpaths.askServerData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reconnectCall()
    {

    }
}
