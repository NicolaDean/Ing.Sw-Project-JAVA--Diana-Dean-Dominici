package it.polimi.ingsw;

import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.ResourceList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

import static it.polimi.ingsw.enumeration.ResourceType.*;

public class ResourceListTest {

    @Test
    public void AddToListTest()
    {
        List<Resource> tmp = new ResourceList();

        tmp.add(new Resource(COIN,2));
        tmp.add(new Resource(ROCK,2));
        tmp.add(new Resource(COIN,3));

        for(Resource res:tmp)
        {
            if(res.getType() == COIN) assertTrue(res.getQuantity()==5);
            if(res.getType() == ROCK) assertTrue(res.getQuantity()==2);
        }

    }

    @Test

    public void RemovingTest()
    {
        List<Resource> tmp = new ResourceList();

        tmp.add(new Resource(COIN,2));
        tmp.remove(new Resource(COIN,1));

        for(Resource res:tmp)
        {
            if(res.getType() == COIN) assertTrue(res.getQuantity()==1);
        }
    }
}
