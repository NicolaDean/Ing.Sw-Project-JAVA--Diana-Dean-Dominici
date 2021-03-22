package it.polimi.ingsw;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     *  Calls all Test Resources
     * @throws Exception .
     */
    @Test
    public void ResourceTest() throws Exception {
        ResourceOperatorTest tmp = new ResourceOperatorTest();
        tmp.AllResourceTest();
    }

    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }



}
