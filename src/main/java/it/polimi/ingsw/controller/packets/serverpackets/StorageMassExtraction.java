package it.polimi.ingsw.controller.packets.serverpackets;

import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.controller.packets.Packet;
import it.polimi.ingsw.controller.packets.PacketManager;
import it.polimi.ingsw.controller.packets.bidirectionalpackets.ACK;

import java.util.List;

public class StorageMassExtraction extends Packet implements PacketManager {

    List<InsertionInstruction> insertions;

    public StorageMassExtraction( List<InsertionInstruction> insertions)
    {
        super("StorageMassExtraction");
        this.insertions = insertions;
    }

    @Override
    public Packet analyze(ServerController controller) {

        for(InsertionInstruction instruction: insertions)
        {
            Packet packet = instruction.apply(controller,this.getPlayerIndex());
            if(packet!=null) return packet;
        }
        return new ACK(0);
    }
}
