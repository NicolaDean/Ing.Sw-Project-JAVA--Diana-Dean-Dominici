package it.polimi.ingsw.view.events;

import javafx.event.Event;
import javafx.event.EventType;

public class GenericMessage extends Event {

    private String msg;

    public static final EventType<GenericMessage> MESSAGE = new EventType<>(Event.ANY,"ANY");
    public static final EventType<GenericMessage> ANY = MESSAGE;
    public static final EventType<GenericMessage> ERROR = new EventType<>(Event.ANY,"ERROR");

    public GenericMessage(EventType<? extends Event> eventType, String msg) {
        super(eventType);
        this.msg = msg;
    }

    public String getMsg()
    {
        return msg;
    }
}
