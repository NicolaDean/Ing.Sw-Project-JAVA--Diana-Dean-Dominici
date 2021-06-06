package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ServerController;

public class Pong extends Packet<ServerController> implements PacketManager<ServerController>{

    int index;

    public Pong(int index) {
        super("Pong");
        this.index=index;
    }

    @Override
    public Packet analyze(ServerController controller) {

        try
        {
            System.out.println("PONG from client "+index +" on game number "+controller.getMatchId() );

            try
            {
                controller.getClients().get(index).getPingController().setPinged();
            } catch (Exception e) {
                System.out.println("Game not already starded");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
