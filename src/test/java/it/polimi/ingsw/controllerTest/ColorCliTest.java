package it.polimi.ingsw.controllerTest;

import it.polimi.ingsw.utils.CliColors;
import it.polimi.ingsw.utils.Logger;
import org.junit.jupiter.api.Test;

public class ColorCliTest {

    @Test
    public void colorPrint()
    {
        CliColors test = new CliColors(System.out);

        test.printColored("Ciao",CliColors.YELLOW_BACKGROUND,CliColors.CYAN_TEXT);
        test.printColored("Come",CliColors.CYAN_BACKGROUND,CliColors.YELLOW_TEXT);
    }

    @Test
    public void logger()
    {
        Logger logger = new Logger();

        logger.Welcome();
    }
}
