package it.polimi.ingsw.model.minimodel;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.dashboard.Storage;
import it.polimi.ingsw.model.resources.Resource;

import java.util.List;

public class MiniPlayer {
    private List<Resource> chest;
    private int position;
    private String nickname;
    private Storage storage;

    public MiniPlayer(String nickname) {
        this.position = 0;
        this.nickname = nickname;
        this.chest = chest;
        this.storage = storage;
    }

    public List<Resource> getChest() {
        return chest;
    }

    public int getPosition() {
        return position;
    }

    public String getNickname() {
        return nickname;
    }

    public Storage getStorage() {
        return storage;
    }
}
