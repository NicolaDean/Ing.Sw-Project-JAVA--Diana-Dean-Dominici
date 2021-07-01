package it.polimi.ingsw.controller.packets;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.controller.packets.Packet;
import it.polimi.ingsw.controller.packets.PacketManager;
import it.polimi.ingsw.enumeration.ErrorMessages;

/**
 * packet that shows the error occured during an operation
 */
public class ACK extends Packet<ClientController> implements PacketManager<ClientController>{

    int errorMSG;

    public ACK(int code)
    {
        super("ACK");
        this.errorMSG = code;
    }

    public ACK(ErrorMessages code)
    {
        super("ACK");
        this.errorMSG = code.ordinal();
    }


    @Override
    public Packet analyze(ClientController controller)
    {
        //TODO we can have a "error Msg" class with a (array of error) that load from JSON and when i need one i do "getErrorCode( int )"
        //TODO Controller contains the view so i can do "view.printError" or something similar
        //TODO ERROR MESSAGES ARE AUTOMATICLY GENERATED FROM EXEPTION, SWITCH WILL DISAPPEAR
        controller.exampleACK(this.errorMSG);

        return null;
    }


}
