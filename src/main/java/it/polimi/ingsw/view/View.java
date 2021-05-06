package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.ClientController;

public interface View {

    public void printWelcomeScreen();

    /**
     * ask user a valid nickname
     */
    public void askNickname();

    /**
     * ask ip and port then notify controller to try connecting to the server
     */
    public void askServerData();

    /**
     * show an error message and then
     * ask ip and port then notify controller to try connecting to the server
     * @param errore
     */
    public void askServerData(String errore);

    public void setObserver(ClientController controller);
}
