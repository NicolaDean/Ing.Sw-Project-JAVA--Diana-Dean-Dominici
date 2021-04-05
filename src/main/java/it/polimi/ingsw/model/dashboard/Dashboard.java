package it.polimi.ingsw.model.dashboard;

import it.polimi.ingsw.enumeration.resourceType;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.PrerequisiteCard;
import it.polimi.ingsw.model.cards.ProductionCard;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceList;
import it.polimi.ingsw.model.resources.ResourceOperator;

import java.util.*;

public class Dashboard {
    private Storage storage;
    private List<Resource> chest;
    private Stack<ProductionCard>[] producionCards;
    private boolean [] papalToken;
    private List<Resource> bonusResources;
    private List<Resource>pendingCost;


    public Storage getStorage() {
        return storage;
    }

    public Dashboard()
    {
        storage = new Storage();
        //chest = new ArrayList<Resource>();

        chest = new ResourceList();

        this.producionCards = new Stack[3];

        this.producionCards[0] = new Stack<ProductionCard>();
        this.producionCards[1] = new Stack<ProductionCard>();
        this.producionCards[2] = new Stack<ProductionCard>();

        papalToken = new boolean[3];
        for(int i=0;i<3;i++)
        {
            papalToken[i]=false;
        }
        bonusResources = null;
        pendingCost = new ResourceList();
    }

    public int getScore(){
        return 0;
    }

    /**
     *  Insert a resource in the storage
     * @param res resource to insert
     * @param pos deposit to select
     */
    public void storageInsertion(Resource res, int pos)
    {
        try {
            this.storage.safeInsertion(res,pos);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     *  Chest insertion
     * @param res resource to insert
     */
    public void chestInsertion(Resource res)
    {
        this.chest.add(res);
    }

    /**
     *  insert a list into the chest
     * @param res resources to insert
     */
    public void chestInsertion(List<Resource> res)
    {
        this.chest.addAll(res);
    }
    /**
     *
     * @param card Card to position
     * @param pos Position where i want to put the card
     * @return true if card correctly setted
     */
    public boolean setProcuctionCard(ProductionCard card,int pos)
    {
        if(pos > 2)return false;//invalid position

        boolean out = this.checkValidPosition(card,pos);

        if(out)
        {
            this.producionCards[pos].add(card);
            this.pendingCost.addAll(card.getCost(this));//Add card cost to the pendingPayment
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

        if(!producionCards[pos].isEmpty())
        {
            if(producionCards[pos].peek().getLevel() == card.getLevel()-1)
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
    public boolean basicProduction(resourceType spendOne, resourceType spendTwo, resourceType obtain)
    {
        List<Resource> availableRes = this.getAllAvailableResource();

        //NEED TO ADD A COMPARE OVERLOADING (list<resource>,resource)
        List<Resource> tmp = new ResourceList();
        tmp.add(new Resource(spendOne,1));
        tmp.add(new Resource(spendTwo,1));

        boolean output = ResourceOperator.compare(availableRes,tmp);
        if(output)
        {
            Resource ob = new Resource(obtain,1);
            this.chestInsertion(new Resource(obtain,1));
            this.pendingCost.add(new Resource(spendOne,1));
            this.pendingCost.add(new Resource(spendTwo,1));
        }
        return output;
    }


    /**
     * Activate the production of a production card
     * @param pos stack of card to select
     * @return true if the activation goes well
     */
    public boolean production(Player p, int pos)
    {
        ProductionCard card = this.producionCards[pos].peek();
        boolean out = card.produce(p);

        if(out)
        {
            this.pendingCost.addAll(card.getCost(this));
        }

        return out;
    }



    /**
     * Apply the cost of that particular res on the chest
     * @param res resource to pay
     */
    public void applyChestCosts(Resource res)
    {
        this.pendingCost.remove(res);
        this.chest.remove(res);
    }

    public void applyChestCosts(List<Resource> res)
    {
        this.pendingCost.retainAll(res);
        this.chest.removeAll(res);
    }

    /**
     * apply a cost on the storage ( and check if discount is available)
     * @param res resource to pay
     * @param pos deposit to select
     */
    public void applyStorageCosts(Resource res,int pos)
    {
            try {
                this.storage.safeSubtraction(res,pos);
                this.pendingCost.remove(res);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    /**
     *
     * @return infinite bonus resources available
     */
    public List<Resource> getDiscount()
    {
        return this.bonusResources;
    }

    /**
     *
     * @return get All resource from all possible sources
     */
    public List<Resource> getAllAvailableResource()
    {
        return ResourceOperator.merge(this.storage.getStorageAsList(),this.chest);
    }

    /**
     *
     * @return the sum of all activated ProductionCards
     */
    public int getCardScores()
    {
        int vp=0;

        for(Stack<ProductionCard> stack : producionCards)
        {
            vp +=stack.stream().mapToInt(ProductionCard::getScore).sum();
        }
        return vp;
    }

    public List<Resource> getPendingCost()
    {
        return this.pendingCost;
    }
    public void setPendingCost(List<Resource> pending)
    {
        this.pendingCost.addAll(pending);}

    public void setPendingCost(Resource pending)
    {
        this.pendingCost.add(pending);}
    public void setDiscount(Resource res)
    {
        if(this.bonusResources == null)
        {
            this.bonusResources = new ResourceList();
        }

        this.bonusResources.add(res);
    }

    /**
     *  check if the prerequisite card is iside the dashboard or not
     * @param requirements prerequisite card
     * @return true if exist
     */
    public boolean checkCardPresence(List<PrerequisiteCard> requirements)
    {
        //foreach stack and foreach card in it check if it match the prerequisite (if true return true)
        for(Stack<ProductionCard> s:this.producionCards)
        {
            //For each card of the stack
            for(ProductionCard card:s)
            {
                //For each prerequisite
                for(PrerequisiteCard requirement:requirements)
                {
                    //Check matching
                    if(card.compareType(requirement)) return true;
                }
            }
        }
        return false;
    }

    /**
     * Add a deposit bonus
     * @param typeBonus resource given from the bonus
     */
    public void addDepositBonus(resourceType typeBonus)
    {
        this.storage.initializeBonusDeposit(typeBonus);
    }

    }
