package it.polimi.ingsw.viewTest;

import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.view.CLI;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.utils.Logger;
import org.junit.jupiter.api.Test;

public class LoggerTest {
    @Test
    public void testMarketPrint(){
        Logger l=new Logger();
        View v=new CLI(0);
        Market m=new Market();
        v.setMarket(m.getResouces(),m.getDiscardedResouce());
        l.printMarket(v);
    }
}
