package it.polimi.ingsw.utils;

import java.util.Locale;

public class CurrentOS {

    /**
     *
     * @return If we are on windows (and debug.windowsdetection=true) return true
     */
    public static boolean IsWindows()
    {
        return System.getProperty("os.name").toLowerCase(Locale.ROOT).contains("windows") && DebugMessages.windowsDetection;
    }


}
