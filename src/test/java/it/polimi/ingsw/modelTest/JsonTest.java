package it.polimi.ingsw.modelTest;

import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.factory.CardFactory;
import it.polimi.ingsw.model.factory.MapFactory;
import org.junit.jupiter.api.Test;

public class JsonTest {


    /**
     * Load production cards from json
     * @throws Exception
     */
    @Test
    public void LoadProdCard() throws Exception {
        CardFactory.loadProductionCardsFromJsonFile();
    }

    /**
     * Load papal spaces from json
     */
    @Test
    public void LoadPapalSpace()
    {
        MapFactory.loadPapalSpacesFromJsonFile();
    }

    /**
     * load leader card from json
     */
    @Test
    public void LoadLeadersTest()
    {
        CardFactory.loadLeaderCardsFromJsonFile();
    }
}
