package it.polimi.ingsw.modelTest;

import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceList;
import it.polimi.ingsw.model.resources.ResourceOperator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

import static it.polimi.ingsw.enumeration.ResourceType.*;

public class ResourceListTest {

    /**
     * check if can add res to custom resource list
     */
    @Test
    public void AddToListTest()
    {
        List<Resource> tmp = new ResourceList();

        tmp.add(new Resource(COIN,2));
        tmp.add(new Resource(ROCK,2));
        tmp.add(new Resource(COIN,3));


        assertTrue(ResourceOperator.extractQuantityOf(COIN,tmp) == 5);
        assertTrue(ResourceOperator.extractQuantityOf(ROCK,tmp) == 2);
    }

    @Test

    /**
     * check if resource list remove override work
     */
    public void RemovingTest()
    {
        List<Resource> tmp = new ResourceList();

        tmp.add(new Resource(COIN,2));
        tmp.remove(new Resource(COIN,1));

        assertTrue(ResourceOperator.extractQuantityOf(COIN,tmp) == 1);
    }

    /**
     * check if resource list isempty override work
     */
    @Test

    public void isEmptyTest()
    {
        List<Resource> tmp = new ResourceList();

        assertTrue(tmp.isEmpty());

        tmp.add(new Resource(COIN,1));

        assertFalse(tmp.isEmpty());
    }
}
