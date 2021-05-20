package it.polimi.ingsw.viewTest;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.market.Market;
import it.polimi.ingsw.model.minimodel.MiniPlayer;
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

    @Test
    public void testPapalPrint(){
        Logger l=new Logger();
        int a=(int)(Math.random()*10)%25;
        MiniPlayer[] player = new MiniPlayer[4];

        for (int i = 0; i < 4; i++) {
            player[i]=new MiniPlayer(""+i);
            player[i].incrementPosition(a);
        }
        l.printPapalPosition(player);

        player = new MiniPlayer[4];
        a=(int)(Math.random()*10)%25;
        for (int i = 0; i < 3; i++) {
            player[i]=new MiniPlayer(""+i);
            player[i].incrementPosition(a);
        }
        player[3]= new MiniPlayer("3");
        player[3].incrementPosition((int)(Math.random()*10)%25);
        l.printPapalPosition(player);


        player = new MiniPlayer[4];
        for (int i = 0; i < 4; i++) {
            player[i]=new MiniPlayer(""+i);
            player[i].incrementPosition((int)(Math.random()*10)%25);
        }
        l.printPapalPosition(player);

    }
}
