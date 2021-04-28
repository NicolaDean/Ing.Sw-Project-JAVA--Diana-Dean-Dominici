package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ServerController;

public class ACK extends Packet implements PacketManager{

    int errorMSG;

    public ACK(int code)
    {
        super("ACK");
        this.errorMSG = code;
    }


    @Override
    public Packet analyze(ServerController controller)
    {
        //TODO we can have a "error Msg" class with a (array of error) that load from JSON and when i need one i do "getErrorCode( int )"
        //TODO Controller contains the view so i can do "view.printError" or something similar
        switch (this.errorMSG)
        {
            case 0:
                System.out.println("OK");
                break;
            case 1:
                System.out.println("NotEnoughPlayers");
                break;
            case 2:
                System.out.println("PlayerListFull");
                break;
            case 3:
                System.out.println("NickameAlreadyTaken");
                break;
            default:
                System.out.println("Generic error");
        }
        return null;

    }


}
