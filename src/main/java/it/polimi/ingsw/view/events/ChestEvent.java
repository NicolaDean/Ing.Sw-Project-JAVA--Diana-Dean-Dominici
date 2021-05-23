package it.polimi.ingsw.view.events;

import it.polimi.ingsw.model.resources.Resource;
import javafx.event.Event;
import javafx.event.EventType;

import java.util.List;

public class ChestEvent  extends Event {

    List<Resource> chest;
    public static final EventType<ChestEvent> CHEST = new EventType<>(Event.ANY,"ANY");

    public ChestEvent(EventType eventType, List<Resource> chest) {
        super(eventType);
        this.chest = chest;
    }

    public List<Resource> getChest()
    {
        return this.chest;
    }
}
