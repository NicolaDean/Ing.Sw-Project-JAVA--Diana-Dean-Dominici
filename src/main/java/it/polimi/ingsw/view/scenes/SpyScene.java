package it.polimi.ingsw.view.scenes;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.ProductionCard;
import it.polimi.ingsw.model.dashboard.Deposit;
import it.polimi.ingsw.model.minimodel.MiniPlayer;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.view.scenes.BasicSceneUpdater;
import javafx.application.Platform;

import java.util.List;

public class SpyScene extends DashboardScene {

    private String nickname = "0";

    public SpyScene(int spyIndex,String nickname)
    {
        this.nickname = nickname;
        this.setIndex(spyIndex);
    }

    @Override
    public void init() {
        super.init();

        this.notifyObserver(controller -> {

            MiniPlayer p = controller.getMiniModel().getPlayers()[this.getIndex()];

            this.drawLeaders     (p.getLeaderCards());
            this.drawStorage     (p.getStorage());
            this.drawChest       (p.getChest());
            this.drawProductions (p.getDecks());
            this.drawPosition    (p.getPosition());
            drawNicknames();

        });
    }

}
