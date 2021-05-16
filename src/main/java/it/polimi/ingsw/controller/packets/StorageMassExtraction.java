package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ServerController;

import java.util.List;

public class StorageMassExtraction extends Packet<ServerController> implements PacketManager<ServerController> {

    List<ExtractionInstruction> insertions;

    public StorageMassExtraction( List<ExtractionInstruction> insertions)
    {
        super("StorageMassExtraction");
        this.insertions = insertions;
    }

    @Override
    public Packet analyze(ServerController controller) {

        for(ExtractionInstruction instruction: insertions)
        {
            Packet packet = instruction.apply(controller,this.getPlayerIndex());
            if(packet!=null) return packet;
        }
        return new ACK(0);
    }
}
