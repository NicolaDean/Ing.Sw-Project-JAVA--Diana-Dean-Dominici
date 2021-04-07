package it.polimi.ingsw;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.LeaderCard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GameTest {

    @Test
    public void GameInitializingTest()
    {
        Game g = new Game();

        System.out.println("Test finito");
    }


    /**
     * this test is done by debugging
     * @throws Exception existing nickame, not enough players
     */
    @Test
    public void PapalSpcesActivation() throws Exception {
        Player p = new Player("nicola");

        Game game = new Game();

        game.addPlayer("nicola",new LeaderCard[0]);
        game.addPlayer("federico",new LeaderCard[0]);
        game.addPlayer("riccardo",new LeaderCard[0]);
        game.addPlayer("eliot",new LeaderCard[0]);

        game.startGame();

        p = game.nextTurn();

        p.incrementPosition(6);

        p = game.nextTurn();

        p.incrementPosition(8);

        p = game.nextTurn();
        p.incrementPosition();

    }
}
