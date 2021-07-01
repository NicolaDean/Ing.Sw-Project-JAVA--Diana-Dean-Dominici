package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceList;

import java.util.ArrayList;
import java.util.List;

/**
 * packet that informs which resources are inserted
 */
public class StorageMassInsertion extends Packet<ServerController> implements PacketManager<ServerController> {


    List<InsertionInstruction> insertions;

    public StorageMassInsertion(List<InsertionInstruction> insertions)
    {
        super("StorageMassInsertion");
        this.insertions = insertions;

    }

    @Override
    public Packet analyze(ServerController controller) {

        Packet packet = null;
        boolean failed = false;
        List<Resource> remaining = new ResourceList();

        for(InsertionInstruction instruction: insertions) {
            if (!failed) {
                packet = instruction.apply(controller, this.getClientIndex());
                if (packet != null) {
                    failed = true;
                    remaining.add(instruction.getResource());
                }else controller.getPendingGain().remove(instruction.getResource());
            } else {
                remaining.add(instruction.getResource());
            }

        }

        if(failed)
        {
            controller.sendMessage(packet,this.getClientIndex());
            controller.sendStorageUpdate(this.getClientIndex());
            //return new MarketResult(remaining);
            return new MarketResult(controller.getPendingGain());
        }

        if(remaining.isEmpty()&&controller.getPendingGain().isEmpty())
        {
            controller.sendMessage(new ACK(0),this.getClientIndex());
            controller.sendStorageUpdate(this.getClientIndex());

            return new OperationCompleted();
        }
        controller.sendStorageUpdate(this.getClientIndex());
        return new MarketResult(controller.getPendingGain());
    }
}
