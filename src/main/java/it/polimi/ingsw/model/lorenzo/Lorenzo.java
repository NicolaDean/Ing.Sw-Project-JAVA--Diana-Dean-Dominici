package it.polimi.ingsw.model.lorenzo;

import it.polimi.ingsw.model.lorenzo.token.ActionToken;

public class Lorenzo {


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
        if(a.isSpecial())
            l.resetDeckToken();
    }

    public void incrementPosition(int n){
        positionBlackCross += n;
    }

}
