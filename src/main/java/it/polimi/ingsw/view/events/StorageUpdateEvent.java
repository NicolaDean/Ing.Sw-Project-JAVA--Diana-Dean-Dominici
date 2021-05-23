package it.polimi.ingsw.view.events;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventType;

public class StorageUpdateEvent extends Event {

    private String msg;

    public static final EventType<StorageUpdateEvent> STORAGE = new EventType<>(Event.ANY,"ANY");
    public static final EventType<StorageUpdateEvent> ANY = STORAGE;
    public static final EventType<StorageUpdateEvent> STORAGE_SAVE = new EventType<>(Event.ANY,"STORAGE_SAVE");

    public StorageUpdateEvent(EventType<? extends Event> eventType, String msg) {
        super(eventType);
        System.out.println("Fired");
        this.msg = msg;
    }

    public String getMsg()
    {
        return msg;
    }
}
