package it.polimi.ingsw.utils;

/**
 * constant class to contain all constant values
 */
public class ConstantValues {


    public static int                       numberOfPlayer          = 4;
    public static int                       defaultServerPort       = 1234;
    public static String                    defaultIP               = "localhost";
    public static int                       normalDepositNumber     = 3;
    public static int                       maxBonusDepositNumb     = 2;
    public static int                       maxDepositsNumber       = ConstantValues.normalDepositNumber + ConstantValues.maxBonusDepositNumb;
    public static int                       bonusDepositSize        = 2;
    public static int                       rowDeck                 = 3;
    public static int                       colDeck                 = 4;
    public static int                       marketRow               = 3;
    public static int                       marketCol               = 4;
    public static int                       playerOwnedLeaders      = 2;
    public static int                       leaderCardsToDraw       = 4;
    public static int                       cardsWinningCondition   = 7;
    public static int                       productionSpaces        = 3;
    public static ResourceRappresentation   resourceRappresentation = new ResourceRappresentation();
    public static int                       numWhiteBall            = 4;
    public static int                       numBlueBall             = 2;
    public static int                       numYellowBall           = 2;
    public static int                       numGrayBall             = 2;
    public static int                       numVioletBall           = 2;

    public static double                    guiWidth                = 1280;
    public static double                    guiHeight               = 720;
    public static String                    reconnectFile           ="reconnectInfo.json";
    public static String                    saveFileName            ="save-state-";
    public static String                    currentIdFile           ="server-match-id-index.json";
    public static String                    prodCardImagesPath      ="/images/cards/productions/";
    public static String                    leaderCardImagesPath    ="/images/cards/leaders/";
    public static String                    tokens                  ="/images/tokens/";
    public static String                    backCard                ="/images/cards/back.png";
}
