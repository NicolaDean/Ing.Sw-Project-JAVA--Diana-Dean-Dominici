package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ServerController;

/**
 * packet that makes the client refresh the dashboard
 */

public class DashReset extends Packet<ServerController>{



    public DashReset()
    {
       super("DashReset");
    }

    @Override
    public Packet analyze(ServerController controller)
    {
        controller.dashReset(this.getClientIndex());
       return null;
    }
}
