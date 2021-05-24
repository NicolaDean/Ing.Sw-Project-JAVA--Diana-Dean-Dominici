package it.polimi.ingsw.view.events;

import it.polimi.ingsw.model.cards.ProductionCard;
import it.polimi.ingsw.model.dashboard.Deposit;
import javafx.event.Event;
import javafx.event.EventType;

public class DecksEvent extends Event {

    ProductionCard[][] cards;

    public static final EventType<DecksEvent> DECKS = new EventType<>(Event.ANY,"DECKS");

    public DecksEvent(EventType<? extends Event> eventType, ProductionCard[][] cards) {
            super(eventType);
            this.cards = cards;
    }

    public ProductionCard[][] getCards()
    {
        return  this.cards;
    }

}
