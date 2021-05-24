package it.polimi.ingsw.view.events;

import javafx.event.Event;
import javafx.event.EventType;

public  class BasicEvent <T> extends Event {


    public static final EventType<GenericMessage> GENERIC = new EventType<>(Event.ANY,"ANY");

    private T content;
    public BasicEvent(EventType<? extends Event> eventType,T content) {
        super(eventType);
        this.content = content;
    }


    public T getContent()
    {
        return content;
    }
}
