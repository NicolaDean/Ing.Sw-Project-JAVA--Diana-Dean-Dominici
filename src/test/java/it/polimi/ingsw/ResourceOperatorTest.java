package it.polimi.ingsw;

import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.ResourceOperator;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.enumeration.ResourceType.*;
import static it.polimi.ingsw.enumeration.ResourceType.SERVANT;
import static org.junit.Assert.*;

/**
 * This Test check the correct output from the Resource operation like comparison or sum/sub
 */
public class ResourceOperatorTest {
    @Test
    public void TestResourceOperatorCompare()
    {
        List<Resource> a = new ArrayList<Resource>();
        List<Resource> b = new ArrayList<Resource>();


        //Test True case
        a.add(new Resource(SHILD,2));
        a.add(new Resource(COIN,1));
        a.add(new Resource(SERVANT,3));

        b.add(new Resource(SHILD,1));
        b.add(new Resource(COIN,1));
        b.add(new Resource(SERVANT,1));

        assertTrue(ResourceOperator.compare(a,b));

        //Test False Case
        a  = new ArrayList<Resource>();
        b  = new ArrayList<Resource>();

        a.add(new Resource(SHILD,1));
        a.add(new Resource(COIN,1));
        a.add(new Resource(SERVANT,1));

        b.add(new Resource(SHILD,2));
        b.add(new Resource(COIN,2));
        b.add(new Resource(SERVANT,2));

        assertFalse(ResourceOperator.compare(a,b));

        //Test case when all type are different
        a  = new ArrayList<Resource>();
        b  = new ArrayList<Resource>();

        a.add(new Resource(SHILD,2));
        a.add(new Resource(COIN,1));

        b.add(new Resource(SERVANT,2));
        b.add(new Resource(ROCK,2));


        assertFalse(ResourceOperator.compare(a,b));
    }
}
