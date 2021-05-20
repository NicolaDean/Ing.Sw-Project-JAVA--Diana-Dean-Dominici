package it.polimi.ingsw.model.cards.leaders;

import it.polimi.ingsw.enumeration.ResourceType;
import it.polimi.ingsw.exceptions.NotSoddisfedPrerequisite;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.PrerequisiteCard;
import it.polimi.ingsw.model.market.balls.BasicBall;
import it.polimi.ingsw.model.market.balls.ResourceBall;
import it.polimi.ingsw.model.resources.Resource;

import java.util.List;

public class LeaderWhiteCard extends LeaderCard {

    public LeaderWhiteCard(List<Resource> cost, List<PrerequisiteCard> cardPrequisite, int victoryPoints, ResourceType type) {
        super(cost,cardPrequisite, victoryPoints, type);
    }

    @Override
    public void activate(Player p) throws NotSoddisfedPrerequisite {
        super.activate(p);
        p.addBonusball(this.getType());
    }
}