package it.polimi.ingsw.controller.packets.serverpackets;

import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.controller.packets.Packet;
import it.polimi.ingsw.controller.packets.PacketManager;
import it.polimi.ingsw.enumeration.ResourceType;

public class BonusProduction  extends Packet implements PacketManager {

    private int position;
    private ResourceType obt;

    public BonusProduction(int position,ResourceType res,ResourceType obt)
    {
        super("BonusProduction");
        this.obt  = obt;
        this.position = position;

    }

    @Override
    public Packet analyze(ServerController controller) {
        return controller.bonusProduction(this.position,this.obt,this.getPlayerIndex());
    }
}
