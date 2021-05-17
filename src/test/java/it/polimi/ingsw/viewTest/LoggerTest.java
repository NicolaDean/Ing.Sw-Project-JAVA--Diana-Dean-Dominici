package it.polimi.ingsw.viewTest;

import it.polimi.ingsw.model.MiniModel;
import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.view.utils.Logger;
import org.junit.jupiter.api.Test;

public class LoggerTest {
    @Test
    public void testMarketPrint(){
        Logger l=new Logger();
        MiniModel mm=new MiniModel();
        Market m=new Market();
        mm.setMarket(m.getResouces(),m.getDiscardedResouce());
        l.printMarket(mm);
    }
}
