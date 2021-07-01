package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceList;

import java.util.List;

/**
 * packet taht sends the list of resources gotten from an extraction
 */
public class StorageMassExtraction extends Packet<ServerController> implements PacketManager<ServerController> {
    boolean buyturn;
    List<ExtractionInstruction> insertions;

    public StorageMassExtraction( boolean buyturn, List<ExtractionInstruction> insertions)
    {
        super("StorageMassExtraction");
        this.insertions = insertions;
        this.buyturn = buyturn;
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
                packet = instruction.apply(controller,this.getClientIndex());
                if(packet!=null)
                {
                    failed = true;
                    remaining.add(instruction.getResource());
                }

            }
            else
            {
                remaining.add(instruction.getResource());
            }
        }

        //TODO provarle tutte e rimandare indietro solo se "remaining" non è vuota
        if(failed)
        {
            controller.sendMessage(packet,this.getClientIndex());
            controller.sendStorageUpdate(this.getClientIndex());
            controller.sendChestUpdate(this.getClientIndex());
            return new PendingCost(remaining);
        }
        else
        {
            controller.sendMessage(new ACK(0),this.getClientIndex());
            controller.sendStorageUpdate(this.getClientIndex());
            controller.sendChestUpdate(this.getClientIndex());
            if(buyturn) controller.sendPendingCard(this.getClientIndex());

            return new OperationCompleted();
        }
    }
}
