package it.polimi.ingsw;

import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.factory.CardFactory;
import it.polimi.ingsw.model.factory.MapFactory;
import org.junit.jupiter.api.Test;

public class JsonTest {


    @Test
    public void LoadProdCard() throws Exception {
        CardFactory.loadProductionCardsFromJsonFile();
    }

    @Test
    public void LoadPapalSpace()
    {
        MapFactory.loadPapalSpacesFromJsonFile();
    }

    @Test
    public void LoadLeadersTest()
    {
        CardFactory.loadLeaderCardsFromJsonFile();
    }
}
