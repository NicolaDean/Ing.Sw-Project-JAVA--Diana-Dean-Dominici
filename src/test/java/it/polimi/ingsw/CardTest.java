package it.polimi.ingsw;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Assertions;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.Dashboard;
import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.enumeration.ResourceType.COIN;
import static it.polimi.ingsw.enumeration.ResourceType.SERVANT;

public class CardTest {

    @Test
    public void CheckCostTesting()
    {
        Dashboard dash = new Dashboard();

        List<Resource> tmp = new ArrayList<Resource>();
        tmp.add(new Resource(COIN,2));
        tmp.add(new Resource(SERVANT,2));

        Card c = new Card(tmp,3);

        assertTrue(c.getScore() == 3);

    }
}
