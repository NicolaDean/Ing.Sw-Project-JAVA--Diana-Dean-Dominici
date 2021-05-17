package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.model.MiniModel;
import it.polimi.ingsw.model.cards.ProductionCard;
import it.polimi.ingsw.model.market.balls.BasicBall;
import it.polimi.ingsw.model.dashboard.Deposit;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.view.observer.Observable;

import java.util.List;

public class GUI extends Observable<ClientController> implements View{


    @Override
    public void printWelcomeScreen() {

    }

    @Override
    public BasicBall[][] getMiniMarketBalls() {
        return new BasicBall[0][];
    }

    @Override
    public BasicBall getMiniMarketDiscardedResouce() {
        return null;
    }

    @Override
    public void showMarket(){

    }

    @Override
    public void setMarket(BasicBall[][] balls, BasicBall discarted) {

    }

    @Override
    public void showError() {

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
    public void showDecks(ProductionCard[][] ProductionCards) {

    }

    @Override
    public Resource askDiscardResource(Resource resource) {
        return null;
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
    public void showGameStarted() {

    }

    @Override
    public void abortHelp() {

    }

    @Override
    public void showMarketExtraction(List<Resource> resourceList, int whiteballs) {
        
    }

    @Override
    public void showStorage(Deposit[] deposits) {

    }

    @Override
    public void askEndTurn() {

    }

    @Override
    public void playerLogged(String nickname) {

    }
}
