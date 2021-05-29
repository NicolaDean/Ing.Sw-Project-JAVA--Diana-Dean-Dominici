package it.polimi.ingsw.view.utils;

import com.google.gson.Gson;
import it.polimi.ingsw.utils.DebugMessages;


import java.io.*;
import java.util.Arrays;
import java.util.List;

public class ErrorManager {


    List<String> errors;

    public ErrorManager()
    {
        Reader reader = new InputStreamReader(ErrorManager.class.getClassLoader().getResourceAsStream("json/errorMessages.json"));
        Gson gson = new Gson();
        String [] tmp = gson.fromJson(reader,String[].class);
        errors = Arrays.asList(tmp);
    }

    /**
     * Get error messages from ack code
     * @param code
     */
    public String getErrorMessageFromCode(int code)
    {
        if(code != 0) {
            Logger l = new Logger();
            //l.printError( errors.get(code));
            return errors.get(code);
        }

        return "";

    }
}
