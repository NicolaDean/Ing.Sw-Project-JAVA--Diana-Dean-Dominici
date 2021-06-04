package it.polimi.ingsw.viewTest;

import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.view.CLI;
import it.polimi.ingsw.view.GUI;
import it.polimi.ingsw.view.View;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class GuiTest {

    @Disabled
    public void showMarketTest(){
        View v=new GUI();
        Market m=new Market();
        v.setMarket(m.getResouces(),m.getDiscardedResouce());
        v.showMarket();
    }
}
