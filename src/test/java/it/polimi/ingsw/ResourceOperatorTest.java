package it.polimi.ingsw;

import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceList;
import it.polimi.ingsw.model.resources.ResourceOperator;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.enumeration.ResourceType.*;

/**
 * This Test check the correct output from the Resource operation like comparison or sum/sub
 */
public class ResourceOperatorTest {

    @Test
    public void AllResourceTest() throws Exception {
        TestResourceOperatorCompare();
        TestResourceOperatorSum();
        TestResourceOperatorExceptionSum();
    }

    /**
     * Test different Possible Scenario for Resources List comparison
     */
    @Test
    public void TestResourceOperatorCompare()
    {
        List<Resource> a = new ResourceList();
        List<Resource> b = new ResourceList();


        //Test True case
        a.add(new Resource(SHIELD,2));
        a.add(new Resource(COIN,1));
        a.add(new Resource(SERVANT,3));

        b.add(new Resource(SHIELD,1));
        b.add(new Resource(COIN,1));
        b.add(new Resource(SERVANT,1));

        assertTrue(ResourceOperator.compare(a,b));

        //Test False Case
        a  = new ArrayList<Resource>();
        b  = new ArrayList<Resource>();

        a.add(new Resource(SHIELD,1));
        a.add(new Resource(COIN,1));
        a.add(new Resource(SERVANT,1));

        b.add(new Resource(SHIELD,2));
        b.add(new Resource(COIN,2));
        b.add(new Resource(SERVANT,2));

        assertFalse(ResourceOperator.compare(a,b));

        //Test case when all type are different
        a  = new ArrayList<Resource>();
        b  = new ArrayList<Resource>();

        a.add(new Resource(SHIELD,2));
        a.add(new Resource(COIN,1));

        b.add(new Resource(SERVANT,2));
        b.add(new Resource(ROCK,2));


        assertFalse(ResourceOperator.compare(a,b));
    }

    /**
     * Test that the sum are calculated correctly
     * @throws Exception if resource type is different
     */

    @Test
    public void TestResourceOperatorSum() throws Exception {
        Resource a= new Resource(SHIELD,2);
        Resource b= new Resource(SHIELD,1);

        Resource c =  ResourceOperator.sum(a,b);
        assertTrue(c.getQuantity()==3 && c.getType() == a.getType() && c.getType() == b.getType() );


    }

    /**
     * Test if Sum of 2 resource with different Type throw exeption
     */
    @Test
    public void TestResourceOperatorExceptionSum() {
        Assertions.assertThrows(Exception.class,()->{
            Resource a= new Resource(SHIELD,2);
            Resource b= new Resource(COIN,1);
            Resource c = ResourceOperator.sum(a,b);
        });


    }
}
