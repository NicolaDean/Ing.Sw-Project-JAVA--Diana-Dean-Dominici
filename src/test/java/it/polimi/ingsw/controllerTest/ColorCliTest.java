package it.polimi.ingsw.controllerTest;

import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceList;
import it.polimi.ingsw.view.utils.CliColors;
import it.polimi.ingsw.view.utils.Logger;
import it.polimi.ingsw.view.CLI;
import it.polimi.ingsw.view.View;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import static it.polimi.ingsw.enumeration.ResourceType.*;

public class ColorCliTest {

    @Test
    public void colorPrint()
    {
        CliColors test = new CliColors(System.out);

        test.printlnColored("Ciao",CliColors.CYAN_TEXT,CliColors.YELLOW_BACKGROUND);
        test.printlnColored("Come",CliColors.YELLOW_TEXT,CliColors.CYAN_BACKGROUND);
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

}
