package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.model.dashboard.Deposit;

public class StorageUpdate extends Packet<ClientController> implements PacketManager<ClientController>{

    Deposit[] deposits;
    public StorageUpdate(Deposit [] deposits) {
        super("StorageUpdate");
        this.deposits = deposits;
    }

    @Override
    public Packet analyze(ClientController controller)
    {
        controller.storageUpdate(this.deposits);
        return null;
    }




}
