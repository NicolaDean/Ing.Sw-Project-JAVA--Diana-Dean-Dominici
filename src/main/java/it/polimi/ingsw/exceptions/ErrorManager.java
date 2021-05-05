package it.polimi.ingsw.exceptions;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.enumeration.ErrorMessages;
import it.polimi.ingsw.model.factory.CardFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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

    public void getErrorMessageFromCode(int code)
    {
        System.out.println("Error code: " + errors.get(code));
    }
}
