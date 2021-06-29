package it.polimi.ingsw.view.utils;

import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * generate random nickames
 */
public class RandomNicks {

    /**
     *
     * @return random nickname from json files,generate also a random number to avoid collisions
     */
    static public String getRandomNickname()
    {
        List<String> errors;
        Reader reader = new InputStreamReader(ErrorManager.class.getClassLoader().getResourceAsStream("json/nickNames.json"));
        Gson gson = new Gson();
        String [] tmp = gson.fromJson(reader,String[].class);
        errors = Arrays.asList(tmp);
        Random rand = new Random();
        Integer random_int = rand.nextInt(errors.size()-1);
        String nickName = errors.get(random_int);
        random_int = rand.nextInt(999);
        String num = random_int.toString();
        return nickName+num;
    }
}
