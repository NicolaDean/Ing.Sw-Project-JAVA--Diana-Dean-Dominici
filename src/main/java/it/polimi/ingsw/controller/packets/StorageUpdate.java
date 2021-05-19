package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.model.dashboard.Deposit;

public class StorageUpdate extends Packet<ClientController> implements PacketManager<ClientController>{

    Deposit[] deposits;
    int playerIndex;
    public StorageUpdate(Deposit [] deposits,int playerIndex) {
        super("StorageUpdate");
        this.deposits = deposits;
        this.playerIndex = playerIndex;
    }

    @Override
    public Packet analyze(ClientController controller)
    {
        controller.storageUpdate(this.deposits,this.playerIndex);
        controller.showStorage();
        return null;
    }




}
