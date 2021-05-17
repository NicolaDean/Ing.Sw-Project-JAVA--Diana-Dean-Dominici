package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceList;

import java.util.List;

public class StorageMassExtraction extends Packet<ServerController> implements PacketManager<ServerController> {
    boolean buyturn;
    List<ExtractionInstruction> insertions;

    public StorageMassExtraction( boolean buyturn, List<ExtractionInstruction> insertions)
    {
        super("StorageMassExtraction");
        this.insertions = insertions;
    }

    @Override
    public Packet analyze(ServerController controller) {

        boolean failed = false;
        List<Resource> remaining = new ResourceList();
        Packet packet =null;

        for(ExtractionInstruction instruction: insertions)
        {
            if(!failed)
            {
                packet = instruction.apply(controller,this.getPlayerIndex());
                if(packet!=null)
                {
                    //TODO invert packet(NACK) and Pending gain
                    failed = true;
                    remaining.add(instruction.getResource());
                }
            }
            else
            {
                remaining.add(instruction.getResource());
            }
        }

        if(failed)
        {
            controller.sendMessage(packet,this.getPlayerIndex());
            controller.sendStorageUpdate();
            return new PendingCost(remaining);
        }
        else
        {
            controller.sendMessage(new ACK(0),this.getPlayerIndex());
            controller.sendStorageUpdate();

            if(buyturn) controller.sendPendingCard();

            return new OperationCompleted();
        }
    }
}
