package it.polimi.ingsw.model.cards.leaders;

import it.polimi.ingsw.enumeration.ResourceType;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.PrerequisiteCard;
import it.polimi.ingsw.model.resources.Resource;

import java.util.List;

public class LeaderWhiteCard extends LeaderCard {

    public LeaderWhiteCard(List<Resource> cost, List<PrerequisiteCard> cardPrequisite, int victoryPoints, ResourceType type) {
        super(cost,cardPrequisite, victoryPoints, type);

    }

    @Override
    public boolean activate(Player p) {
        //TODO ADD THE ACTIVATE FUNCTIOn
        return super.activate(p);
    }
}
