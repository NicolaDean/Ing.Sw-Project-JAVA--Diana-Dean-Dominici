package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.view.observer.Observable;

import java.util.List;

public class GUI extends Observable<ClientController> implements View{

    @Override
    public void printWelcomeScreen() {

    }

    @Override
    public void askNickname() {

    }

    @Override
    public void askServerData() {

    }

    @Override
    public void askServerData(String errore) {

    }

    @Override
    public void askBuy() {

    }

    @Override
    public void askProduction() {

    }

    @Override
    public void askBonusProduction() {

    }

    @Override
    public void askBasicProduction() {

    }

    @Override
    public void askMarketExtraction() {

    }

    @Override
    public void askDiscardResource(List<Resource> resourceList) {

    }

    @Override
    public void askResourceInsertion(List<Resource> resourceList) {

    }

    @Override
    public void askResourceExtraction(List<Resource> resourceList) {

    }

    @Override
    public void askSwapDeposit() {

    }

    @Override
    public void askTurnType() {

    }

    @Override
    public void askCommand() {

    }

    @Override
    public void playerLogged(String nickname) {

    }
}
