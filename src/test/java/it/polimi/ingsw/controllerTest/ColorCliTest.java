package it.polimi.ingsw.controllerTest;

import it.polimi.ingsw.controller.packets.GameStarted;
import it.polimi.ingsw.exceptions.FullDepositException;
import it.polimi.ingsw.exceptions.NoBonusDepositOwned;
import it.polimi.ingsw.exceptions.WrongPosition;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.cards.ProductionCard;
import it.polimi.ingsw.model.dashboard.Deposit;
import it.polimi.ingsw.model.dashboard.Storage;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceList;
import it.polimi.ingsw.view.utils.CliColors;
import it.polimi.ingsw.view.utils.Logger;
import it.polimi.ingsw.view.CLI;
import it.polimi.ingsw.view.View;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import static it.polimi.ingsw.enumeration.ResourceType.*;

public class ColorCliTest {

    @Test
    public void colorPrint()
    {
        CliColors test = new CliColors(System.out);

        test.printColored("Ciao",CliColors.CYAN_TEXT,CliColors.YELLOW_BACKGROUND);
        test.printColored("Come",CliColors.YELLOW_TEXT,CliColors.CYAN_BACKGROUND);
    }

    @Test
    public void logger()
    {
        Logger logger = new Logger();

        logger.Welcome();
    }

    @Disabled
    public void printLogo()
    {
        View view = new CLI();

        view.printWelcomeScreen();
        view.askNickname();
    }

    @Test
    public void fedetest()
    {
        View view = new CLI();

    }

    @Test
    public void loggerFunction()
    {
        Logger log = new Logger();

        List<Resource> resourceList = new ResourceList();

        System.out.println("Printed one by one");
        log.printResource(new Resource(COIN,1));
        log.printResource(new Resource(SHIELD,2));
        log.printResource(new Resource(SERVANT,1));
        log.printResource(new Resource(ROCK,1));


        System.out.println("\nPrinted with printList");
        resourceList.add(new Resource(COIN,1));
        resourceList.add(new Resource(SHIELD,1));
        resourceList.add(new Resource(SERVANT,1));
        resourceList.add(new Resource(ROCK,1));

        log.printResourceList(resourceList);
    }

    @Test
    public void printCard()
    {
        Game g = new Game();

        Logger logger = new Logger();

        ProductionCard[][] cards = new ProductionCard[3][4];

        int i=0;

        for(Stack<ProductionCard> p[] :g.getProductionDecks())
        {
            int j=0;
            for(Stack<ProductionCard> t:p)
            {
                cards[i][j] = t.peek();
                j++;
            }
            i++;
        }
        logger.printDeks(cards);

        Storage s = new Storage();

        try {
            s.safeInsertion(new Resource(COIN,1),1);
        } catch (NoBonusDepositOwned noBonusDepositOwned) {
            noBonusDepositOwned.printStackTrace();
        } catch (WrongPosition wrongPosition) {
            wrongPosition.printStackTrace();
        } catch (FullDepositException e) {
            e.printStackTrace();
        }

        Deposit [] x = s.getDeposits();

        logger.printDashboard(x,cards[2][2].getCost(),cards[1]);
    }


}
