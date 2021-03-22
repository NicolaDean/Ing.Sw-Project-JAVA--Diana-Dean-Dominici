package it.polimi.ingsw.model;

import it.polimi.ingsw.enumeration.ResourceType;

import java.util.*;
import java.util.stream.Stream;

public class Dashboard {
    Storage Storage;
    Resource [] Chest;
    Stack<ProductionCard>[] ProducionCards;
    boolean [] PapalToken;

    public Dashboard()
    {
        this.ProducionCards = new Stack[3];

        this.ProducionCards[0] = new Stack<ProductionCard>();
        this.ProducionCards[1] = new Stack<ProductionCard>();
        this.ProducionCards[2] = new Stack<ProductionCard>();
    }

    public int getScore(){
        return 0;
    }

    /**
     *
     * @param card Card to position
     * @param pos Position where i want to put the card
     * @return true if card correctly setted
     */
    public boolean setProcuctionCard(ProductionCard card,int pos)
    {
        boolean out = this.checkValidPosition(card,pos);

        if(out)
        {
            this.ProducionCards[pos].add(card);
        }
        return out;
    }

    /**
     *
     * @param card Card to position
     * @param pos  position to check
     * @return  true if the position is valid
     */
    private boolean checkValidPosition(ProductionCard card, int pos)
    {

        if(!ProducionCards[pos].isEmpty())
        {
            if(ProducionCards[pos].peek().getLevel() == card.getLevel()-1)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            if(card.getLevel()==1)
                return true;
            else
                return false;
        }

    }

    /**
     * Excange 2 resource (of wanted type)  for 1 of a different type
     * @param spendOne     Type i want to spend
     * @param spendTwo     Type i want to spend
     * @param obtain    Type i want
     * @return true if i can do a basicProduction
     */
    public boolean basicProduction(ResourceType spendOne, ResourceType spendTwo,ResourceType obtain)
    {
        List<Resource> availableRes = this.getAllAvailableResource();

        //NEED TO ADD A COMPARE OVERLOADING (list<resource>,resource)
        List<Resource> tmp = new ArrayList<Resource>();
        tmp.add(new Resource(spendOne,1));
        tmp.add(new Resource(spendTwo,1));

        boolean output = ResourceOperator.compare(availableRes,tmp);
        if(output)
        {
            Resource ob = new Resource(obtain,1);
            //ADD TO CHEST ob resource
        }
        return output;
    }

    public List<Resource> getAllAvailableResource()
    {
        return new ArrayList<Resource>();
    }

    /**
     *
     * @return the sum of all activated ProductionCards
     */
    public int getCardScores()
    {
        int vp=0;

        for(Stack<ProductionCard> stack : ProducionCards)
        {
            vp +=stack.stream().mapToInt(ProductionCard::getScore).sum();
        }
        return vp;
    }
}
