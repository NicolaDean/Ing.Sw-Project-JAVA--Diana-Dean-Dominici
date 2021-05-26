package it.polimi.ingsw.view.scenes;

import it.polimi.ingsw.model.cards.ProductionCard;
import it.polimi.ingsw.model.dashboard.Deposit;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.view.scenes.BasicSceneUpdater;

import java.util.List;

public class SpyScene extends BasicSceneUpdater {

    private int    spyIndex = 0;
    private String nickname = "0";

    public SpyScene(int spyIndex,String nickname)
    {
        this.nickname = nickname;
        this.spyIndex = spyIndex;
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void updateChest(int player,List<Resource> chest) {
        super.updateChest(player,chest);
    }

    @Override
    public void updateStorage(int player,Deposit[] storage) {
        super.updateStorage(player,storage);
    }

    @Override
    public void updateDashCard(ProductionCard card, int pos, int player) {
        super.updateDashCard(card, pos, player);
    }


}
