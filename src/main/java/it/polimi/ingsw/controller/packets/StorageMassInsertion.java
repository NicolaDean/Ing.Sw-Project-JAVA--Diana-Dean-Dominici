package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceList;

import java.util.ArrayList;
import java.util.List;

public class StorageMassInsertion extends Packet<ServerController> implements PacketManager<ServerController> {

    List<InsertionInstruction> insertions;

    public StorageMassInsertion( List<InsertionInstruction> insertions)
    {
        super("StorageMassInsertion");
        this.insertions = insertions;
    }

    @Override
    public Packet analyze(ServerController controller) {

        Packet packet = null;
        boolean failed = false;
        List<Resource> remaining = new ResourceList();

        for(InsertionInstruction instruction: insertions)
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
            return new MarketResult(remaining);
        }
        else
        {
            controller.sendMessage(new ACK(0),this.getPlayerIndex());
            controller.sendStorageUpdate();
            return new OperationCompleted();
        }

    }
}
