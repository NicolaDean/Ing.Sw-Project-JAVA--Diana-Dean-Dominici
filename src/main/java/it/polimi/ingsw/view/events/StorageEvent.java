package it.polimi.ingsw.view.events;

import it.polimi.ingsw.model.dashboard.Deposit;
import javafx.event.Event;
import javafx.event.EventType;

public class StorageEvent extends BasicEvent{

    Deposit[] deposits;

    public static final EventType<StorageEvent> STORAGE = new EventType<>(Event.ANY,"ANY");

    public StorageEvent(EventType<? extends Event> eventType,Deposit[] deposits) {
        super(eventType,deposits);
        this.deposits = deposits;
    }

    public Deposit[] getDeposits()
    {
        return  deposits;
    }
}
