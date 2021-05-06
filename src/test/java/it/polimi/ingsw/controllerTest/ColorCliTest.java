package it.polimi.ingsw.controllerTest;

import it.polimi.ingsw.view.utils.CliColors;
import it.polimi.ingsw.view.utils.Logger;
import it.polimi.ingsw.view.CLI;
import it.polimi.ingsw.view.View;
import org.junit.jupiter.api.Test;

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

    @Test
    public void printLogo()
    {
        View view = new CLI();

        view.printWelcomeScreen();
        view.askNickname();
    }
}
