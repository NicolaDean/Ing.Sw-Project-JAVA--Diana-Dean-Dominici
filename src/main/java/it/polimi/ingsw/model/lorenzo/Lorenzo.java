package it.polimi.ingsw.model.lorenzo;

import it.polimi.ingsw.model.lorenzo.token.ActionToken;

import java.io.Serializable;

public class Lorenzo implements Serializable {


    public Lorenzo() {
        positionBlackCross = 0;
    }

    private int positionBlackCross;

    public int getPosition(){
        return positionBlackCross;
    }

    public ActionToken drawToken(){
        return null;
    }

    public void activateToken(LorenzoGame l,ActionToken a){
        a.activateToken(l);
    }

    public void incrementPosition(int n){
        positionBlackCross += n;
    }

}
