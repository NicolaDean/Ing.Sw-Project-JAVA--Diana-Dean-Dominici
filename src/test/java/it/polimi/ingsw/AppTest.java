package it.polimi.ingsw;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.model.Dashboard;
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
    public void DashboardTest()
    {
        DashboardTest tmp = new DashboardTest();
        tmp.TestAll();
    }

    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }



}
