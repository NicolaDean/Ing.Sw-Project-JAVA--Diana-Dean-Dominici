package it.polimi.ingsw;

import it.polimi.ingsw.model.cards.JsonCardFactory;
import org.junit.jupiter.api.Test;

public class JsonTest {


    @Test
    public void json() throws Exception {
        JsonCardFactory.loadProductionCardsFromJsonFile();
    }
}
